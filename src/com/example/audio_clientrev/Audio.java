package com.example.audio_clientrev;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
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
	FileInputStream fis;
	FileOutputStream fos;
	BufferedOutputStream bos;
	ByteArrayOutputStream baos;

	byte[] data, tem;
	public MediaPlayer mediaPlayer;

	int cursor;

	public void send_audio() throws IOException
	{
		dir = new File(Environment.getExternalStorageDirectory() + "/mc/voice");
		send_file = new File(dir, String.valueOf(MainActivity.chatAdapter
				.getCount() - 1) + ".mp3");

		fis = new FileInputStream(send_file);
		baos = new ByteArrayOutputStream(1000);
		tem = new byte[1000];

		while ((cursor = fis.read(tem)) != -1)
		{
			baos.write(tem, 0, cursor);
		}
//		fis.close();
//		bis.close();
//		baos.close();
		data = baos.toByteArray();
		dataTransmission.send(data, DataTransmission.SOUND);
	}

	public void handle_audio(byte[] data) throws IOException
	{
		dir = new File(Environment.getExternalStorageDirectory() + "/mc/voice");
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		rev_file = new File(dir, String.valueOf(MainActivity.chatAdapter
				.getCount()) + ".mp3");
		fos = new FileOutputStream(rev_file);
		bos = new BufferedOutputStream(fos);
		bos.write(data);
		play_audio();
	}

	public void play_audio() throws IllegalArgumentException,
			SecurityException, IllegalStateException, IOException
	{
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(rev_file.getAbsolutePath());
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
		MainActivity.chatAdapter.addList("record", false);
	}
}
