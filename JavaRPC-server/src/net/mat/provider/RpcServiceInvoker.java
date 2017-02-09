/**  */
package net.mat.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 任务调用者
 * 职责：1、将客户端发送的码流反序列化成对象，反射调用服务实现者，获取执行结果。
 * 		2、将执行结果反序列化成对象，通过Socket发送给客户端。
 * 		3、释放Socket等连接资源，防止句柄泄露。	
 * @author MatchstickShi
 */
public class RpcServiceInvoker implements Runnable
{
	Socket client = null;
	
	public RpcServiceInvoker(Socket client)
	{
		this.client = client;
	}
	
	@Override
	public void run()
	{
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		
		try
		{
			input = new ObjectInputStream(client.getInputStream());
			String interfaceName = input.readUTF();
			Class<?> serviceCls = Class.forName(interfaceName);
			if(serviceCls.isInterface())		//如果是接口，则默认包名后缀"impl"，且实现类后缀为"Impl"的实现类
				serviceCls = Class.forName(serviceCls.getPackage().getName() + ".impl." + serviceCls.getSimpleName() + "Impl");
			String methodName = input.readUTF();
			Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
			Object[] arguments = (Object[]) input.readObject();
			Method method = serviceCls.getMethod(methodName, parameterTypes);
			Object result = method.invoke(serviceCls.newInstance(), arguments);
			output = new ObjectOutputStream(client.getOutputStream());
			output.writeObject(result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(output != null)
			{
				try
				{
					output.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if(input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if(client != null)
			{
				try
				{
					client.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}