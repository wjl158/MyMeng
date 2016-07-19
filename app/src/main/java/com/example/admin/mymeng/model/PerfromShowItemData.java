package com.example.admin.mymeng.model;

import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by admin on 2016/07/13.
 * 演绎秀ListItem内容的数据结构
 */
public class PerfromShowItemData {
    public PerfromShowItemData(String caption, String content, String imgBk, String imgHeadA, String imgHeadB, String time) {
        Caption = caption;
        Content = content;
        this.imgBk = imgBk;
        this.imgHeadA = imgHeadA;
        this.imgHeadB = imgHeadB;
        Time = time;
    }

    /**
     * 标题
     */
    public String Caption;

    /**
     * 内容
     */
    public String Content;

    /**
     * 背景图
     */
    public String imgBk;

    /**
     * 两个小头像A、B
     */
    public String imgHeadA;
    public String imgHeadB;

    /**
     * 发帖时间
     */
    public String Time;

}
