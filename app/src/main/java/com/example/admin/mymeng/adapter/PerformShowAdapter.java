package com.example.admin.mymeng.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.mymeng.R;
import com.example.admin.mymeng.model.PerfromShowItemData;
import com.example.admin.mymeng.unity.JUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/07/13.
 * 演绎秀List适配器
 */
public class PerformShowAdapter extends RecyclerView.Adapter<PerformShowAdapter.MyViewHolder> {

    /**
     * 存储要显示的数据
     */
    private ArrayList<PerfromShowItemData> mData;

    public PerformShowAdapter(ArrayList<PerfromShowItemData> mData) {
        this.mData = mData;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PerfromShowItemData perfromShowItemData = (PerfromShowItemData)mData.get(position);

        holder.tvCaption.setText(perfromShowItemData.Caption);
        holder.tvContent.setText(perfromShowItemData.Content);
        holder.tvTime.setText(perfromShowItemData.Time);
        Log.e("onBindViewHolder", "onBindViewHolder: " );

        holder.imgBk.setImageURI(Uri.parse(perfromShowItemData.imgBk));
        holder.imgHeadA.setImageURI(Uri.parse(perfromShowItemData.imgHeadA));
        holder.imgHeadB.setImageURI(Uri.parse(perfromShowItemData.imgHeadB));



        //这一句不是很流畅，因为没有Resize
        //view.setImageURI(obj.mUrl);

//        ImageRequest imageRequest =
//                ImageRequestBuilder.newBuilderWithSource(Uri.parse(perfromShowItemData.imgBk))
//                        .setResizeOptions(
//                                new ResizeOptions(holder.imgBk.getLayoutParams().width, holder.imgBk.getLayoutParams().height))
//                        .setProgressiveRenderingEnabled(true)
//                        .build();
//        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(imageRequest)
//                .setAutoPlayAnimations(true)
//                .build();
//        holder.imgBk.setController(draweeController);
//
//
//
//        ImageRequest imageRequest1 =
//                ImageRequestBuilder.newBuilderWithSource(Uri.parse(perfromShowItemData.imgHeadA))
//                        .setResizeOptions(
//                                new ResizeOptions(holder.imgHeadA.getLayoutParams().width, holder.imgHeadA.getLayoutParams().height))
//                        .setProgressiveRenderingEnabled(true)
//                        .build();
//        DraweeController draweeController1 = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(imageRequest1)
//                .setAutoPlayAnimations(true)
//                .build();
//        holder.imgHeadA.setController(draweeController1);
////
//        ImageRequest imageRequest2 =
//                ImageRequestBuilder.newBuilderWithSource(Uri.parse(perfromShowItemData.imgHeadB))
//                        .setResizeOptions(
//                                new ResizeOptions(holder.imgHeadB.getLayoutParams().width, holder.imgHeadB.getLayoutParams().height))
//                        .setProgressiveRenderingEnabled(true)
//                        .build();
//        DraweeController draweeController2 = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(imageRequest2)
//                .setAutoPlayAnimations(true)
//                .build();
//        holder.imgHeadB.setController(draweeController2);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //1、创建一个ViewHolder 把自己定义的View传递进去
        MyViewHolder myViewHolder = new MyViewHolder((LinearLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, null, false));
        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout mLayout;

        /**
         * 标题
         */
        public TextView tvCaption;

        /**
         * 内容
         */
        public TextView tvContent;

        /**
         * 背景图
         */
        public SimpleDraweeView imgBk;

        /**
         * 两个小头像A、B
         */
        public SimpleDraweeView imgHeadA;
        public SimpleDraweeView imgHeadB;

        /**
         * 发帖时间
         */
        public TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            mLayout = (LinearLayout)itemView;

            tvCaption = (TextView)mLayout.findViewById(R.id.tvCaption);
            tvContent = (TextView)mLayout.findViewById(R.id.tvContent);
            imgBk = (SimpleDraweeView) mLayout.findViewById(R.id.imgBk);
            imgHeadA = (SimpleDraweeView)mLayout.findViewById(R.id.imgHeadA);
            imgHeadB = (SimpleDraweeView)mLayout.findViewById(R.id.imgHeadB);
            tvTime = (TextView)mLayout.findViewById(R.id.tvTime);

            int a = JUtils.getScreenWidth() / 2;

            //设置图片的宽高
            imgBk.getLayoutParams().width = JUtils.getScreenWidth() / 2 - mLayout.getPaddingLeft() * 2 - ((RelativeLayout)mLayout.getChildAt(0)).getPaddingLeft() * 2;
            imgBk.getLayoutParams().height = imgBk.getLayoutParams().width;


            //设置头像图片为圆形
            GenericDraweeHierarchy gdh = new GenericDraweeHierarchyBuilder(mLayout.getResources())
//                .setPlaceholderImage(Drawables.sPlaceholderDrawable)
//                .setFailureImage(Drawables.sErrorDrawable)
                    .setRoundingParams(RoundingParams.asCircle())
                    .setProgressBarImage(new ProgressBarDrawable())
                    .build();
            imgHeadA.setHierarchy(gdh);
            GenericDraweeHierarchy gdh1 = new GenericDraweeHierarchyBuilder(mLayout.getResources())
//                .setPlaceholderImage(Drawables.sPlaceholderDrawable)
//                .setFailureImage(Drawables.sErrorDrawable)
                    .setRoundingParams(RoundingParams.asCircle())
                    .setProgressBarImage(new ProgressBarDrawable())
                    .build();
            imgHeadB.setHierarchy(gdh1);
        }
    }
}
