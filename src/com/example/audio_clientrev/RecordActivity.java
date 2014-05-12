package com.example.audio_clientrev;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.view.View.OnCreateContextMenuListener;
import android.app.Activity;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.AudioRecord;
import android.os.Bundle;

public class RecordActivity extends Activity
{
	int a,b,c=0;  //try
	
	AudioRecord audioRecord;
	static boolean isRecording = false;
	static RecordActivity audioContext = null;
	static int bufferReadResult;
	static List<short[]> soundList = new ArrayList<short[]>();
	
	AudioTrack audioTrack;
	short[] audioData;                           //store audio data
	
	
	DataTransmission dataTransmission = new DataTransmission();
	public short[] TxBuffer;

	public void record()
	{
		int frequency = 11025;
		int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
		int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

		try
		{
			int bufferSize = AudioRecord.getMinBufferSize(frequency,
					channelConfiguration, audioEncoding);
			audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
					frequency, channelConfiguration, audioEncoding, bufferSize);

			isRecording = true;

			int trackSize = AudioTrack.getMinBufferSize(frequency,
					channelConfiguration, AudioFormat.ENCODING_PCM_16BIT);
			audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 11025,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, trackSize,
					AudioTrack.MODE_STREAM);

			TxBuffer = new short[bufferSize];

			audioRecord.startRecording(); // 开始录音
			audioTrack.play(); // 开始播放
			int i=1000;
			while (i-- != 0)
			{
				bufferReadResult = audioRecord.read(TxBuffer, 0, bufferSize); // 从麦克风读取音频
				audioTrack.write(TxBuffer, 0, bufferReadResult);
//				Log.d("MC",
//						String.valueOf(bufferSize) + " "
//								+ String.valueOf(bufferReadResult));
//				soundList.add(TxBuffer);
				
				//				dataTransmission.send(TxBuffer);

				// System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult); //
				// 复制文件
				// audioTrack.write(TxBuffer, 0, bufferReadResult);
				//				new Thread(new Runnable()
				//				{
				//					@Override
				//					public void run()
				//					{
				//						audioTrack.write(ClientThread.audioData, 0,
				//								bufferReadResult);
				//					}
				//				}).start();
			}
			try
			{
//				dataTransmission.send(soundList);
				Log.d("MC", "send");
				for (short[] audioData : soundList)
				{
					audioTrack.write(audioData, 0, bufferReadResult);
					Log.d("MC", "send");
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			audioTrack.stop();
			audioRecord.stop();
			finish();
		}
		catch (Throwable t)
		{
			Log.e("AudioRecord", "Recording Failed");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_record);
		soundList.clear();
		audioContext = this;
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				record();
			}
		}).start();
	}
}
