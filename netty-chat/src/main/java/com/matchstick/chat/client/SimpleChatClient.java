/**  */
package com.matchstick.chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author MatchstickShi
 */
public class SimpleChatClient
{
	private final String host;

	private final int port;

	public SimpleChatClient(String host, int port)
	{
		this.host = host;
		this.port = port;
	}

	public void run() throws Exception
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try
		{
			Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class).handler(new SimpleChatClientInitializer());
			Channel ch = bootstrap.connect(host, port).sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while(true)
				ch.writeAndFlush(in.readLine() + "\r\n");
		}
		finally
		{
			group.shutdownGracefully();
		}
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		new SimpleChatClient("localhost", 8080).run();
	}
}
