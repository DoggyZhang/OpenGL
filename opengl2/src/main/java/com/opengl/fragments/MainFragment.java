package com.opengl.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.opengl.R;
import com.opengl.fragments.base.BaseFragment;

/**
 * Created by Administrator on 2016/11/21.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener {
    @Override
    public int getLayoutID() {
        return R.layout.fragment_main;
    }

    @Override
    public void initArgument(Bundle arguments) {

    }

    @Override
    public void initView(View root) {
        root.findViewById(R.id.btn_1).setOnClickListener(this);
        root.findViewById(R.id.btn_2).setOnClickListener(this);
        root.findViewById(R.id.btn_3).setOnClickListener(this);
        root.findViewById(R.id.btn_4).setOnClickListener(this);
        root.findViewById(R.id.btn_5).setOnClickListener(this);
        root.findViewById(R.id.btn_6).setOnClickListener(this);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.btn_1:
                fragmentTransaction.replace(R.id.MainContainer, new Fragment01());
                break;
            case R.id.btn_2:
                fragmentTransaction.replace(R.id.MainContainer, new Fragment02());
                break;
            case R.id.btn_3:
                fragmentTransaction.replace(R.id.MainContainer, new Fragment03());
                break;
            case R.id.btn_4:
                fragmentTransaction.replace(R.id.MainContainer, new Fragment04());
                break;
            case R.id.btn_5:
                fragmentTransaction.replace(R.id.MainContainer, new Fragment05());
                break;
            case R.id.btn_6:
                fragmentTransaction.replace(R.id.MainContainer, new Fragment06());
                break;
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
