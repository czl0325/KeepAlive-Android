package com.github.zlonepixelsdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class KeepAliveService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenListener listener = new ScreenListener(this);
        listener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                KeepAliveManager.getInstance(KeepAliveService.this).stopKeepAliveActivity();
            }

            @Override
            public void onScreenOff() {
                KeepAliveManager.getInstance(KeepAliveService.this).startKeepAliveActivity();
            }

            @Override
            public void onUserPresent() {

            }
        });
    }
}
