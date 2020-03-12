package com.example.taobaounion.base;

import com.example.taobaounion.view.IHomeCallback;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/10 0010  11:25
 */
public interface IBasePresenter<T> {
    /**
     * Register view callback.
     *
     * @param callback the callback
     */
    void registerViewCallback(T callback);

    /**
     * Unregister view callback.
     *
     * @param callback the callback
     */
    void unregisterViewCallback(T callback);
}
