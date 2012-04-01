package com.jbotelho.pyirc2.client;

import android.app.Activity;
import android.os.Handler;

public class PushActivity extends android.app.Activity
{
	private Handler mHandler = new Handler();

    public void onResume() {
        super.onResume();
        getAppContext().currentActivity = this;
    }
	
	
	public PushApplication getAppContext(){
        return (PushApplication) getApplicationContext();
    }
	
    
	public Handler getHandler() {
		return mHandler;
	}
	
	private Runnable pushNoitificationRunnable = new Runnable() {
	   public void run() {
		   getAppContext().pushNoitification(incoming, incomingType);
	   }
	};
		
	public Runnable getPushNoitificationRunnable () {
		return pushNoitificationRunnable;
	}
	
	String incoming = "";
	String incomingType = "";
	
	public void pushData (String incoming, String incomingType) {
		this.incoming = incoming;
		this.incomingType = incomingType;
	}
}
