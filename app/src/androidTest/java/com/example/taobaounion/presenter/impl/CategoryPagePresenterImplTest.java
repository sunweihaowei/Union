package com.example.taobaounion.presenter.impl;

import android.util.Log;

import com.example.taobaounion.presenter.ICategoryPagerPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/23 0023  8:55
 */
public class CategoryPagePresenterImplTest {
    @Before
    public void setUp() throws Exception {
     CategoryPagePresenterImpl.getsInstance().getContentByCategoryId(9660);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getsInstance() {

    }

    @Test
    public void getContentByCategoryId() {
    }

    @Test
    public void loaderMore() {
    }

    @Test
    public void reload() {
    }

    @Test
    public void registerViewCallback() {
    }

    @Test
    public void unregisterViewCallback() {
    }
}