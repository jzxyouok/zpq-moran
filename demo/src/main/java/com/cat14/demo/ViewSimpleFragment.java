package com.cat14.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewSimpleFragment extends Fragment {

    private String mTitle;
    public static final String BUNDLE_TITLE = "title";


    /**
     * 创建Fragment，并将存入值得Bundle给Fragment
     */
    public static ViewSimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);

        ViewSimpleFragment fragment = new ViewSimpleFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    /**
     * 根据已有的信息创建Fragment中的view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(BUNDLE_TITLE);
        }

        // 创建View，设置属性并返回
        TextView tv = new TextView(getActivity());
        tv.setText(mTitle);
        tv.setGravity(Gravity.CENTER);

        return tv;
    }



}
