package com.example.audio_clientrev;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

public class Camera extends Activity
{
	static byte[] pic;
	DataTransmission dataTransmission = new DataTransmission();

	public void savePhoto()
	{
		int size = 0;

		File file = new File(Environment.getExternalStorageDirectory() + "/mc/image/"
				+ String.valueOf(MainActivity.chatAdapter.getCount()) + ".png");
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			size = fis.available();
			System.out.println("size = " + size);
			pic = new byte[size];
			fis.read(pic);
			fis.close();
		}
		catch (FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		final Bitmap cameraBitmap = BitmapFactory.decodeFile(file
				.getAbsolutePath());
		MainActivity.chatAdapter.addList(cameraBitmap, ChatAdapter.Tx,ChatAdapter.PIC);

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					dataTransmission.send(pic,ChatAdapter.PIC);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}

	public Uri openCamera()
	{
		File f = null;
		try
		{
			File dir = new File(Environment.getExternalStorageDirectory()
					+ "/mc/image/");
			if (!dir.exists())
				dir.mkdirs();

			f = new File(dir, String.valueOf(MainActivity.chatAdapter
					.getCount()) + ".png");										// localTempImgDir & localTempImageFileName is custom defined name
		}
		catch (ActivityNotFoundException e)
		{
			Toast.makeText(this, "没有找到储存目录", Toast.LENGTH_LONG).show();
		}
		return Uri.fromFile(f);
	}

	public void handlePhoto(byte[] picByte)
	{
		File dir = new File(Environment.getExternalStorageDirectory() + "/mc/image/");
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		File file = new File(dir, String.valueOf(MainActivity.chatAdapter.getCount())
				+ ".png");
		Bitmap bitmap = BitmapFactory.decodeByteArray(picByte, 0,
				picByte.length);

		MainActivity.chatAdapter.addList(bitmap, ChatAdapter.Rx,ChatAdapter.PIC);
		FileOutputStream out = null;
		try
		{
			out = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
