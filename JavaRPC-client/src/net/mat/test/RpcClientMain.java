/**  */
package net.mat.test;

import java.net.InetSocketAddress;

import net.mat.proxy.RpcServiceProxy;
import net.mat.service.EchoService;

/**
 * @author MatchstickShi
 */
public class RpcClientMain
{
	public static void main(String[] args)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				RpcServiceProxy<EchoService> proxy = new RpcServiceProxy<>();
				EchoService echo = proxy.importer(EchoService.class, new InetSocketAddress("localhost", 8088));
				System.out.println(echo.echo("Are you ok ?"));
			}
		}).start();
	}
}