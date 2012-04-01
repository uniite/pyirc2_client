package com.jbotelho.pyirc2.client;

import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PushListActivity extends PushActivity
{
	protected ListView mList;
	
	
	private Runnable updateList = new Runnable() {
	   public void run() {
		   ((ArrayAdapter)mList.getAdapter()).notifyDataSetChanged();
	   }
	};
	
	public Runnable getUpdateList () {
		return updateList;
	}
}
