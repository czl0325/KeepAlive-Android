# KeepAlive-Android
app进程包活的几种实现方式


### 方案1 ：仿QQ，留一个像素在前端

详见demo：ZLOnePixelsDemo<br>

启动了一个服务，当监听到用户锁屏广播的时候，启动一个1像素的activity在前端，当用户解锁屏幕后，把这个activity销毁掉，实现了app进程保活。