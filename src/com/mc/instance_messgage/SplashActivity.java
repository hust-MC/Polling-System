package com.mc.instance_messgage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

public class SplashActivity extends Activity
{
	private final int SPLASH_DISPLAY_LENGHT = 2500; // 延迟两秒
	ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		progressDialog = new ProgressDialog(this);
//		progressDialog.setTitle("正在努力加载。。。");
//		progressDialog.setMessage("请稍候。。。");
//		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		progressDialog.show();
		
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				Intent mainIntent = new Intent(SplashActivity.this,
						MainActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
			}

		}, SPLASH_DISPLAY_LENGHT);
	}

}
