package com.example.zzs.testbaidumap.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by zzs on 2016/9/27.
 * 使用Application做全局初始化
 * note:必须在清单文件进行注册    android:name=".MyApplication"
 * 否则 写没写这个Application是没有作用
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }
}
