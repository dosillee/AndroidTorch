package com.xgleng.torch;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    public static Context sApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sApplicationContext = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
