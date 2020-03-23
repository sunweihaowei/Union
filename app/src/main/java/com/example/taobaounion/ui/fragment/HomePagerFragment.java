package com.example.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.model.domain.Categories;
import com.example.taobaounion.model.domain.HomePagerContentBean;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.presenter.impl.CategoryPagePresenterImpl;
import com.example.taobaounion.ui.adapter.HomePageContentAdapter;
import com.example.taobaounion.ui.adapter.LooperPagerAdapter;
import com.example.taobaounion.utils.Constants;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.SizeUtils;
import com.example.taobaounion.utils.ToastUtil;
import com.example.taobaounion.view.ICategoryPagerCallback;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.vondear.rxtool.view.RxToast;

import java.util.List;

import butterknife.BindView;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/3 0003  16:42
 * 每个ViewPager对应一页
 */
public class HomePagerFragment extends BaseFragment implements ICategoryPagerCallback {

    private ICategoryPagerPresenter mICategoryPagerPresenter;
    private int mMaterialId;
    private HomePageContentAdapter mHomePageContentAdapter;
    private LooperPagerAdapter mLooperPagerAdapter;
    //自己在里面new自己，这样可以HomePagerFragment.newInstance来调用自己
    //单例模式Singleton pattern
    //HomeFragment就会把category这个bean传过来，然后把他储存（setArguments）
    public static HomePagerFragment newInstance(Categories.DataBean category) {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGER_TITLE, category.getTitle());//加入
        bundle.putInt(Constants.KEY_HOME_PAGER_MATERIAL_ID, category.getId());
        homePagerFragment.setArguments(bundle);//将我们滑到的那一项数据绑定到homePagerFragment，然后它有数据

        return homePagerFragment;
    }

    @BindView(R.id.home_pager_content_list)
    public RecyclerView mContentList;
    @BindView(R.id.looper_pager)
    public ViewPager mLooperVP;
    @BindView(R.id.home_pager_title)
    public TextView currentCategoryTitleTv;
    @BindView(R.id.looper_point_container)
    public LinearLayout mLooperPointContainer;
    @BindView(R.id.home_pager_refresh)
    public TwinklingRefreshLayout homePagerTwinklingRefreshLayout;

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

//        创建适配器
        mLooperPagerAdapter = new LooperPagerAdapter();
//        设置适配器
        mLooperVP.setAdapter(mLooperPagerAdapter);
//        设置refresh的相关属性
        homePagerTwinklingRefreshLayout.setEnableRefresh(false);//不能下拉刷新
        homePagerTwinklingRefreshLayout.setEnableLoadmore(true);//可以加载更多
    }

    @Override
    protected void initListener() {
        mLooperVP.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /*LogUtils.d(this,"hello,我是onPageScrolled");*/
                Log.d("HomePagerFragment","hello111,我是onPageScrolled");
            }
            @Override
            public void onPageSelected(int position) {
                int dataSize = mLooperPagerAdapter.getDataSize();
                if (dataSize==0) {
                    return;
                }
                LogUtils.d(this,"hello,我是onPageonPageSelected");
                int targetPosition;
                LogUtils.d(this,"dataSize----->"+dataSize);
                targetPosition = position % dataSize;//这里是自己的方法
//                int targetPosition = position % mLooperPagerAdapter.getDataSize();//这里是最好的方法
//                LogUtils.d(this,""+mLooperPagerAdapter.getDataSize());
                //切换指示器
                updateLooperIndicator(targetPosition);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtils.d(this,"hello,我是onPageScrollStateChanged");
                LogUtils.d(this,"DataSize----->"+mLooperPagerAdapter.getDataSize());
            }
        });

        homePagerTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                去加载更多内容
                if (mICategoryPagerPresenter!=null) {
                    mICategoryPagerPresenter.loaderMore(mMaterialId);//这里输入id，告诉在哪里
                }
            }
        });
    }
   /**
    * @Author 孙伟豪
    * @Date 2020/3/19 0019 20:07
    * 更新指示灯方法
    */
    private void updateLooperIndicator(int targetPosition) {
        for (int i = 0; i < mLooperPointContainer.getChildCount(); i++) {
            View point=mLooperPointContainer.getChildAt(i);
            if (i == targetPosition) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
        }

    }

    /**
     * @Author 孙伟豪
     * @Date 2020/3/11 0011 10:42
     */
    @Override
    protected void initPresenter() {//打开界面执行
        mICategoryPagerPresenter = CategoryPagePresenterImpl.getsInstance();
        mICategoryPagerPresenter.registerViewCallback(this);//接口注册，注册后就不为null，
        //然后当前类来实现接口
    }

    /**
     * @Author 孙伟豪
     * @Date 2020/3/18 0018 11:51
     * 这里是加载数据
     */
    @Override
    protected void loadData() {
        int dataSize = mLooperPagerAdapter.getDataSize();
        LogUtils.d(this,"dataSize  ---  >"+dataSize);
        Bundle arguments = getArguments();//获取加入到homeAdapter添加时的滑到的那一项参数
        String title = arguments.getString(Constants.KEY_HOME_PAGER_TITLE);
        mMaterialId = arguments.getInt(Constants.KEY_HOME_PAGER_MATERIAL_ID);
        LogUtils.d(this, "title -- >" + title);
        LogUtils.d(this, "materiaId -- >" + mMaterialId);
        if (mICategoryPagerPresenter != null) {
            mICategoryPagerPresenter.getContentByCategoryId(mMaterialId);
        }
        if (currentCategoryTitleTv != null) {
            currentCategoryTitleTv.setText(title);
        }
    }

    /**
     * @Author 孙伟豪
     * @Date 2020/3/18 0018 11:50
     * 这里是已经加载适配器数据
     */
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
        ToastUtil.showToast("网络错误，请重试");
        if (homePagerTwinklingRefreshLayout!=null){//它还在加载，还没有结束，我们要手动结束
            homePagerTwinklingRefreshLayout.finishLoadmore();//设置加载了,即结束加载
        }
    }

    @Override
    public void onLoaderMoreEmpty() {
        ToastUtil.showToast("没有更多可以展示的商品");
        if (homePagerTwinklingRefreshLayout!=null){//它还在加载，还没有结束，我们要手动结束
            homePagerTwinklingRefreshLayout.finishLoadmore();//设置加载了,即结束加载
        }
    }

    @Override
    public void onLoaderMoreLoaded(List<HomePagerContentBean.DataBean> contents) {
        mHomePageContentAdapter.addData(contents);
        if (homePagerTwinklingRefreshLayout!=null){//它还在加载，还没有结束，我们要手动结束
            homePagerTwinklingRefreshLayout.finishLoadmore();//设置加载了,即结束加载
        }
        ToastUtil.showToast("加载了"+contents.size()+"条数据");
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLooperListLoaded(List<HomePagerContentBean.DataBean> contents) {
        LogUtils.d(this, "Looper size----->" + contents.size());
        //设置数据
        mLooperPagerAdapter.setData(contents);
        //中间点%数据的size不一定为0，所以显示的就不是第一个。
        //处理一下，变成第一个为0
        int dx=(Integer.MAX_VALUE/2)%contents.size();
        int targetCenterPosition=(Integer.MAX_VALUE/2)-dx;
        //设置到中间点
        mLooperVP.setCurrentItem(targetCenterPosition);
        List<HomePagerContentBean.DataBean> dataBeans = mLooperPagerAdapter.getDataBeans();
        LogUtils.d(this,"dateBeans-------->"+dataBeans.size());
        mLooperPointContainer.removeAllViews();
        //添加点
        for (int i = 0; i < contents.size(); i++) {
            View point = new View(getContext());
            int size = SizeUtils.dip2xp(getContext(), 8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);//长宽
            layoutParams.leftMargin = SizeUtils.dip2xp(getContext(), 5);
            layoutParams.rightMargin = SizeUtils.dip2xp(getContext(), 5);
            point.setLayoutParams(layoutParams);
            if (i == 0) {
                point.setBackgroundResource(R.drawable.shape_indicator_point_selected);
            } else {
                point.setBackgroundResource(R.drawable.shape_indicator_point_normal);
            }
            mLooperPointContainer.addView(point);
        }
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
