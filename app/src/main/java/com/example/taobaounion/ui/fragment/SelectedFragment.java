package com.example.taobaounion.ui.fragment;

import android.view.View;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;

public class SelectedFragment extends BaseFragment {

    @Override
    protected int getRootViewResId() {
        //将界面填到BaseFragment的坑里
        return R.layout.fragment_selected;
    }
    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }
}
