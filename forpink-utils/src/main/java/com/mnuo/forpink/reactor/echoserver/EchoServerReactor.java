package com.mnuo.forpink.reactor.echoserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServerReactor implements Runnable{
	Selector selector;
	ServerSocketChannel serverSocket;
	EchoServerReactor() throws IOException{
		 //...获取选择器、开启serverSocket服务监听通道
        //...绑定AcceptorHandler新连接处理器到selectKey
		// 打开选择器
		selector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9090);
		
		serverSocket.bind(address);
		//非阻塞
		serverSocket.configureBlocking(false);
		
		//分布处理, 第一步接受accespt事件
		SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		sk.attach(new AcceptorHandler());
		
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				selector.select();
				Set<SelectionKey> selected = selector.selectedKeys();
				Iterator<SelectionKey> it = selected.iterator();
				if(it.hasNext()){
					SelectionKey sk = it.next();
					dispatch(sk);
				}
				selected.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	void dispatch(SelectionKey sk){
		Runnable handler = (Runnable) sk.attachment();
		if(handler != null){
			handler.run();
		}
	}
	 // Handler:新连接处理器
	class AcceptorHandler implements Runnable{
		@Override
		public void run() {
			try {
				SocketChannel channel = serverSocket.accept();
				if(channel != null){
						new EchoHander(selector, channel);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	public static void main(String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }
}
class EchoHander implements Runnable{
	final SocketChannel channel;
	final SelectionKey sk;
	final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
	static final int RECEIVING = 0, SENDING = 1;
	int state = RECEIVING;
	EchoHander(Selector selector, SocketChannel c) throws IOException{
		channel = c;
		c.configureBlocking(false);
		//仅仅取得选择键, 后设置感兴趣的IO事件
		sk = channel.register(selector, 0);
		//将handler作为选择键的附件
		sk.attach(this);
		//注册read就绪事件
		sk.interestOps(SelectionKey.OP_READ);
		selector.wakeup();
	}
	@Override
	public void run() {
		try {
			if(state == SENDING){
				//写入通道
				channel.write(byteBuffer);
				//切换成写模式
				byteBuffer.clear();
				
				sk.interestOps(SelectionKey.OP_READ);
				state = RECEIVING;
			}else if(state == RECEIVING){
				int length = 0;
				while ((length = channel.read(byteBuffer)) > 0) {
					System.out.println(new String(byteBuffer.array(), 0, length));
				}
				 //读完后，准备开始写入通道,byteBuffer切换成读模式
				byteBuffer.flip();
				 //读完后，注册write就绪事件
                sk.interestOps(SelectionKey.OP_WRITE);
                //读完后,进入发送的状态
                state = SENDING;
			}
			 //处理结束了, 这里不能关闭select key，需要重复使用
            //sk.cancel();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
