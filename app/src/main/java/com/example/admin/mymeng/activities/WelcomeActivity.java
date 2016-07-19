package com.example.admin.mymeng.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.admin.mymeng.R;
import com.example.admin.mymeng.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // 隐藏上面状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);

        Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).map(l->{
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
            return null;
        }).subscribe();
    }
}
