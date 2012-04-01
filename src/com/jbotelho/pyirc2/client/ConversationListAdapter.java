package com.jbotelho.pyirc2.client;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ConversationListAdapter extends ArrayAdapter<String> {
	private Activity context;
	private LayoutInflater mInflater;
    private ArrayList<Message> messages;
	

	public ConversationListAdapter(Activity context, int resource) {
		super(context, resource);
		this.context = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = ((ConversationListActivity)getContext()).getAppContext().messages;
	}
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
		Bitmap myImage;
		
		// TODO: Look into deflation
		if (null == convertView) {
			row = mInflater.inflate(R.layout.rowlayout, null);
		} else {
			row = convertView;
		}
		
		// Get the Message we're displaying
		Message msg = messages.get(position);
		
		// Get the UI elements
		TextView name = (TextView) row.findViewById(R.id.name);
		TextView status = (TextView) row.findViewById(R.id.status);
		ImageView iv = (ImageView) row.findViewById(R.id.status_icon);
		
		// Display the chat's name
		name.setText(msg.sender);
		
		// Set the name's text color based on the state of the conversation
        name.setTextColor(Color.WHITE);
		
        // Set the status icon to the chat icon (chats don't have a status)
        iv.setImageResource(R.drawable.status_chat);
        // Set the description
        status.setText(msg.body);
		
		return row;
	}
	
	
	@Override
	public int getCount () {
		return messages.size();
	}
}