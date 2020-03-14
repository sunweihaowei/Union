package com.example.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.domain.Categories;
import com.example.taobaounion.model.domain.HomePagerContentBean;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.presenter.impl.CategoryPagePresenterImpl;
import com.example.taobaounion.ui.adapter.HomePageContentAdapter;
import com.example.taobaounion.ui.adapter.HomePagerAdapter;
import com.example.taobaounion.utils.Constants;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.view.ICategoryPagerCallback;

import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/3 0003  16:42
 */
public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mICategoryPagerPresenter;
    private int mMaterialId;
    private HomePageContentAdapter mHomePageContentAdapter;

    //自己在里面new自己，这样可以HomePagerFragment.newInstance来调用自己
    //单例模式Singleton pattern
    public static HomePagerFragment newInstance(Categories.DataBean category) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();

        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID, category.getId());
        homePagerFragment.setArguments(bundle);
        return homePagerFragment;
    }

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mContentList;

    @Override
    protected int getRootViewResId() {
        return R.layout.fragment_home_pager;
    }

    @Override
    protected void initView(View rootView) {
        //设置布局管理器
        mContentList.setLayoutManager(new LinearLayoutManager(getContext()));
        mContentList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.top = 5;
                outRect.bottom = 5;
            }
        });
                //创建适配器
                mHomePageContentAdapter = new HomePageContentAdapter();
        //设置适配器
        mContentList.setAdapter(mHomePageContentAdapter);
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
        Bundle arguments = getArguments();//获取参数
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        mMaterialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
        LogUtils.d(this, "title -- >" + title);
        LogUtils.d(this, "materiaId -- >" + mMaterialId);
        if (mICategoryPagerPresenter != null) {
            mICategoryPagerPresenter.getContentByCategoryId(mMaterialId);
        }
    }

    @Override
    public void onContentLoaded(List<HomePagerContentBean.DataBean> contents) {
        //数据列表加载到了
       mHomePageContentAdapter.setData(contents);
        setUpState(State.SUCCESS);
    }

    @Override
    public int getCategoryId() {
        return mMaterialId;
    }

    @Override
    public void onLoading() {
        setUpState(State.LOADING);
    }

    @Override
    public void onError() {
        //网络错误
        setUpState(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setUpState(State.EMPTY);
    }

    @Override
    public void onLoaderMoreError() {

    }

    @Override
    public void onLoaderMoreEmpty() {

    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContentBean.DataBean> contents) {

    }

    @Override
    public void onLooperListLoaded(List<HomePagerContentBean.DataBean> contents) {

    }

    /**
     * @Author 孙伟豪
     * @Date 2020/3/11 0011 10:43
     * 释放掉
     */
    @Override
    protected void release() {
        if (mICategoryPagerPresenter != null) {
            mICategoryPagerPresenter.unregisterViewCallback(this);
        }
    }
}
