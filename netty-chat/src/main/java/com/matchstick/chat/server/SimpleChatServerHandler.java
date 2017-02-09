/**  */
package com.matchstick.chat.server;

import java.text.MessageFormat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author MatchstickShi
 * SimpleChannelInboundHandler，这个类实现了 ChannelInboundHandler 接口，ChannelInboundHandler 提供了许多事件处理的接口方法，
 * 然后你可以覆盖这些方法。现在仅仅只需要继承 SimpleChannelInboundHandler 类而不是你自己去实现接口方法。
 */
public class SimpleChatServerHandler extends SimpleChannelInboundHandler<String>
{
	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	/**
	 * handlerAdded() 事件处理方法。
	 * 每当从服务端收到新的客户端连接时，客户端的 Channel 存入 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
	 */
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception
	{
		Channel incoming = ctx.channel();
		channels.forEach(channel -> channel.writeAndFlush("系统：用户[" + incoming.remoteAddress() + "]加入\n"));
		channels.add(ctx.channel());
	}
	
	/**
	 * handlerRemoved() 事件处理方法。
	 * 每当从服务端收到客户端断开时，客户端的 Channel 移除 ChannelGroup 列表中，并通知列表中的其他客户端 Channel
	 */
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception
	{
		Channel incoming = ctx.channel();
		channels.forEach(channel -> channel.writeAndFlush("系统：用户[" + incoming.remoteAddress() + "]离开\n"));
		channels.remove(ctx.channel());
	}
	
	/**
	 * channelRead0() 事件处理方法。
	 * 每当从服务端读到客户端写入信息时，将信息转发给其他客户端的 Channel。
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception
	{
		Channel incoming = ctx.channel();
		channels.forEach(channel -> channel.writeAndFlush(
				channel != incoming ? MessageFormat.format("{0}：{1}\n", incoming.remoteAddress(), s)
						: MessageFormat.format("(你)：{0}\n", s)));
	}
	
	/**
	 * channelActive() 事件处理方法。服务端监听到客户端活动
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
        Channel incoming = ctx.channel();
        System.out.println("系统：客户端["+incoming.remoteAddress()+"]在线");
	}
	
	/**
	 * channelInactive() 事件处理方法。服务端监听到客户端不活动
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception
	{
        Channel incoming = ctx.channel();
        System.out.println("系统：客户端["+incoming.remoteAddress()+"]掉线");
	}
	
	/**
	 * exceptionCaught() 事件处理方法是当出现 Throwable 对象才会被调用，即当 Netty 由于 IO 错误或者处理器在处理事件时抛出的异常时。
	 * 在大部分情况下，捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
	 * 然而这个方法的处理方式会在遇到不同异常的情况下有不同的实现，比如你可能想在关闭连接之前发送一个错误码的响应消息。
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
	{
		Channel incoming = ctx.channel();
        System.out.println("系统：客户端["+incoming.remoteAddress()+"]异常");
        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
	}
}