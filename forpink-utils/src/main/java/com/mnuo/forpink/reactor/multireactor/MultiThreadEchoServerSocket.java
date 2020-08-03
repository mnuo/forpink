package com.mnuo.forpink.reactor.multireactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadEchoServerSocket {
	ServerSocketChannel serverSocket;
	AtomicInteger next = new AtomicInteger(0);
	//选择器  
	Selector[] seletor = new Selector[2];
	//引入多个子反应器
	SubReactor[] subReactors = null;
	MultiThreadEchoServerSocket() throws IOException{
		 //初始化多个选择器
		seletor[0] = Selector.open();
		seletor[1] = Selector.open();
		serverSocket = ServerSocketChannel.open();
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9090);
		serverSocket.socket().bind(address);
		
		serverSocket.configureBlocking(false);//非阻塞
//		第一个选择器,负责监控新连接事件
		SelectionKey sk = serverSocket.register(seletor[0], SelectionKey.OP_ACCEPT);
		//绑定Handler: attach新连接监控Handler处理器到SelectionKey(选择键)
		sk.attach(new AcceptorHandler());
		 //第一个子反应器，一子反应器负责一个选器
		SubReactor subReactor1 = new SubReactor(seletor[0]);
		 //第二个子反应器，一子反应器负责一个选择器
		SubReactor subreactor2 = new SubReactor(seletor[1]);
		subReactors = new SubReactor[]{subReactor1, subreactor2};
	}
	
	class SubReactor implements Runnable{
		//每个线程负责一个选择器的查询和选择
		final Selector selector;
		public SubReactor(Selector selector) {
			this.selector = selector;
		}

		@Override
		public void run() {
			try {
				while (!Thread.interrupted()) {
					selector.select();
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> it = keys.iterator();
					
					while (it.hasNext()) {
//						反应器负责dispatch收到的事件
						SelectionKey sk = it.next();
						dispatch(sk);
					}
					keys.clear();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		void dispatch(SelectionKey sk){
			//调用之前attach绑定到选择键的handler处理器对象
			Runnable handler = (Runnable)sk.attachment();
			if(handler != null){
				handler.run();
			}
		}
	}
	// Handler:新连接处理器
	class AcceptorHandler implements Runnable{
		@Override
		public void run() {
			try {
				SocketChannel channel = serverSocket.accept();
				if(channel != null){
					//Todo
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(next.incrementAndGet() == seletor.length){
				next.set(0);
			}
		}
	}
	void startService(){
		new Thread(subReactors[0]).start();
		new Thread(subReactors[1]).start();
	}

	public static void main(String[] args) throws IOException {
		MultiThreadEchoServerSocket server = new MultiThreadEchoServerSocket();
		server.startService();
	}
}
