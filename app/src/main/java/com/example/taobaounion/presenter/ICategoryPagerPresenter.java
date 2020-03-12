package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.ICategoryPagerCallback;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/9 0009  23:16
 */
public interface ICategoryPagerPresenter extends IBasePresenter<ICategoryPagerCallback> {


    /**
     * Gets conten by category id.
     * 根据分类id内容获取
     *
     * @param categoryId the category id
     */
    void getContentByCategoryId(int categoryId);
    /**
     * Loader more.
     *
     * @param categoryId the category id
     */
    void loaderMore(int categoryId);

    /**
     * Reload.
     *
     * @param categoryId the category id
     */
    void reload(int categoryId);


}
