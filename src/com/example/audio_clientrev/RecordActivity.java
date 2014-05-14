package com.example.audio_clientrev;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View.OnCreateContextMenuListener;
import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.os.Bundle;
import android.os.Environment;

public class RecordActivity extends Activity
{
	static boolean isRecording = false;

	public File file;
	public static MediaRecorder mediaRecorder;
	public MediaPlayer mediaPlayer;

	public void record() throws IllegalStateException, IOException
	{
		isRecording = true;
		File dir = new File(Environment.getExternalStorageDirectory()
				+ "/mc/voice");
		if (!dir.exists())
			dir.mkdirs();

		file = new File(dir,
				String.valueOf(MainActivity.chatAdapter.getCount()) + ".amr");

		mediaRecorder = new MediaRecorder();
		mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		mediaRecorder.setOutputFile(file.getAbsolutePath());

		mediaRecorder.prepare();
		mediaRecorder.start();

		while (isRecording)
		{
			
		}

		mediaRecorder.stop();
		mediaRecorder.release();

		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(file.getAbsolutePath());
		mediaPlayer.prepare();
		mediaPlayer.start();
		
		mediaPlayer.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				Log.d("MC","completion");
			}
		}); 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_record);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					record();
					new Audio().send_audio();
					finish();
				} catch (IllegalStateException e)
				{
					e.printStackTrace();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}
