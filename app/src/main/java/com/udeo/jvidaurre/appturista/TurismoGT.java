package com.udeo.jvidaurre.appturista;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class TurismoGT extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
