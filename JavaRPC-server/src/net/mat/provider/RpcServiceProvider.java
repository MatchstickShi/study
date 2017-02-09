/**  */
package net.mat.provider;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 服务提供者
 * 职责：作为服务端，监听客户端的TCP连接，接受到新的客户端连接之后，将其封装成Task，由线程池执行。
 * @author MatchstickShi
 */
public class RpcServiceProvider
{
	static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	
	public static void start(String hostName, int port) throws Exception
	{
		ServerSocket server = new ServerSocket();
		server.bind(new InetSocketAddress(hostName, port));
		try
		{
			while (true)
			{
				executor.execute(new RpcServiceInvoker(server.accept()));
			} 
		}
		finally
		{
			server.close();
		}
	}
}