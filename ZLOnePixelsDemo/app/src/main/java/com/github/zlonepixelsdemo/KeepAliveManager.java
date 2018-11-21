package com.github.zlonepixelsdemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class KeepAliveManager {
    private static KeepAliveManager instance;
    private Context mContext;
    private WeakReference<AppCompatActivity> mActivityInstance;

    public KeepAliveManager(Context context) {
        mContext = context;
    }

    public static KeepAliveManager getInstance(Context context) {
        if (instance == null) {
            synchronized (KeepAliveManager.class) {
                if (instance == null) {
                    instance = new KeepAliveManager(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void setActivityInstance(AppCompatActivity activityInstance) {
        mActivityInstance = new WeakReference<AppCompatActivity>(activityInstance);
    }

    public void startKeepAliveActivity() {
        Intent intent = new Intent(mContext, KeepAliveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void stopKeepAliveActivity() {
        if (mActivityInstance!=null&&mActivityInstance.get()!=null){
            AppCompatActivity activity = mActivityInstance.get();
            activity.finish();
        }
    }
}
