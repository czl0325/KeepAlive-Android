# KeepAlive-Android
app进程包活的几种实现方式


### 方案1 ：仿QQ，留一个像素在前端

详见demo：ZLOnePixelsDemo<br>

启动了一个服务，当监听到用户锁屏广播的时候，启动一个1像素的activity在前端，当用户解锁屏幕后，把这个activity销毁掉，实现了app进程保活。


### 方案2 ：双进程守护

详见demo：ZLDoubleProcessDemo<br>

原理： 进入app的时候启动两个进程服务，A和B，AB互相连接，当A发现B断开（既B被系统杀死），就启动B，重新绑定<br>
同理当B发现A断开，也启动A，重新绑定。
这样确保两个进程服务都活着。


### 方案3 ：放大招 （JobService）

详见demo：ZLJobServiceDemo<br>

原理：通过JobService实现，当安卓系统接收到任意任务，日程，通知的时候，就唤醒自己。