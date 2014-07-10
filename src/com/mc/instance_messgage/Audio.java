package com.mc.instance_messgage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.util.Log;

public class Audio
{
	static AudioRecord audioRecord;
	static RecordActivity audioContext = null;
	AudioTrack audioTrack;
	DataTransmission dataTransmission = new DataTransmission();

	File send_file, rev_file, dir;

	byte[] data;
	public MediaPlayer mediaPlayer;

	int cursor;

	public void send_audio() throws IOException
	{
		dir = new File(Environment.getExternalStorageDirectory() + "/mc/voice");
		send_file = new File(dir, String.valueOf(MainActivity.chatAdapter
				.getCount() - 1) + ".amr");

		FileInputStream fis = new FileInputStream(send_file);
		BufferedInputStream bis= new BufferedInputStream(fis); 
		data = new byte[(int)send_file.length()];

		while ((cursor = bis.read(data)) != -1)
		{
		}
			fis.close();
			bis.close();
		dataTransmission.send(data, ChatAdapter.SOUND);
	}

	public void handle_audio(byte[] data) throws IOException
	{
		dir = new File(Environment.getExternalStorageDirectory() + "/mc/voice");
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		rev_file = new File(dir, String.valueOf(MainActivity.chatAdapter
				.getCount()) + ".amr");
		FileOutputStream fos = new FileOutputStream(rev_file);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(data);
		bos.flush();
		if (fos != null)
		{
			fos.close();
		}
		if (bos != null)
		{
			bos.close();
		}
		
	}

	public void play_audio(File file) throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException
	{
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(file.getAbsolutePath());
		mediaPlayer.prepare();
		mediaPlayer.start();

		mediaPlayer.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				Log.d("MC", "completion");
			}
		});
	}

}
