package com.mc.instance_messgage;

import java.util.ArrayList;
import java.util.List;

import com.example.audio_clientrev.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter
{
	private Context context;
	static List<Message> chatList = new ArrayList<Message>();

	final static boolean Tx = true;
	final static boolean Rx = false;
	static final int WORD = 1;
	static final int PIC = 2;
	static final int SOUND = 3;

	Time time = new Time();
	String minute = null;

	public ChatAdapter(Context context)
	{
		this.context = context;
	}

	@Override
	public int getCount()
	{
		return chatList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return chatList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout linearLayout = null;
		TextView now;
		time.setToNow();

		if (time.minute < 10)
		{
			minute = "0" + time.minute;
		}
		else
		{
			minute = time.minute + "";
		}

		if ((chatList.get(position)).Tx) 												// ready to transmit
		{
			switch (chatList.get(position).type)
			{
			case ChatAdapter.WORD:

				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.message_r, null);
				TextView textView = (TextView) linearLayout
						.findViewById(R.id.chatView_r);
				now = (TextView) linearLayout.findViewById(R.id.ttime_r);

				textView.setText(String.valueOf(chatList.get(position).Content));
				now.setText(time.month + 1 + "/" + time.monthDay + " " + time.hour
						+ ":" + minute);
				break;

			case ChatAdapter.PIC:

				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.picture_r, null);
				ImageView imageView_pic = (ImageView) linearLayout
						.findViewById(R.id.picture_r);
				now = (TextView) linearLayout.findViewById(R.id.ptime_r);
				
				imageView_pic
						.setImageBitmap((Bitmap) chatList.get(position).Content);
				now.setText(time.month + 1 + "/" + time.monthDay + " " + time.hour
						+ ":" + minute);
				break;

			case ChatAdapter.SOUND:

				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.picture_r, null);
				ImageView imageView_sound = (ImageView) linearLayout
						.findViewById(R.id.picture_r);
				now = (TextView) linearLayout.findViewById(R.id.ptime_r);
				
				imageView_sound
						.setImageBitmap((Bitmap) chatList.get(position).Content);
				now.setText(time.month + 1 + "/" + time.monthDay + " " + time.hour
						+ ":" + minute);
				break;
			}
		}
		else
		// ready to receive
		{
			if (chatList.get(position).Content instanceof Bitmap)                          // receive
			// bitmap
			{
				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.picture_l, null);
				ImageView chatView = (ImageView) linearLayout
						.findViewById(R.id.picture_l);
				now = (TextView) linearLayout.findViewById(R.id.ptime_l);
				
				chatView.setImageBitmap((Bitmap) chatList.get(position).Content);
				now.setText(time.month + 1 + "/" + time.monthDay + " " + time.hour
						+ ":" + minute);
			}
			else
			// receive string
			{
				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.message_l, null);
				TextView chatView = (TextView) linearLayout
						.findViewById(R.id.chatView_l);
				now = (TextView) linearLayout.findViewById(R.id.ttime_l);
				
				chatView.setText(String.valueOf(chatList.get(position).Content));
				now.setText(time.month + 1 + "/" + time.monthDay + " " + time.hour
						+ ":" + minute);
			}
		}
		return linearLayout;
	}

	public void addList(Object content, Boolean Tx, int type)
	{
		Message message = new Message(content, Tx, type);
		chatList.add(message);
		notifyDataSetChanged();
	}

	class Message                       // declare a Message class to store Message
	{
		Object Content;
		Boolean Tx;
		int type;

		public Message(Object Content, Boolean Tx, int type)
		{
			this.Tx = Tx;
			this.Content = Content;
			this.type = type;
		}
	}
}
