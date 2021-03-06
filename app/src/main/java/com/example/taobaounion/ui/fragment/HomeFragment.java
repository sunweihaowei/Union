package com.example.taobaounion.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * The type Home fragment.
 */
public class HomeFragment extends BaseFragment implements IHomeCallback {
    @BindView(R.id.home_pager)
    public ViewPager homePager;
    @BindView(R.id.home_indicator)
    public TabLayout mTabLayout;
    private IHomePresenter mIHomePresenter;
    private HomePagerAdapter mHomePagerAdapter;
    //    加载了布局
    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
//        return super.loadRootView(inflater, container);//下面是父亲的默认加载布局
        return inflater.inflate(R.layout.base_home_fragment_layout,container,false);// 这里重写了父亲的方法，替换了加载的layout
    }
    @Override
    protected int getRootViewResId() {
//        填到BaseFragment的坑里
        return R.layout.fragment_home;
    }
    @Override
    protected void initView(View rootView) {
        mTabLayout.setupWithViewPager(homePager);
        //给viewPager设置适配器
        mHomePagerAdapter = new HomePagerAdapter(getChildFragmentManager());//操作子类
        //设置适配器
        homePager.setAdapter(mHomePagerAdapter);
    }

    @Override
    protected void initPresenter() {
        //创建presenter,implements
        mIHomePresenter=new HomePresenterImpl();//
        mIHomePresenter.registerViewCallback(this);
        //注册后要取消注册，不取消会导致内存泄露
    }

    @Override
    protected void loadData() {
//        载入数据
        mIHomePresenter.getCategories();
    }

    /**
     * @Author 孙伟豪
     * @Date 2020/3/20 0020 18:37
     * 这里后面的方法是被调用才会执行，即接口要实现后，要被调用才执行
     */

    @Override
    public void onCategoriesLoaded(Categories categories) {
        setUpState(State.SUCCESS);
        LogUtils.d(this,"onCategoriesLoaded..");
        //        加载的数据会从这里出来，布局存在时加载数据
        if (mHomePagerAdapter!=null){
//            homePager.setOffscreenPageLimit(categories.getData().size());//这样占内存
            mHomePagerAdapter.setCategories(categories);
        }
    }
    @Override
    public void onError() {
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
            mIHomePresenter.unregisterViewCallback(this);
        }
    }
    /**
     * @Author 孙伟豪
     * @Date 2020/3/23 0023 18:26
     * 重新加载
     */
    @Override
    protected void onRetryClick() {
        //网络错误，点击重试，重新加载分类内容
        if (mIHomePresenter!=null){
            mIHomePresenter.getCategories();
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
