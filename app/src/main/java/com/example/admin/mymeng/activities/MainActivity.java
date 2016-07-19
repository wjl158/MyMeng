package com.example.admin.mymeng.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.mymeng.R;
import com.example.admin.mymeng.base.BaseActivity;
import com.example.admin.mymeng.fragment.FragmentDrama;
import com.example.admin.mymeng.fragment.FragmentMessage;
import com.example.admin.mymeng.fragment.FragmentMy;
import com.example.admin.mymeng.fragment.FragmentPerformShow;
import com.example.admin.mymeng.fragment.FragmentSound;
import com.facebook.drawee.backends.pipeline.Fresco;

public class MainActivity extends BaseActivity {
    private static boolean isExit = false;

    /**
     * 底部导航栏标题和图标
     */
    private TextView tv0;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private ImageView img0;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;

    private RelativeLayout lay0;


    /**
     * 演绎秀
     */
    private FragmentPerformShow fgPerformShow;

    /**
     * 剧本
     */
    private FragmentDrama fgDrama;

    /**
     * 声吧
     */
    private FragmentSound fgSound;

    /**
     * 消息
     */
    private FragmentMessage fgMessage;

    /**
     * 我的
     */
    private FragmentMy fgMy;


    private FragmentManager fManager;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public static void setSnackbarMessageTextColor(Snackbar snackbar, int color) {
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(color);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        fManager = getFragmentManager();

        tv0 = (TextView)findViewById(R.id.activity_main_tv_0);
        tv1 = (TextView)findViewById(R.id.activity_main_tv_1);
        tv2 = (TextView)findViewById(R.id.activity_main_tv_2);
        tv3 = (TextView)findViewById(R.id.activity_main_tv_3);
        tv4 = (TextView)findViewById(R.id.activity_main_tv_4);
        img0 = (ImageView) findViewById(R.id.activity_main_img_0);
        img1 = (ImageView) findViewById(R.id.activity_main_img_1);
        img2 = (ImageView) findViewById(R.id.activity_main_img_2);
        img3 = (ImageView) findViewById(R.id.activity_main_img_3_1);
        img4 = (ImageView) findViewById(R.id.activity_main_img_4_1);

        lay0 = (RelativeLayout)findViewById(R.id.activity_main_lay0);
        lay0.performClick();
    }

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;

            Snackbar.make(findViewById(R.id.lay_activity_main_root), "再按一次退出程序", Snackbar.LENGTH_LONG).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            AppExit(this);
        }
    }


    public void OnClickTab(View v)
    {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        CancleSelected();
        switch (v.getId())
        {

            case R.id.activity_main_lay0:
                tv0.setSelected(true);
                img0.setSelected(true);


                if(fgPerformShow == null){
                    fgPerformShow = new FragmentPerformShow();
                    fTransaction.add(R.id.activity_main_frame,fgPerformShow);
                }else{
                    fTransaction.show(fgPerformShow);
                };
                break;

            case R.id.activity_main_lay1:
                tv1.setSelected(true);
                img1.setSelected(true);

                if(fgDrama == null){
                    fgDrama = new FragmentDrama();
                    fTransaction.add(R.id.activity_main_frame,fgDrama);
                }else{
                    fTransaction.show(fgDrama);
                };
                break;

            case R.id.activity_main_lay2:
                tv2.setSelected(true);
                img2.setSelected(true);

                if(fgSound == null){
                    fgSound = new FragmentSound();
                    fTransaction.add(R.id.activity_main_frame,fgSound);
                }else{
                    fTransaction.show(fgSound);
                };
                break;

            case R.id.activity_main_lay3:
                tv3.setSelected(true);
                img3.setSelected(true);

                if(fgMessage == null){
                    fgMessage = new FragmentMessage();
                    fTransaction.add(R.id.activity_main_frame,fgMessage);
                }else{
                    fTransaction.show(fgMessage);
                };
                break;

            case R.id.activity_main_lay4:
                tv4.setSelected(true);
                img4.setSelected(true);

                if(fgMy == null){
                    fgMy = new FragmentMy();
                    fTransaction.add(R.id.activity_main_frame,fgMy);
                }else{
                    fTransaction.show(fgMy);
                };
                break;
        }

        fTransaction.commit();
    }

    public void CancleSelected()
    {
        tv0.setSelected(false);
        tv1.setSelected(false);
        tv2.setSelected(false);
        tv3.setSelected(false);
        tv4.setSelected(false);

        img0.setSelected(false);
        img1.setSelected(false);
        img2.setSelected(false);
        img3.setSelected(false);
        img4.setSelected(false);

    }


    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgPerformShow != null)fragmentTransaction.hide(fgPerformShow);
        if(fgDrama != null)fragmentTransaction.hide(fgDrama);
        if(fgSound != null)fragmentTransaction.hide(fgSound);
        if(fgMessage != null)fragmentTransaction.hide(fgMessage);
        if(fgMy != null)fragmentTransaction.hide(fgMy);
    }


}
