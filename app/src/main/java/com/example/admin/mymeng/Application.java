package com.example.admin.mymeng;

import com.example.admin.mymeng.unity.JUtils;
import com.yolanda.nohttp.NoHttp;

/**
 * Created by admin on 2016/07/15.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JUtils.initialize(this);
        NoHttp.initialize(this);
    }
}
