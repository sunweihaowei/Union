package com.example.taobaounion.ui.activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.ui.fragment.HomeFragment;
import com.example.taobaounion.ui.fragment.RedPacketFragment;
import com.example.taobaounion.ui.fragment.SearchFragment;
import com.example.taobaounion.ui.fragment.SelectedFragment;
import com.example.taobaounion.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity{
    private BaseFragment mHomeFragment;
    private BaseFragment mRedPacketFragmet;
    private BaseFragment mSelectedFragment;
    private BaseFragment mSearchFragment;
    private FragmentManager mFm;
    private Unbinder mBind;
    @BindView(R.id.main_navigation_bar) BottomNavigationView getMain_navigation_bar;
    @BindView(R.id.main_page_container) FrameLayout main_page_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(MainActivity.this);
        initFragment();
        listenter();

    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mRedPacketFragmet = new RedPacketFragment();
        mSelectedFragment = new SelectedFragment();
        mSearchFragment = new SearchFragment();
        mFm = getSupportFragmentManager();
        switchFragment(mHomeFragment);
    }


    private void listenter() {
        getMain_navigation_bar.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                LogUtils.d(this,"切换到首页");
                switchFragment(mHomeFragment);
            } else if (item.getItemId() == R.id.select) {
                LogUtils.w(this, "切换到精选");
                switchFragment(mSelectedFragment);
            } else if (item.getItemId() == R.id.red_packet) {
                LogUtils.i(this, "切换到特惠");
                switchFragment(mRedPacketFragmet);
            } else if (item.getItemId() == R.id.search) {
                LogUtils.e(this, "切换到搜索");
                switchFragment(mSearchFragment);
            }
            return true;
        });
    }

    private void switchFragment(BaseFragment targetFragment) {
        FragmentTransaction fragmentTransaction = mFm.beginTransaction();
        fragmentTransaction.replace(R.id.main_page_container,targetFragment);
        fragmentTransaction.commit();
    }
}
