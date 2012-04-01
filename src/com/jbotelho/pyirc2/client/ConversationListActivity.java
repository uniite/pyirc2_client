package com.jbotelho.pyirc2.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;

public class ConversationListActivity extends PushListActivity {
	private LayoutInflater mInflater;
    public static final String TAG = "ConvListActivity";
    

    public void onResume() {
        super.onResume();
        if (getAppContext().conversationListActivity == null) {
            getAppContext().conversationListActivity = this;
        }

        if (mList != null && mList.getAdapter() != null) {
            Message msg = new Message();
            msg.body = "Test message.";
            msg.sender = "Test User";
            getAppContext().messages.add(msg);
            getUpdateList().run();
        }
    }
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mList = (ListView)findViewById(R.id.list);
        mList.setAdapter(new ConversationListAdapter(this, R.layout.rowlayout));

        KeepAliveService.actionStart(ConversationListActivity.this);
    }
}
