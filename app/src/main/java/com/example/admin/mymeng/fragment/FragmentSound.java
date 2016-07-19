package com.example.admin.mymeng.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.admin.mymeng.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;

/**
 * Created by admin on 2016/06/21.
 * 声吧
 */
public class FragmentSound extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_sound,container,false);
//        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
////        getArguments()
//        Bundle bundle = getArguments();
//        content =  bundle.getString("value");
//        txt_content.setText(content);

        return view;
    }

}
