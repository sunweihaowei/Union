package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.Api;
import com.example.taobaounion.model.domain.Categories;
import com.example.taobaounion.presenter.IHomePresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.view.IHomeCallback;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

//由子类实施
public class HomePresenterImpl implements IHomePresenter {
    private IHomeCallback mIHomeCallback=null;

    //获取商品分类
    @Override
    public void getCategories() {
        if (mIHomeCallback != null) {
            mIHomeCallback.onLoading();
        }
        //加载的分类数据
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        Api api = retrofit.create(Api.class);//地址后面那一部分加上去
        Call<Categories> task = api.getCategories();//这个拿到bean
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                //数据结果
                int code = response.code();
                LogUtils.d(HomePresenterImpl.this,"result code is ------>"+code);
                if (response.code()== HttpURLConnection.HTTP_OK){
                    //请求成功
                    Categories categories = response.body();
                    if (mIHomeCallback !=null) {
                        if (categories==null || categories.getData().size()==0) {
                            mIHomeCallback.onEmpty();
                        }else {
                            mIHomeCallback.onCategoriesLoaded(categories);
                        }
                    }
                }else {
                    //请求失败
                    LogUtils.i(this,"failing");
                    if (mIHomeCallback != null) {
                        mIHomeCallback.onError();
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                LogUtils.e(HomePresenterImpl.this,"请求错误----------------------》" + t);
                if (mIHomeCallback != null) {
                    mIHomeCallback.onError();
                }
            }
        });
    }
    //    注册
    @Override
    public void registerViewCallback(IHomeCallback callback) {
        this.mIHomeCallback =callback;//这种也是变成成员变量
    }
    //    不注册
    @Override
    public void unregisterViewCallback(IHomeCallback callback) {
        mIHomeCallback =null;
    }
}
