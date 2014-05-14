package com.example.audio_clientrev;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;

public class DataTransmission
{
	
	static int revType;

	public void send(Object data,int type) throws IOException
	{
		OutputStream os = ClientThread.socket.getOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(os);
		out.writeInt(type);
		out.writeObject(data);
		out.flush();
	}

	public Object rev() throws StreamCorruptedException, IOException,
			ClassNotFoundException
	{
		ObjectInputStream in = new ObjectInputStream(
				ClientThread.socket.getInputStream());
		revType = in.readInt();
		return in.readObject();
	}

}
