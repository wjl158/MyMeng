package com.example.admin.mymeng.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.mymeng.R;

/**
 * Created by admin on 2016/06/21.
 * 声吧
 */
public class FragmentMessage extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_message,container,false);
//        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
////        getArguments()
//        Bundle bundle = getArguments();
//        content =  bundle.getString("value");
//        txt_content.setText(content);

        return view;
    }

}
