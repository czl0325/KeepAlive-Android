package com.github.zldoubleprocessdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class FirstService extends Service {
    private MyBinder mBinder;
    private MyServiceConnection mServiceConnection;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mBinder == null) {
            mBinder = new MyBinder();
        }
        mServiceConnection = new MyServiceConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this, SecondService.class), mServiceConnection, BIND_IMPORTANT);

        String CHANNEL_ONE_ID = "com.github.zldoubleprocessdemo";
        String CHANNEL_ONE_NAME = "服务1";
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker("服务1来了").setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent).setContentText("服务2启动");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(Color.RED);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ONE_ID);
        }
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        startForeground(startId, notification);
        return START_STICKY;
    }

    public class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public String getProcessName() throws RemoteException {
            return "FirstService";
        }
    }

    public class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("czl","SecondService服务已连接上");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("czl","SecondService服务被系统杀死了!");
            Intent intent = new Intent(FirstService.this, SecondService.class);
            FirstService.this.startActivity(intent);
            FirstService.this.bindService(intent, mServiceConnection, Context.BIND_IMPORTANT);
            Toast.makeText(FirstService.this, "服务1又活过来了", Toast.LENGTH_SHORT).show();
        }
    }
}
