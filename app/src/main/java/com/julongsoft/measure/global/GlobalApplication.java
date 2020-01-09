package com.julongsoft.measure.global;

import android.app.Application;
import android.content.Context;

import com.julongsoft.measure.db.GreenDaoHelper;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by duoqi.tao on 2017/7/11.
 */

public class GlobalApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        GreenDaoHelper.getInstance();
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
    }


    /**
     * 获取全局的上下文引用
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }
}
