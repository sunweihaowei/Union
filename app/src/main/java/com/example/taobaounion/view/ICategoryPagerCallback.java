package com.example.taobaounion.view;

import com.example.taobaounion.model.domain.HomePagerContentBean;

import java.util.List;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/10 0010  9:55
 */
public interface ICategoryPagerCallback {

    /**
     * On content loaded.加载成功
     *
     * @param contents the contents
     */
    void onContentLoaded(List<HomePagerContentBean> contents);

    /**
     * Onloading.加载中
     *
     * @param categoryId the category id
     */
    void onloading( int categoryId);//对应的告诉哪个界面更新

    /**
     * On error.加载错误
     *
     * @param categoryId the category id
     */
    void onError(int categoryId);

    /**
     * On empty.加载为空
     *
     * @param categoryId the category id
     */
    void onEmpty(int categoryId);

    /**
     * On loader more error.加载更多时，网络错误
     *
     * @param categoryId the category id
     */
    void onLoaderMoreError(int categoryId);

    /**
     * On loader more empty.没有更多内容
     *
     * @param categoryId the category id
     */
    void onLoaderMoreEmpty(int categoryId);

    /**
     * On loader more loaded.加载更多内容了
     *
     * @param contents the contents
     */
    void onLoaderMoreLoaded(List<HomePagerContentBean.DataBean> contents);

    /**
     * On looper list loaded.轮播图内容加载了
     *
     * @param contents the contents
     */
    void onLooperListLoaded(List<HomePagerContentBean.DataBean> contents);
}
