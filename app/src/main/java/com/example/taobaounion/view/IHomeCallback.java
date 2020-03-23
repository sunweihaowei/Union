package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallback;
import com.example.taobaounion.model.domain.Categories;

/**
 * The interface Home callback.
 */
//这里是通知UI更新
public interface IHomeCallback extends IBaseCallback {
    /**
     * On categories loaded.
     *接口是不用reture直接有
     * @param categories the categories
     */
    /*传入bean类Categories，进行加载
    * 已经加载
    * */
    void onCategoriesLoaded(Categories categories);
}
