package com.example.taobaounion.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.domain.Categories;
import com.example.taobaounion.model.domain.HomePagerContentBean;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.presenter.impl.CategoryPagePresenterImpl;
import com.example.taobaounion.utils.Constants;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.view.ICategoryPagerCallback;

import java.util.List;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/3 0003  16:42
 */
public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mICategoryPagerPresenter;

    //自己在里面new自己，这样可以HomePagerFragment.newInstance来调用自己
    //单例模式Singleton pattern
    public static HomePagerFragment newInstance(Categories.DataBean category){
        HomePagerFragment homePagerFragment=new HomePagerFragment();

        Bundle bundle=new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE,category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID,category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        setUpState(State.SUCCESS);
    }
    /**
     * @Author 孙伟豪
     * @Date 2020/3/11 0011 10:42
     */
    @Override
    protected void initPresenter() {
        mICategoryPagerPresenter = CategoryPagePresenterImpl.getsInstance();
        mICategoryPagerPresenter.registerViewCallback(this);
        //然后当前类来实现接口
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        int materialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
        LogUtils.d(this,"title -- >" +title);
        LogUtils.d(this,"materiaId -- >" +materialId);
        //TODO:加载数据
        if (mICategoryPagerPresenter !=null) {
            mICategoryPagerPresenter.getContentByCategoryId(materialId);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContentBean> contents) {

    }

    @Override
    public void onloading(int categoryId) {

    }

    @Override
    public void onError(int categoryId) {

    }

    @Override
    public void onEmpty(int categoryId) {

    }

    @Override
    public void onLoaderMoreError(int categoryId) {

    }

    @Override
    public void onLoaderMoreEmpty(int categoryId) {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContentBean.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContentBean.DataBean> contents) {

    }
    /**
     *
     * @Author 孙伟豪
     * @Date 2020/3/11 0011 10:43
     * 释放掉
     */
    @Override
    protected void release() {
        if (mICategoryPagerPresenter !=null) {
            mICategoryPagerPresenter.unregisterViewCallback(this);
        }
    }
}
