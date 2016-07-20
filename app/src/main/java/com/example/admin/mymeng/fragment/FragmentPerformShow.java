package com.example.admin.mymeng.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.HandlerThread;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.admin.mymeng.R;
import com.example.admin.mymeng.activities.MainActivity;
import com.example.admin.mymeng.adapter.PerformShowAdapter;
import com.example.admin.mymeng.intf.OnMultiLineTabViewListener;
import com.example.admin.mymeng.model.PerformShowItemData1;
import com.example.admin.mymeng.model.PerfromShowItemData;
import com.example.admin.mymeng.noHttp.HttpListener;
import com.example.admin.mymeng.view.MultiLineTabView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by admin on 2016/06/21.
 * 演绎秀
 */
public class FragmentPerformShow extends Fragment {

    private ArrayList<PerfromShowItemData> mData;

    private PerformShowAdapter performShowAdapter;

    /**
     * main layout
     */
    View view;

    /**
     * 图片轮播
     */
    private RollPagerView mRollViewPager;
    private TestLoopAdapter mLoopAdapter;

    /**
     * 刷新控件
     */
    SwipeRefreshLayout swipeRefreshLayout;

    private NestedScrollView nestedScrollView;

    /**
     * 全部分类， 点击后弹出菜单
     */
    private TextView textView;

    /**
     * 当前分类菜单的索引、类型
     */
    private int nType;

    /**
     * 工具栏布局
     */
    private RelativeLayout relativeLayout;

    /**
     * 滚动到哪个坐标位置，工具栏才会透明
     */
    private int alphaX;

    public LayoutInflater mInflater;

    /**
     * 工具栏标题: 卖萌
     */
    public TextView tvToolbarText;

    /**
     * 加载更多按钮
     */
    public Button btnLoadMore;

    /**
     * 弹出菜单的滚动位置
     */
    private int nPopupPosX;


    /**
     * 查询所有的帖子
     */
    private final static String URL_QUERY_INFO = "http://192.168.4.43/TP5RC4/public/Index.php/meng/229259139";

    private final static String URL_BASE_BKIMG = "http://192.168.4.43/TP5RC4/public/Image/BkImg/";
    private final static String URL_BASE_HEADIMG = "http://192.168.4.43/TP5RC4/public/Image/HeadImg/";


    // 用于刷新界面
    private android.os.Handler handler = new android.os.Handler() {
        public void handleMessage(android.os.Message msg) {
            nestedScrollView.scrollTo(0, msg.what);
            if (msg.what == (nPopupPosX - 1))
            {
                allTypeClick();
                setToolbarBkg(Integer.MAX_VALUE);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        view = inflater.inflate(R.layout.fg_performshow,container,false);

        //工具栏标题: 卖萌
        tvToolbarText = (TextView)view.findViewById(R.id.fg_performshow_tvPos);

        alphaX = 0;
        nType = 0;

        //工具栏
        relativeLayout = (RelativeLayout)view.findViewById(R.id.fg_performshow_toolbar);
        relativeLayout.setBackgroundColor(Color.argb(0, Integer.parseInt("ec",16), Integer.parseInt("95",16), Integer.parseInt("04",16)));

        //<!--全部分类 最新 热门导航-->
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.layout_fg_performshow_type);


        //滚动事件
        nestedScrollView = (NestedScrollView)view.findViewById(R.id.fg_performshow_MainScroll);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (alphaX == 0)
                {
                    alphaX = (int)linearLayout.getY() - relativeLayout.getHeight();
                }

                //滚动时设置工具栏背景透明渐变
                setToolbarBkg(scrollY);
            }
        });


        //图片轮播
        mRollViewPager= (RollPagerView)view.findViewById(R.id.fg_performshow_roll);
        mRollViewPager.setPlayDelay(3000);
        mRollViewPager.setAdapter(mLoopAdapter = new TestLoopAdapter(mRollViewPager));
        mRollViewPager.setHintView(new IconHintView(view.getContext(),R.drawable.point_focus,R.drawable.point_normal));

        //tab 最新 热门
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.fg_performshow_TabLayout_newhot);
        tabLayout.addTab(tabLayout.newTab().setText("最新"));
        tabLayout.addTab(tabLayout.newTab().setText("热门"));

        //全部分类菜单
        textView = (TextView)view.findViewById(R.id.tv_fg_performshow_alltype);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nPopupPosX = (int)(linearLayout.getY() - relativeLayout.getHeight());

                new Thread(){
                    @Override
                    public void run() {
                        for (int i = 0; i<= nPopupPosX; i++)
                        {
                            try {
                                sleep(1);
                                handler.sendEmptyMessage(i);

                            }catch (Exception e)
                            {
                            }
                        }
                    }
                }.start();
            }
        });

        //RecyclerView控件
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.listMain);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        mData = new ArrayList<PerfromShowItemData>();


        //如果不加这一句，界面显示的时候就会滚动到中间
        //跟EditText一样，在父元素的属性下面下下面这两行即可，不用代码设置。亲测可用。
        //android:focusableInTouchMode="true"
        //android:focusable="true"

        recyclerView.setFocusable(false);

        performShowAdapter = new PerformShowAdapter(mData);
        recyclerView.setAdapter(performShowAdapter);

        //这一句非常重要，RecyclerView和NestedScrollingView滚动冲突，造成滚动不顺畅，
        // 如果不加如下代码则滑动时“不顺畅”、“卡顿”、“粘滞”
        // 详见http://blog.csdn.net/iknownu/article/details/50476615
        recyclerView.setNestedScrollingEnabled(false);



        //刷新控件
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.fg_performshow_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.main_color,
                R.color.gray_4,
                R.color.red,
                R.color.blue);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);;
//        swipeRefreshLayout.setProgressBackgroundColor(R.color.swipe_background_color);
        //swipeRefreshLayout.setPadding(20, 20, 20, 20);
        //swipeRefreshLayout.setProgressViewOffset(true, 100, 200);
        //swipeRefreshLayout.setDistanceToTriggerSync(50);
        //swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });


        //第一次显示时自动刷新
        swipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                swipeRefreshLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });


        btnLoadMore = (Button)view.findViewById(R.id.btnLoadMore);
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLoadMore.setText("正在加载");
                loadData();
            }
        });

        btnLoadMore.setVisibility(View.INVISIBLE);
        return view;
    }


    private class TestLoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.header_hot_show,
                R.drawable.header_hot_user,
                R.drawable.header_drama_user,

        };
        private int count = imgs.length;

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return count;
        }

    }

    public void allTypeClick()
    {
        View view = mInflater.inflate(R.layout.popup_alltype, null, false);

        MultiLineTabView multiLineTabView = (MultiLineTabView)view.findViewById(R.id.MultiLineTabView_popup_alltype_main);
        multiLineTabView.setSelectedIndex(nType);

        multiLineTabView.addItem("全部分类", 0);
        multiLineTabView.addItem("古风", 1);
        multiLineTabView.addItem("动漫", 2);

        multiLineTabView.addItem("小说", 3);
        multiLineTabView.addItem("影视", 4);
        multiLineTabView.addItem("都市", 5);

        multiLineTabView.addItem("纯爱", 6);
        multiLineTabView.addItem("逗比", 7);
        multiLineTabView.addItem("耽美百合", 8);

        multiLineTabView.addItem("剧情歌", 9);
        multiLineTabView.addItem("游戏", 10);
        multiLineTabView.addItem("诗词句", 11);

        multiLineTabView.addItem("CV练习", 12);
        multiLineTabView.addItem("魔幻", 13);
        multiLineTabView.addItem("民国", 14);

        multiLineTabView.addItem("外语", 15);
        multiLineTabView.addItem("童话", 16);
        multiLineTabView.addItem("连环画", 17);



        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

//        popWindow.setAnimationStyle(R.anim.push_left_in);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                //这个事件可以不添加 --weijl
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x5FFF0000));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(textView, 50, 0);


        multiLineTabView.setmDelegate(new OnMultiLineTabViewListener() {
            @Override
            public void onClick(String sType, int nTag) {
                textView.setText(sType);
                nType = nTag;
                popWindow.dismiss();
            }
        });

    }

    /**
     * 设置工具栏的背景透明度
     * @param X 当前滚动条的位置
     */
    public void setToolbarBkg(int X)
    {
        if(X >= alphaX) {
            relativeLayout.setBackgroundColor(Color.argb(255, Integer.parseInt("ec", 16), Integer.parseInt("95", 16), Integer.parseInt("04", 16)));
            tvToolbarText.setVisibility(View.VISIBLE);
        }
        else
        {
            relativeLayout.setBackgroundColor(Color.argb(255*X/alphaX, Integer.parseInt("ec", 16), Integer.parseInt("95", 16), Integer.parseInt("04", 16)));
            tvToolbarText.setVisibility(View.INVISIBLE);

        }
    }

    public void loadData()
    {
        Request<String> request = null;
        request = NoHttp.createStringRequest(URL_QUERY_INFO, RequestMethod.GET);
        NoHttp.newRequestQueue().add(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {

            }
            //刷新成功
            @Override
            public void onSucceed(int what, Response<String> response) {
                //得到Json结果
                List<PerformShowItemData1> barList1 = JSON.parseArray(response.get(),PerformShowItemData1.class);
                for(int i = 0; i < barList1.size(); i++)
                {
                    PerformShowItemData1 performShowItemData1 =  (PerformShowItemData1)barList1.get(i);

                    String Caption = performShowItemData1.getCaption();
                    String Content = performShowItemData1.getDramaContent();
                    String imgBk = URL_BASE_BKIMG + performShowItemData1.getBkImg();
                    String Time = performShowItemData1.getCreateTime();
                    String imgHeadA = URL_BASE_HEADIMG + performShowItemData1.getHeadImgFileNameA();
                    String imgHeadB = URL_BASE_HEADIMG + performShowItemData1.getHeadImgFileNameB();

                    mData.add(new PerfromShowItemData(Caption, Content, imgBk, imgHeadA, imgHeadB, Time));

                    performShowAdapter.notifyDataSetChanged();
                }

                swipeRefreshLayout.setRefreshing(false);
                btnLoadMore.setVisibility(View.VISIBLE);
                btnLoadMore.setText("加载更多");

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                Toast.makeText(view.getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                btnLoadMore.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFinish(int what) {
            }
        });
    }

}
