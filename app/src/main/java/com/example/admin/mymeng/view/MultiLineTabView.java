package com.example.admin.mymeng.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.mymeng.R;
import com.example.admin.mymeng.intf.OnMultiLineTabViewListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/06/24.
 */
public class MultiLineTabView extends LinearLayout {

    /**
     * 设置Item的点击事件监听
     */
    private OnMultiLineTabViewListener mDelegate;

    private SparseArray<View> mList;

    /**
     * 每行有多少列
     */
    private int mColumn;

    /**
     * 总共有多少个Item
     */
    private int mCountItem;

    /**
     * Item的高度
     */
    private float mItemHeight;

    /**
     * Item字体尺寸
     */
    private float mItemTextSize;

    /**
     * Item字体颜色
     */
    private int mItemTextColor;

    /**
     * 底部条颜色
     */
    private int mItemBottomColor;

    /**
     * 当前的linearlayout
     */


    /**
     * 当前选中的Item的索引
     */
    private int mSelectedIndex;


    /**
     * 设置当前哪个Item被选中
      * @param n
     */
    public void setSelectedIndex(int n)
    {

    }

    public void setmDelegate(OnMultiLineTabViewListener Delegate)
    {
        mDelegate = Delegate;
    }

    LinearLayout linearLayout;


    private void addRowLinearLayout() {
        linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) mItemHeight);
        linearLayout.setLayoutParams(layoutParams);

        linearLayout.setOrientation(HORIZONTAL);
        linearLayout.setWeightSum(mColumn);

        addView(linearLayout);
    }

    private void addRowItemLayout(String itemName, int tag){
        RelativeLayout relativeLayout = new RelativeLayout(linearLayout.getContext());
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        relativeLayout.setLayoutParams(layoutParams1);
        linearLayout.addView(relativeLayout);


        TextView textView = new TextView(linearLayout.getContext());
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams2.addRule(RelativeLayout.CENTER_VERTICAL);

        textView.setText(itemName);
        textView.setTextColor(mItemTextColor);
        textView.setId(mCountItem+100);
        textView.setTag(tag);
        //这里要注意参数
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mItemTextSize);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < mList.size(); i++)
                {
                   int n = mList.keyAt(i);
                    if (n == (int)v.getTag())
                    {
                        ((View)mList.get(n)).setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    }
                    else
                    {
                        ((View)mList.get(n)).setBackgroundColor(mItemTextColor);
                    }

                }

                mDelegate.onClick(v);
            }
        });


        relativeLayout.addView(textView, layoutParams2);

        View view = new View(getContext());
        view.setBackgroundColor(mItemBottomColor);

        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 4);
        layoutParams3.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams3.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams3.addRule(RelativeLayout.ALIGN_LEFT, mCountItem+100);
        layoutParams3.addRule(RelativeLayout.ALIGN_RIGHT, mCountItem+100);
        relativeLayout.addView(view, layoutParams3);

        mList.put(tag, view);
//        mList.add(view);
    }



    public void addItem(String itemName, int tag)
    {
        //首先判断当前有多少个Item

        //这里需要先添加一个LinearLayout
        if (mCountItem % mColumn == 0)
        {

            addRowLinearLayout();
            addRowItemLayout(itemName, tag);
        }
        else
        {
            addRowItemLayout(itemName, tag);

        }
        mCountItem++;
        invalidate();

    }

    public MultiLineTabView(Context context) {
        super(context);
    }

    public MultiLineTabView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mList = new SparseArray<View>();

        setOrientation(VERTICAL);
        mCountItem = 0;

        /*这里取得declare-styleable集合*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MultiLineTabView);

        /*取得本集合里面总共有多少个属性，（有多少个属性被定义在drawable的XML里）*/
        int indexCount = typedArray.getIndexCount();

        /*遍历这些属性，拿到属性对应的id，然后通过id拿到对应的值*/
        for (int i = 0; i < indexCount; i++) {

             /*拿到对应的id值taId*/
            int taId = typedArray.getIndex(i);
            switch (taId) {
                case R.styleable.MultiLineTabView_column:
                    mColumn = typedArray.getInteger(taId, 3);
                    break;
                case R.styleable.MultiLineTabView_itemHeight:
                    mItemHeight = typedArray.getDimensionPixelSize(taId, 40);
                    break;
                case R.styleable.MultiLineTabView_itemTextColors:
                    mItemTextColor = typedArray.getColor(taId, 0);
                    break;
                case R.styleable.MultiLineTabView_itemTextSize:
                    mItemTextSize = typedArray.getDimension(taId, 0);
                    break;
                case R.styleable.MultiLineTabView_itemBottomColors:
                    mItemBottomColor = typedArray.getColor(taId, 0);
                    break;
            }
        }

    }

    public MultiLineTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
