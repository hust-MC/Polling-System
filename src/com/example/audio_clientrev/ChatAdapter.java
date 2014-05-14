package com.example.audio_clientrev;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
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

		if ((chatList.get(position)).Tx) 												// ready to transmit
		{
			switch (chatList.get(position).type)
			{
			case ChatAdapter.WORD:

				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.message_r, null);
				TextView textView = (TextView) linearLayout
						.findViewById(R.id.chatView_r);
				textView.setText(String.valueOf(chatList.get(position).Content));
				break;

			case ChatAdapter.PIC:

				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.picture_r, null);
				ImageView imageView_pic = (ImageView) linearLayout
						.findViewById(R.id.picture_r);
				imageView_pic.setImageBitmap((Bitmap) chatList.get(position).Content);
				break;
				
			case ChatAdapter.SOUND:
				
				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.picture_r, null);
				ImageView imageView_sound = (ImageView) linearLayout
						.findViewById(R.id.picture_r);
				imageView_sound.setImageBitmap((Bitmap) chatList.get(position).Content);
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
				chatView.setImageBitmap((Bitmap) chatList.get(position).Content);
			}
			else
			// receive string
			{
				linearLayout = (LinearLayout) layoutInflater.inflate(
						R.layout.message_l, null);
				TextView chatView = (TextView) linearLayout
						.findViewById(R.id.chatView_l);
				chatView.setText(String.valueOf(chatList.get(position).Content));
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
