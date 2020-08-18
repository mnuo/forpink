package com.mnuo.forpink.netty.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyDiscardServer {
	private final int serverPort;
	ServerBootstrap b = new ServerBootstrap();
	
	public NettyDiscardServer(int port){
		this.serverPort = port;
	}
	
	public void runServer(){
		//创建反应器线程组
		EventLoopGroup bossLoopGroup = new NioEventLoopGroup();
		EventLoopGroup workerLoopGroup = new NioEventLoopGroup();
		
		try {
			// 1设置反应器线程组
			b.group(bossLoopGroup);
			// 2设置nio的通道
			b.channel(NioServerSocketChannel.class);
			
			// 3 设置监听端口
			b.localAddress(serverPort);
			
			// 4设置通道的参数
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
			
			// 5装配子通道流水线
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				/**
				 * 有连接到达时创建一个通道
				 */
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// 流水线管理子通道中的handler处理器
					//向子通道流水线添加一个handler处理器
					ch.pipeline().addLast(new NettyDiscardHandler());
				}
				
			});
			
			// 6 开始绑定服务器
			// 通过调用sync同步方法阻塞直到绑定成功
			ChannelFuture channelFuture;
		
			channelFuture = b.bind().sync();
		
			// 7 等待通道关闭的异步任务结束
	        // 服务监听通道会一直等待通道关闭的异步任务结束
			ChannelFuture closeFuture = channelFuture.channel().closeFuture();
			closeFuture.sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 8关闭EventLoopGroup，
	           // 释放掉所有资源包括创建的线程
			workerLoopGroup.shutdownGracefully();
			bossLoopGroup.shutdownGracefully();
		}
		
	}
	 public static void main(String[] args) throws InterruptedException {
	        int port = 9090;
	        new NettyDiscardServer(port).runServer();
	    }
	
}
