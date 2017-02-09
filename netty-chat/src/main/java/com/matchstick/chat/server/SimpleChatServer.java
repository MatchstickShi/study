/**  */
package com.matchstick.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author MatchstickShi
 */
public class SimpleChatServer
{
	private int port;

	public SimpleChatServer(int port)
	{
		this.port = port;
	}

	public void run() throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap b = new ServerBootstrap().group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new SimpleChatServerInitializer())
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			System.out.println("netty-chat服务已启动。");
			ChannelFuture f = b.bind(port).sync();	//绑定端口，开始接收进来的连接
			// 等待服务器 socket 关闭 。
			// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
			f.channel().closeFuture().sync();
		}
		finally
		{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
			System.out.println("netty-chat服务已停止。");
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		int port = args.length > 0 ? Integer.parseInt(args[0]) : 8080;
		new SimpleChatServer(port).run();
	}
}