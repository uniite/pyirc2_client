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
		   getAppContext().pushNoitification();
	   }
	};
		
	public Runnable getPushNoitificationRunnable () {
		return pushNoitificationRunnable;
	}
}
