package com.github.zljobservicedemo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobHandleService extends JobService {
    private int kJobId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("czl","job onStartCommand");
        scheduleJob(getJobInfo());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e("czl","job onStartJob");
        boolean isWork = isServiceWork("com.github.zljobservicedemo.MyService");
        if (!isWork) {
            startService(new Intent(this, MyService.class));
            Toast.makeText(this, "服务启动了!", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e("czl","job被停止了");
        scheduleJob(getJobInfo());
        return true;
    }

    private void scheduleJob(JobInfo t) {
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    private JobInfo getJobInfo() {
        JobInfo.Builder builder = new JobInfo.Builder(kJobId, new ComponentName(this, JobHandleService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        builder.setPeriodic(2000);
        return builder.build();
    }

    private boolean isServiceWork(String serviceName) {
        boolean isWork = false;
        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = am.getRunningServices(100);
        if (myList.size() == 0)
            return false;
        for (int i=0; i<myList.size(); i++) {
            String name = myList.get(i).service.getClassName().toString();
            if (name.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }
}
