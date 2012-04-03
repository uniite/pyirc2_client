package com.jbotelho.pyirc2.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class ConversationListActivity extends PushListActivity implements OnItemClickListener {
	private LayoutInflater mInflater;
    public static final String TAG = "ConversationListActivity";
    

    public void onResume() {
        super.onResume();
        if (getAppContext().conversationListActivity == null) {
            getAppContext().conversationListActivity = this;
        }
    }
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        mList = (ListView)findViewById(R.id.list);
        mList.setAdapter(new ConversationListAdapter(this, R.layout.rowlayout));
        mList.setOnItemClickListener(this);

        registerForContextMenu(mList);

        KeepAliveService.actionStart(ConversationListActivity.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.conversation_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.add:
                if (getAppContext().pushClient != null) {
                    Delta delta = new Delta();
                    delta.target = new NumberOrText[] { new NumberOrText(0, "conversations") };
                    delta.event = "add";
                    delta.dataType = "Conversation";
                    getAppContext().pushClient.sendObject(delta, Delta.class);

                    Conversation conversation = new Conversation();
                    conversation.name = "#pyguy_test";
                    getAppContext().pushClient.sendObject(conversation, Conversation.class);
                }
                return true;
            case R.id.exit:
                KeepAliveService.actionStop(ConversationListActivity.this);
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    /* When a user long-presses on a conversation, display a context menu */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.conversation_list_context_menu, menu);
    }

    /* When a user taps on a context menu item*/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            // For "remove", tell the server to leave the selected conversation
            case R.id.remove:
                if (getAppContext().pushClient != null) {
                    Delta delta = new Delta();
                    delta.target = new NumberOrText[] { new NumberOrText(0, "conversations") };
                    delta.event = "remove";
                    delta.dataType = "Conversation";
                    getAppContext().pushClient.sendObject(delta, Delta.class);

                    Conversation conversation = new Conversation();
                    conversation.name = getAppContext().conversations.get(info.position).name;
                    getAppContext().pushClient.sendObject(conversation, Conversation.class);
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    /* When a user taps on a conversation */
    public void onItemClick(AdapterView parent, View v, int position, long id) {
        // Store the current conversation so the ConversationActivity can reference it
        getAppContext().currentConversation = getAppContext().conversations.get(position);
        // Start the ConversationActivity
        startActivityForResult(new Intent(v.getContext(), ConversationActivity.class), 0);
    }
}
