package com.example.taobaounion.base;

import android.app.Application;
import android.content.Context;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/23 0023  15:14
 */
public class BaseApplication extends Application {
    private static Context appContext;//静态，不动就好调用

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getBaseContext();
    }
    public static Context getAppContext(){
        return appContext;
    }
}
