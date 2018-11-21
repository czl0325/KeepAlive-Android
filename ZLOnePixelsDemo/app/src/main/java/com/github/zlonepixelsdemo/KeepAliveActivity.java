package com.github.zlonepixelsdemo;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class KeepAliveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_keep_alive);

        Window window = getWindow();
        window.setGravity(Gravity.LEFT|Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = 1;
        params.height = 1;
        params.x = 0;
        params.y = 0;
        window.setAttributes(params);

        KeepAliveManager.getInstance(this).setActivityInstance(this);
        Log.i("czl", "KeepLiveActivity----onCreate!!!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("czl", "KeepLiveActivity----onDestroy!!!");
    }
}
