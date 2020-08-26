package com.mnuo.forpink.zk;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientFactory {
	/**
     * @param connectionStr zk的连接地址
     * @return CuratorFramework实例
     */
	public static CuratorFramework createSimple(String connectionStr){
		 // 重试策略:第一次重试等待1s，第二次重试等待2s，第三次重试等待4s
        // 第一个参数：等待时间的基础单位，单位为毫秒
        // 第二个参数：最大重试次数
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 获取CuratorFramework实例的最简单方式
        // 第一个参数：zk的连接地址
        // 第二个参数：重试策略
        return CuratorFrameworkFactory.newClient(connectionStr, retryPolicy);
	}

	/**
	 * @param connectionStr
	 *            zk的连接地址
	 * @param retryPolicy重试策略
	 * @param connectionTimeoutMs连接超时时间
	 * @param sessionTimeoutMs会话超时时间
	 * @return CuratorFramework实例
	 */
	public static CuratorFramework createWithOptions(String connectionStr, RetryPolicy retryPolicy,
			int connectionTimeoutMs, int sessionTimeoutMs) {
		// 用builder方法创建CuratorFramework实例
		return CuratorFrameworkFactory.builder()
											.connectString(connectionStr)
											.retryPolicy(retryPolicy)
											.connectionTimeoutMs(connectionTimeoutMs)
											.sessionTimeoutMs(sessionTimeoutMs)
										// 其他的创建选项
										.build();
	}
	
	public static void createNode() {
		// 创建客户端
		CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
		try {
			// 启动客户端实例,连接服务器
			client.start();
			// 创建一个ZNode节点
			// 节点的数据为 payload
			String data = "hello";
			byte[] payload = data.getBytes("UTF-8");
//			String zkPath = "/test/CRUD/node-1";
			String zkPath = "/test/listener/remoteNode";
			client.create().creatingParentsIfNeeded()
				.withMode(CreateMode.PERSISTENT)
				.forPath(zkPath, payload);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
		}
	}
	
	/**
	 * 读取节点
	 */
	public static void readNode() {
	    //创建客户端
	    CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
	    try {
	        //启动客户端实例,连接服务器
	        client.start();
	        String zkPath = "/test/CRUD/node-1";
	        Stat stat = client.checkExists().forPath(zkPath);
	        if (null != stat) {
	            //读取节点的数据
	            byte[] payload = client.getData().forPath(zkPath);
	            String data = new String(payload, "UTF-8");
	            System.err.println("read data:"+ data);
	            String parentPath = "/test";
	            List<String> children = client.getChildren().forPath(parentPath);
	            for (String child : children) {
	            	System.err.println("child:" + child);
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        CloseableUtils.closeQuietly(client);
	    }
	}
	/**
	 * 更新节点
	 */
	public static void updateNode() {
	    //创建客户端
	    CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
	    try {
	        //启动客户端实例,连接服务器
	        client.start();
	        String data = "hello world";
	        byte[] payload = data.getBytes("UTF-8");
	        String zkPath = "/test/CRUD/node-1";
	        client.setData()
	        .forPath(zkPath, payload);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        CloseableUtils.closeQuietly(client);
	    }
	}
	/**
     * 更新节点 - 异步模式
     */
	public static void updateNodeAsync() {
		// 创建客户端
		CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
		try {
			// 异步更新完成，回调此实例
			AsyncCallback.StringCallback callback = new AsyncCallback.StringCallback() {
				// 回调方法
				@Override
				public void processResult(int i, String s, Object o, String s1) {
					System.out.println("i = " + i + " | " + "s = " + s + " | " + "o = " + o + " | " + "s1 = " + s1);
				}
			};
			// 启动客户端实例,连接服务器
			client.start();
			String data = "hello ,every body! ";
			byte[] payload = data.getBytes("UTF-8");
			String zkPath = "/test/CRUD/node-1";
			client.setData().inBackground(callback) // 设置回调实例
					.forPath(zkPath, payload);
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
		}
	}
	/**
     * 删除节点
     */
    public static void deleteNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple("127.0.0.1:2181");
        try {
            //启动客户端实例,连接服务器
            client.start();
            //删除节点
            String zkPath = "/test/CRUD/node-1";
            client.delete().forPath(zkPath);
            //删除后查看结果
            String parentPath = "/test";
            List<String> children = client.getChildren().forPath(parentPath);
            for (String child : children) {
                log.info("child:"+ child);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }
	public static void main(String[] args) {
		createNode();
//		updateNodeAsync();
//		readNode();
//		deleteNode();
	}
	
	
}
