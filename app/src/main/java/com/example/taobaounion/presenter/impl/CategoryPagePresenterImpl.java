package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.Api;
import com.example.taobaounion.model.domain.HomePagerContentBean;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 泛型
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/10 0010  11:36
 */
public class CategoryPagePresenterImpl implements ICategoryPagerPresenter {

    private Map<Integer, Integer> pagesInfoHashMap = new HashMap<>();//集合保存页码

    public static final int DEFAULT_PAGE = 1;

    private CategoryPagePresenterImpl() {

    }

    private static ICategoryPagerPresenter sInstance = null;

    //饿汉式，跟线程没有关系
    public static ICategoryPagerPresenter getsInstance() {
        if (sInstance == null) {
            sInstance = new CategoryPagePresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {//页id，推选：9660，食品：9649
        for (ICategoryPagerCallback callback : callbacks) {//我们切换好多次，导致有好多个布局
            if (callback.getCategoryId()==categoryId) {//这里把所有callbacks都弄出来，进行比较，如果是就执行
                callback.onLoading();
            }
        }
        //根据分类id去加载内容
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();//随便得到头部
        Api api = retrofit.create(Api.class);//这里选择尾部还是待定
        Integer targetPage = pagesInfoHashMap.get(categoryId);//得到一个页码9649那样子
        if (targetPage == null) {
            targetPage = DEFAULT_PAGE;//1
            pagesInfoHashMap.put(categoryId, targetPage);//页码key来拿那一页
        }
        LogUtils.d(this,"categoryId--->"+categoryId);
        Call<HomePagerContentBean> task = createTask(categoryId, api, targetPage);
        task.enqueue(new Callback<HomePagerContentBean>() {
            @Override
            public void onResponse(Call<HomePagerContentBean> call, Response<HomePagerContentBean> response) {
                int code = response.code();
                LogUtils.d(CategoryPagePresenterImpl.this, "code===>" + code);
                if (code == HttpURLConnection.HTTP_OK) {
                    HomePagerContentBean pagerContentBean = response.body();
                    LogUtils.d(CategoryPagePresenterImpl.this, "pageContent -->" + pagerContentBean);
                    //把数据给到UI更新
                    handleHomePageContentResult(pagerContentBean,categoryId);
                }else {
                    handleNetworkError(categoryId);
                }
            }
            @Override
            public void onFailure(Call<HomePagerContentBean> call, Throwable t) {
                LogUtils.d(CategoryPagePresenterImpl.this, "onFailure--->" + t.toString());
                handleNetworkError(categoryId);
            }
        });
    }

    private Call<HomePagerContentBean> createTask(int categoryId, Api api, Integer targetPage) {
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);//页码加哪一页，这里都是第一页
        LogUtils.d(CategoryPagePresenterImpl.this, "homePagerUrl-- >" + homePagerUrl);
        return api.getHomePageContentBean(homePagerUrl);
    }

    private void handleNetworkError(int categoryId) {
        for (ICategoryPagerCallback callback : callbacks) {//不是就不鸟他，是就执行
            if (callback.getCategoryId()==categoryId) {
                callback.onError();
            }
        }
    }

    private void handleHomePageContentResult(HomePagerContentBean pagerContentBean, int categoryId) {
        //通知UI层更新数据
        List<HomePagerContentBean.DataBean> data = pagerContentBean.getData();
        for (ICategoryPagerCallback callback : callbacks) {
            if (callback.getCategoryId()==categoryId) {
                if (pagerContentBean==null || pagerContentBean.getData().size()==0) {
                    callback.onEmpty();
                }else {
                    List<HomePagerContentBean.DataBean> looperData = data.subList(data.size() - 5, data.size());//返回最后5个
                    callback.onLooperListLoaded(looperData);
                    callback.onContentLoaded(data);
                }
            }
        }
    }
    @Override
    public void loaderMore(int categoryId) {
        //加载更多，
    }

    @Override
    public void reload(int categoryId) {

    }

    private List<ICategoryPagerCallback> callbacks = new ArrayList<>();//这里ViewPager有好多个

    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {//把使用这个方法的界面注册过来，calllback==那边的界面
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);//
        }
    }

    @Override
    public void unregisterViewCallback(ICategoryPagerCallback callback) {
        callbacks.remove(callback);
    }


}
