package com.jbotelho.pyirc2.client;

import android.app.Service;

abstract class PushService extends Service {
    public PushApplication getAppContext(){
        return (PushApplication) getApplicationContext();
    }
}