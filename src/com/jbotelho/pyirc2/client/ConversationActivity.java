package com.jbotelho.pyirc2.client;

import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

public class ConversationActivity extends PushListActivity {
	private LayoutInflater mInflater;
    public static final String TAG = "ConversationActivity";
    

    public void onResume() {
        super.onResume();
        if (getAppContext().conversationActivity == null) {
            getAppContext().conversationActivity = this;
        }

        setTitle(getAppContext().currentConversation.name);

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
        mList.setAdapter(new ConversationMessagesAdapter(this, R.layout.message_row));

        registerForContextMenu(mList);

        KeepAliveService.actionStart(ConversationActivity.this);
    }
}
