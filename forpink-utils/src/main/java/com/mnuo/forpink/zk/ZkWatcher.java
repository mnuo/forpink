package com.mnuo.forpink.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent.Type;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

public class ZkWatcher {
	private String workerPath = "/test/listener/remoteNode";
	private String subWorkerPath = "/test/listener/remoteNode/id-";
	
	public void testWatcher() throws Exception{
		CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
		client.start();
		
		Stat stat = client.checkExists().forPath(workerPath);
		if(stat == null){
			byte[] payload = workerPath.getBytes("UTF-8");
			client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(workerPath, payload);
		}
		
		Watcher w = new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println("监听到的变化watchedEvent = " + 
						event);
			}
		};
		byte[] content = client.getData().usingWatcher(w).forPath(workerPath);
		
		System.out.println("监听节点内容：" + new String(content));
		// 第一次变更节点数据
        client.setData().forPath(workerPath, "第1次更改内容".getBytes());
        // 第二次变更节点数据
        client.setData().forPath(workerPath, "第2次更改内容".getBytes());
        Thread.sleep(Integer.MAX_VALUE);
	}
	
	public void nodeCache() throws Exception{
		//检查节点是否存在, 没有则创建
		CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
		client.start();
		
		Stat stat = client.checkExists().forPath(workerPath);
		if(stat == null){
			byte[] payload = workerPath.getBytes("UTF-8");
			client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(workerPath, payload);
		}
		
		NodeCache nodeCache = new NodeCache(client, workerPath, false);
		
		NodeCacheListener listener = new NodeCacheListener() {
			
			@Override
			public void nodeChanged() throws Exception {
				ChildData child = nodeCache.getCurrentData();
				 System.err.println("ZNode节点状态改变, path="+ child.getPath());
				 System.err.println("ZNode节点状态改变, data=" + new String(child.getData(), "Utf-8"));
				 System.err.println("ZNode节点状态改变, stat=" + child.getStat());
			}
		};
		//启动节点监听
		nodeCache.getListenable().addListener(listener);
		nodeCache.start();
		//第一次改变数据
		client.setData().forPath(workerPath, "第1次改变数据".getBytes());
		Thread.sleep(1000);
		//第二次改变数据
		client.setData().forPath(workerPath, "第2次改变数据".getBytes());
		Thread.sleep(1000);
		//第三次改变数据
		client.setData().forPath(workerPath, "第3次改变数据".getBytes());
		Thread.sleep(1000);
		
		//第四次改变数据
		client.delete().forPath(workerPath);
        Thread.sleep(Integer.MAX_VALUE);
	}
	
	public void pathChildrenCache() throws Exception{
		//检查节点是否存在, 没有则创建
		CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
		client.start();
		
		Stat stat = client.checkExists().forPath(workerPath);
		if(stat == null){
			byte[] payload = workerPath.getBytes("UTF-8");
			client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(workerPath, payload);
		}
		
		PathChildrenCache cache = new PathChildrenCache(client, workerPath, true);
		PathChildrenCacheListener listener = new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				ChildData data = event.getData();
				switch (event.getType()) {
				case CHILD_ADDED:
					System.err.println("子节点增加, path="+data.getPath()+", data=%s" + new String(data.getData(), "UTF-8"));
					break;
				case CHILD_UPDATED:
					System.err.println("子节点更新, path="+data.getPath()+", data=%s" + new String(data.getData(), "UTF-8"));
					break;
				case CHILD_REMOVED:
					System.err.println("子节点删除, path="+data.getPath()+", data=%s" + new String(data.getData(), "UTF-8"));
					break;
				default:
					break;
				}
				
			}
		};
		//增加监听器
		cache.getListenable().addListener(listener);
		//设置启动模式
		cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
		Thread.sleep(2000);
		
		//创建3个子节点
		for (int i = 0; i < 3; i++) {
			client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(subWorkerPath+i, (subWorkerPath+i).getBytes("utf-8"));
		}
		Thread.sleep(2000);
		//删除3个子节点
		for (int i = 0; i < 3; i++) {
			client.delete().forPath(subWorkerPath+i);
		}
	}
	
	public static void main(String[] args) {
		ZkWatcher w = new ZkWatcher();
		try {
//			w.testWatcher();
			//无论是PathChildrenCache，还是TreeCache，所谓的监听都是在进行Curator本地缓存视图和ZooKeeper服务器远程的数据节点的对比，并且在进行数据同步时会触发相应的事件
//			w.nodeCache();
			w.pathChildrenCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
