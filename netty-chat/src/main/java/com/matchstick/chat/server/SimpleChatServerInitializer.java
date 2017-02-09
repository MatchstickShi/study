/**  */
package com.matchstick.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @author MatchstickShi
 */
public class SimpleChatServerInitializer extends ChannelInitializer<SocketChannel>
{
	@Override
	protected void initChannel(SocketChannel ch) throws Exception
	{
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
			.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8))
	        .addLast("encoder", new StringEncoder(CharsetUtil.UTF_8))
	        .addLast("handler", new SimpleChatServerHandler());
		
		System.out.println("系统：客户端["+ch.remoteAddress() +"]连接上");
	}
}