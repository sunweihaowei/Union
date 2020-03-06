package com.example.taobaounion.ui.fragment;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.domain.Categories;
import com.example.taobaounion.presenter.IHomePresenter;
import com.example.taobaounion.presenter.impl.HomePresenterImpl;
import com.example.taobaounion.ui.adapter.HomePagerAdapter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.view.IHomeCallback;
import com.google.android.material.tabs.TabLayout;
import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IHomeCallback {
    @BindView(R.id.home_pager)
    public ViewPager homePager;
    @BindView(R.id.home_indicator)
    public TabLayout mTabLayout;

    private IHomePresenter mIHomePresenter;
    private HomePagerAdapter mHomePagerAdapter;
    //    加载了布局
    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home;
    }
    @Override
    protected void initView(View rootView) {
        mTabLayout.setupWithViewPager(homePager);
        //给viewPager设置适配器
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());
        //设置适配器
        homePager.setAdapter(mHomePagerAdapter);
    }
    @Override
    protected void initPresenter() {
        //创建presenter,implements
        mIHomePresenter = new HomePresenterImpl();
        mIHomePresenter.registerCallback(this);
        //注册后要取消注册，不取消会导致内存泄露
    }
    @Override
    protected void loadData() {
//        载入数据
        mIHomePresenter.getCategories();
    }


    @Override
    public void onCategoriesLoaded(Categories categories) {
        setUpState(State.SUCCESS);
        LogUtils.d(this,"onCategoriessLoaded..");
        //        加载的数据会从这里出来，布局存在时加载数据
        if (mHomePagerAdapter!=null){
            mHomePagerAdapter.setCategories(categories);
        }
    }
    @Override
    public void onNetworkError() {
        setUpState(State.ERROR);
    }
    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }
    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }



    @Override
    protected void release() {
        //取消回调注册
        if (mIHomePresenter != null) {
            mIHomePresenter.unregisterCallback(this);
        }
    }
}































/* //就简单的加载了布局
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //打气筒打气，把布局充到容器里面，然后就没有了
        //每个都这样做，重复，区别：layout
        return inflater.inflate(R.layout.fragment_home,container,false);
    }*/
