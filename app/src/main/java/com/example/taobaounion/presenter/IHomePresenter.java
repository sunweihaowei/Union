package com.example.taobaounion.presenter;

import com.example.taobaounion.view.IHomeCallback;

//主持人，用于组织，接口
//接口是先设计好的，然后，让大家，来分工
public interface IHomePresenter {
    /*
    * 获取商品分类
    * 下面类型没有“{  }”的，都是要重写的
    * */
    void getCategories();//这里是异步的，你要求网络上拿，然后等，然后我们要干嘛
//    注册
    void registerCallback(IHomeCallback callback);
//    不注册
    void unregisterCallback(IHomeCallback callback);
}
