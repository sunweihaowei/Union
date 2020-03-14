package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallback;
import com.example.taobaounion.model.domain.HomePagerContentBean;

import java.util.List;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/10 0010  9:55
 */
public interface ICategoryPagerCallback extends IBaseCallback {

    /**
     * On content loaded.加载成功
     *
     * @param contents the contents
     */
    void onContentLoaded(List<HomePagerContentBean.DataBean> contents);

    int getCategoryId();

    /**
     * On loader more error.加载更多时，网络错误
     *
     */
    void onLoaderMoreError();

    /**
     * On loader more empty.没有更多内容
     *
     */
    void onLoaderMoreEmpty();

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
