package com.mnuo.forpink.zk.lock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import com.mnuo.forpink.zk.ClientFactory;

public class ZkLock implements Lock{
	private static final String ZK_PATH = "/test/lock";
	private static final String LOCK_PREFIX = ZK_PATH + "/";
	private static final long WAIT_TIME = 1000;
	
	//ZK客户端
	CuratorFramework client = null;
	private String locked_short_path = null;
	private String locked_path = null;
	private String prior_path = null;
	
	final AtomicInteger lockCount = new AtomicInteger(0);
	private Thread thread;
	
	public ZkLock() {
		client = ClientFactory.createSimple("127.0.0.1:2181");
		client.start();
		try {
			Stat stat = client.checkExists().forPath(ZK_PATH);
			if(stat == null){
				client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
				.forPath(ZK_PATH);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean lock() {
		synchronized (this) {
			if(lockCount.get() == 0){
				thread = Thread.currentThread();
				lockCount.incrementAndGet();
			}else{
				if(!thread.equals(Thread.currentThread())){
					return false;
				}
				lockCount.incrementAndGet();
				return true;
			}
		}
		try {
			boolean locked = false;
			//首先尝试着去加锁
//			（1）创建临时顺序节点，并且保存自己的节点路径。
//			（2）判断是否是第一个，如果是第一个，则加锁成功。如果不是，就找到前一个ZNode节点，并且把它的路径保存到prior_path
			locked = tryLock();
			if(locked){
				return true;
			}
			//如果加锁失败就去等待
			while (!locked) {
				await();
				//获取等待的子节点列表
				List<String> waiter = getWaiters();
				if(checkLocked(waiter)){
					locked = true;
				}
				
			}
			return true;
		} catch (Exception e) {
			unlock();
		}
		return false;
	}
	/**
     * 从zookeeper中拿到所有等待节点
     */
    protected List<String> getWaiters() {

        List<String> children = null;
        try {
            children = client.getChildren().forPath(ZK_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return children;

    }
    private String getShortPath(String locked_path) {

        int index = locked_path.lastIndexOf(ZK_PATH + "/");
        if (index >= 0) {
            index += ZK_PATH.length() + 1;
            return index <= locked_path.length() ? locked_path.substring(index) : "";
        }
        return null;
    }
	/**
     * 尝试加锁
     *
     * @return 是否加锁成功
     * @throws Exception 异常
     */
    private boolean tryLock() throws Exception {
        //创建临时ZNode节点
        locked_path = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
				.forPath(LOCK_PREFIX);
        if (null == locked_path) {
            throw new Exception("zk error");
        }
        //取得加锁的排队编号
        locked_short_path = getShortPath(locked_path);
        //获取加锁的队列
        List<String> waiters = getWaiters();
        //获取等待的子节点列表，判断自己是否第一个
        if (checkLocked(waiters)) {
            return true;
        }
        // 判断自己排第几个
        int index = Collections.binarySearch(waiters, locked_short_path);
        if (index < 0) {
            // 网络抖动，获取到的子节点列表里可能已经没有自己了
            throw new Exception("节点没有找到: " + locked_short_path);
        }
        //如果自己没有获得锁
        //保存前一个节点，稍候会监听前一个节点
        prior_path = ZK_PATH + "/" + waiters.get(index - 1);
        return false;
    }
    private void await() throws Exception {

        if (null == prior_path) {
            throw new Exception("prior_path error");
        }

        final CountDownLatch latch = new CountDownLatch(1);


        //订阅比自己次小顺序节点的删除事件
        Watcher w = new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
                System.out.println("[WatchedEvent]节点删除");

                latch.countDown();
            }
        };

        client.getData().usingWatcher(w).forPath(prior_path);
/*
        //订阅比自己次小顺序节点的删除事件
        TreeCache treeCache = new TreeCache(client, prior_path);
        TreeCacheListener l = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client,
                                   TreeCacheEvent event) throws Exception {
                ChildData data = event.getData();
                if (data != null) {
                    switch (event.getType()) {
                        case NODE_REMOVED:
                            log.debug("[TreeCache]节点删除, path={}, data={}",
                                    data.getPath(), data.getData());

                            latch.countDown();
                            break;
                        default:
                            break;
                    }
                }
            }
        };

        treeCache.getListenable().addListener(l);
        treeCache.start();*/
        latch.await(WAIT_TIME, TimeUnit.SECONDS);
    }

    /**
     * 判断是否加锁成功
     * @param waiters 排队列表
     * @return 成功状态
     */
    private boolean checkLocked(List<String> waiters) {
        //节点按照编号，升序排列
    	Collections.sort(waiters);
        // 如果是第一个，代表自己已经获得了锁
        if (locked_short_path.equals(waiters.get(0))) {
            System.err.println("成功地获取分布式锁,节点为"+locked_short_path);
            return true;
        }
        return false;
    }
	@Override
	public boolean unlock() {
		if (!thread.equals(Thread.currentThread())) {
			return false;
		}

		int newLockCount = lockCount.decrementAndGet();

		if (newLockCount < 0) {
			throw new IllegalMonitorStateException("Lock count has gone negative for lock: " + locked_path);
		}

		if (newLockCount != 0) {
			return true;
		}
		try {
			Stat stat = client.checkExists().forPath(locked_path);
			if(stat != null){
//			if (ZKclient.instance.isNodeExist(locked_path)) {
				client.delete().forPath(locked_path);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
    public void testLock() throws InterruptedException {
//        for (int i = 0; i < 10; i++) {
//            FutureTaskScheduler.add(() -> {
//                ZkLock lock = new ZkLock();
//                lock.lock();
//
//                for (int j = 0; j < 10; j++) {
//
//                    count++;
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                log.info("count = " + count);
//                lock.unlock();
//
//            });
//        }
//
//        Thread.sleep(Integer.MAX_VALUE);
    }

/**
 * InterProcessMutex curator自带InterProcessMutex可重入锁
 * @throws InterruptedException
 */
    public void testzkMutex() throws InterruptedException {

//        CuratorFramework client = ZKclient.instance.getClient();
//        final InterProcessMutex zkMutex =
//                new InterProcessMutex(client, "/mutex");
//        ;
//        for (int i = 0; i < 10; i++) {
//            FutureTaskScheduler.add(() -> {
//
//                try {
//                    zkMutex.acquire();
//
//                    for (int j = 0; j < 10; j++) {
//
//                        count++;
//                    }
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    log.info("count = " + count);
//                    zkMutex.release();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            });
//        }
//
//        Thread.sleep(Integer.MAX_VALUE);
    }
}
