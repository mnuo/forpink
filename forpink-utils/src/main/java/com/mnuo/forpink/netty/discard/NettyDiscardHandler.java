package com.mnuo.forpink.netty.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyDiscardHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		log.info("收到消息,丢弃如下:");
		ByteBuf in = (ByteBuf) msg;
		
		try {
			while (in.isReadable()) {
				System.out.println(in.readByte());
			}
			System.out.println();
		} finally {
			ReferenceCountUtil.release(msg);
		}
		
	}
}
