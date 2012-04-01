package com.jbotelho.pyirc2.client;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;


public class PushApplication extends android.app.Application {
	PushActivity currentActivity;

    ConversationListActivity conversationListActivity;

    ArrayList<Message> messages = new ArrayList<Message>();

    // TODO: Note that the push service should be started before the
    // 		 pull service, so we don't end up with out-dated data.
    public void pushNoitification(String incoming, String incomingType) {
        Gson gson = new GsonBuilder().create();

        try {
            // Figure out what type of data we have and decode it accordingly
            if (incomingType.equals("Message")) {
                // Decode the message
                Message msg = gson.fromJson(incoming, Message.class);
                // Process the changes
                messages.add(msg);
                // Update the screen
                if (conversationListActivity != null) {
                    conversationListActivity.getUpdateList().run();
                }
            }

        // TODO: Log some sort of invalid JSON error
        } catch(RuntimeException e) {}
    }
}
