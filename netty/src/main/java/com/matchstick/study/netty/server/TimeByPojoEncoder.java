package com.matchstick.study.netty.server;

import com.matchstick.study.netty.pojo.UnixTime;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author shibingqian
 * 2016年9月22日
 */
public class TimeByPojoEncoder extends /*ChannelOutboundHandlerAdapter*/ MessageToByteEncoder<UnixTime>
{
	/*@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
	{
		UnixTime m = (UnixTime)msg;
		ByteBuf encoded = ctx.alloc().buffer(4);
		encoded.writeInt((int) m.value());*/
		/*
		 * 第一，通过 ChannelPromise，当编码后的数据被写到了通道上 Netty 可以通过这个对象标记是成功还是失败。
		 * 第二， 我们不需要调用 cxt.flush()。因为处理器已经单独分离出了一个方法 void flush(ChannelHandlerContext cxt),
		 * 		如果像自己实现 flush() 方法内容可以自行覆盖这个方法。
		 */
		/*ctx.write(encoded, promise);
	}*/

	@Override
	protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) throws Exception
	{
		out.writeInt((int) msg.value());
	}
}