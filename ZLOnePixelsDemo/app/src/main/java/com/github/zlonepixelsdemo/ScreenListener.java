package com.github.zlonepixelsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;

public class ScreenListener {
    private Context mContext;
    private ScreenStateListener mScreenStateListener;

    private ScreenBroadcastReceiver mScreenBroadcastReceiver;

    public ScreenListener(Context context) {
        mContext = context;
        mScreenBroadcastReceiver = new ScreenBroadcastReceiver();
    }

    private class ScreenBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_ON)) {
                mScreenStateListener.onScreenOn();
            } else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
                mScreenStateListener.onScreenOff();
            } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
                mScreenStateListener.onUserPresent();
            }
        }
    }


    public void begin(ScreenStateListener listener) {
        mScreenStateListener = listener;

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mScreenBroadcastReceiver, filter);

        //获取屏幕状态
        PowerManager manager = (PowerManager)mContext.getSystemService(Context.POWER_SERVICE);
        if (manager.isScreenOn()) {
            if (mScreenStateListener!=null) {
                mScreenStateListener.onScreenOn();
            }
        } else {
            if (mScreenStateListener!=null) {
                mScreenStateListener.onScreenOff();
            }
        }
    }

    public void unregisterListener() {
        mContext.unregisterReceiver(mScreenBroadcastReceiver);
    }

    public interface ScreenStateListener {
        void onScreenOn();

        void onScreenOff();
        //用户解锁
        void onUserPresent();
    }
}
