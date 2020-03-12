package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.IHomeCallback;

/**
 * The interface Home presenter.
 */
//主持人，用于组织，接口
//接口是先设计好的，然后，让大家，来分工
public interface IHomePresenter extends IBasePresenter<IHomeCallback> {
    /**
     * Gets categories.
     */
    /*
    * 获取商品分类
    * 下面类型没有“{  }”的，都是要重写的
    * */
    void getCategories();//这里是异步的，你要求网络上拿，然后等，然后我们要干嘛


}
