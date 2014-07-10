package com.mc.instance_messgage;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ClientThread implements Runnable
{
	final int PORT = 6666;                            // 设置端口信息
	final int TIMEOUT = 3000;

	static final int CONNECT_FAILED = 1;                     // 初始化消息类型
	static final int CONNECT_SUCCESS = 2;

	static Socket socket;

	Object obj;
	static OutputStream out = null;
	Handler handler, revHandler;
	String content;

	List<short[]> revSound = new ArrayList<short[]>();

	DataTransmission dataTransmission = new DataTransmission();

	public ClientThread(Handler handler)
	{
		this.handler = handler;
	}

	public void sendMsg(Object obj)
	{
		Message msg = new Message();
		msg.obj = obj;
		handler.sendMessage(msg);
	}

	public void sendMsg(int type)
	{
		Message msg = new Message();
		msg.arg1 = type;
		handler.sendMessage(msg);
	}

	@Override
	public void run()
	{
		Log.d("MC", "RUN");
		try
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress("192.168.0.119", PORT), 3000);
			sendMsg(CONNECT_SUCCESS);
		} catch (Exception e)
		{
			sendMsg(CONNECT_FAILED);
		}
		try
		{
			while ((obj = dataTransmission.rev()) != null)                   // 判断数据类型
			{
				sendMsg(obj);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
