package com.example.audio_clientrev;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

public class Audio
{
	static AudioRecord audioRecord;
	static RecordActivity audioContext = null;
	AudioTrack audioTrack;
	DataTransmission dataTransmission = new DataTransmission();

	List<short[]> revSound = new ArrayList<short[]>();     // Store Sound

	public Audio(List<short[]> revSound)
	{
		this.revSound = revSound;
		final int SIZE = revSound.get(0).length;

		// audioData = new short[SIZE*RecordActivity.bufferReadResult];

	}

	public void audio_play()
	{
		int frequency = 11025;
		int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
		int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;

		int bufferSize = AudioRecord.getMinBufferSize(frequency,
				channelConfiguration, audioEncoding);
//		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
//				channelConfiguration, audioEncoding, bufferSize);

		int trackSize = AudioTrack.getMinBufferSize(frequency,
				channelConfiguration, AudioFormat.ENCODING_PCM_16BIT);
		audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 11025,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, trackSize,
				AudioTrack.MODE_STREAM);

		MainActivity.chatAdapter.addList("录音啦", false);

//		audioRecord.startRecording(); // 开始录音
		audioTrack.play(); // 开始播放
		// while (isRecording)
		{
			// bufferReadResult = audioRecord.read(TxBuffer, 0, bufferSize); //
			// 从麦克风读取音频
			// Log.d("MC",
			// String.valueOf(bufferSize) + " "
			// + String.valueOf(bufferReadResult));
			// short[] tmp = new short[bufferReadResult];
			// soundList.add(tmp);
			for (short[] audioData : revSound)
			{
				audioTrack.write(audioData, 0, audioData.length);
				Log.d("MC", "send");
			}
		}
		audioTrack.stop();
//		audioRecord.stop();
	}
	
}
