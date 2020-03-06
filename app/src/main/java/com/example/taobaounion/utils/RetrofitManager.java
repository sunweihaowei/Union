package com.example.taobaounion.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * The type Retrofit manager.把REtrofit代码抽取出来，简便化
 */
public class RetrofitManager {
    //自己new自己了
    private static final RetrofitManager ourInstance = new RetrofitManager();
    private final Retrofit mRetrofit;

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RetrofitManager getInstance() {
        return ourInstance;
    }
//这里是他的构造方法
    private RetrofitManager() {
        //创建retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get retrofit retrofit.
     *
     * @return the retrofit
     */
    public Retrofit getRetrofit(){
        return mRetrofit;
    }
    //这是单例模式，优点比较快
//这是一个retrofit的帮助类
}
