package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.Api;
import com.example.taobaounion.model.domain.HomePagerContentBean;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.ICategoryPagerCallback;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**泛型
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/10 0010  11:36
 */
public class CategoryPagePresenterImpl implements ICategoryPagerPresenter {

    private Map<Integer,Integer> pagesInfoHashMap =new HashMap<>();

    public static final int DEFAULT_PAGE=1;

    private CategoryPagePresenterImpl(){

    }

    private static ICategoryPagerPresenter sInstance =null;
    //饿汉式，跟线程没有关系
    public static ICategoryPagerPresenter getsInstance(){
        if (sInstance ==null){
            sInstance =new CategoryPagePresenterImpl();
        }
        return sInstance;
    }
    @Override
    public void getContentByCategoryId(int categoryId) {
        //根据分类id去加载内容
        Retrofit retrofit= RetrofitManager.getInstance().getRetrofit();
        Api api=retrofit.create(Api.class);
        Integer targetPage= pagesInfoHashMap.get(categoryId);
        if (targetPage ==null){
            targetPage=DEFAULT_PAGE;
            pagesInfoHashMap.put(categoryId,targetPage);
        }
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, targetPage);
        LogUtils.d(CategoryPagePresenterImpl.this,"home pager url -- >"+homePagerUrl);
        Call<HomePagerContentBean> task = api.getHomePageContentBean(homePagerUrl);
        task.enqueue(new Callback<HomePagerContentBean>() {
            @Override
            public void onResponse(Call<HomePagerContentBean> call, Response<HomePagerContentBean> response) {
                int code = response.code();
                LogUtils.d(CategoryPagePresenterImpl.this,"code===>"+code);
                if (code== HttpURLConnection.HTTP_OK){
                    HomePagerContentBean pagerContentBean = response.body();
                    LogUtils.d(CategoryPagePresenterImpl.this,"pageContent -->"+pagerContentBean);
                }
            }

            @Override
            public void onFailure(Call<HomePagerContentBean> call, Throwable t) {
                LogUtils.d(CategoryPagePresenterImpl.this,"onFailure--->"+t.toString());

            }
        });


    }

    @Override
    public void loaderMore(int categoryId) {

    }

    @Override
    public void reload(int categoryId) {

    }

    @Override
    public void registerViewCallback(ICategoryPagerCallback callback) {

    }

    @Override
    public void unregisterViewCallback(ICategoryPagerCallback callback) {

    }






}
