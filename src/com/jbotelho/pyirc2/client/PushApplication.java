package com.jbotelho.pyirc2.client;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;


public class PushApplication extends android.app.Application {
	PushActivity currentActivity;
    ConversationListActivity conversationListActivity;
    ConversationActivity conversationActivity;

    ArrayList<Conversation> conversations = new ArrayList<Conversation>();
    ArrayList<Message> messages = new ArrayList<Message>();

    Conversation currentConversation;

    PushState pushState = PushState.NORMAL;
    Delta delta = null;

    PushClient pushClient = null;

    ConcurrentLinkedQueue<String> pushQueue = new ConcurrentLinkedQueue<String>();





    // TODO: Note that the push service should be started before the
    // 		 pull service, so we don't end up with out-dated data.
    public void pushNoitification() {
        String incomingType = pushQueue.poll();
        String incoming = pushQueue.poll();
        Gson gson = new GsonBuilder().create();

        try {
            // Figure out what type of data we have and decode it accordingly
            Class cls = Class.forName("com.jbotelho.pyirc2.client." + incomingType);
            // Only accept specific class types
            if (!cls.isAnnotationPresent(PushObject.class)) {
                throw new Exception(String.format("Invalid type '%s'", incomingType));
            }
            // Decode the object
            Object obj = gson.fromJson(incoming, cls);

            switch (pushState) {
                case NORMAL:
                    if (obj instanceof Delta) {
                        delta = (Delta)obj;
                        // Need to wait for the delta's data before proceeding
                        pushState = PushState.DELTA;
                    }
                    break;
                case DELTA:
                    // Figure out what to do with the full delta

                    // First we need to translate the target into an actual object
                    // (usually an ArrayList)
                    ArrayList target = null;
                    if (delta.target.length == 2) {
                        if (cls == Message.class) {
                            target = messages;
                        } else if (cls == Conversation.class) {
                            target = conversations;
                        } else {
                            // Unsupported type
                        }
                    }

                    // Then, perform the appropriate operation on said target,
                    // according to delta.event
                    if (target != null) {
                        if (delta.event.equals("add")) {
                            target.add(obj);
                        } else if (delta.event.equals("change")) {
                            target.set(delta.target[1].number, obj);
                        } else if (delta.event.equals("remove")) {
                            target.remove(delta.target[1].number);
                        }
                    }

                    // Update the current activity
                    if (currentActivity instanceof PushListActivity) {
                        ((PushListActivity)currentActivity).getUpdateList().run();
                    }

                    // Done processing this delta
                    pushState = PushState.NORMAL;
                    break;
            }

        // TODO: Log some sort of invalid JSON error
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
