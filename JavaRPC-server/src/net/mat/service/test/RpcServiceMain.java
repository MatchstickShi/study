/**  */
package net.mat.service.test;

import net.mat.provider.RpcServiceProvider;

/**
 * 创建一个异步启动服务端的线程并启动，用于接受RPC客户端的请求，根据请求参数调用服务实现类，返回结果给客户端
 * @author MatchstickShi
 */
public class RpcServiceMain
{
	public static void main(String[] args)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					RpcServiceProvider.start("localhost", 8088);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}