package com.example.taobaounion.model;

import com.example.taobaounion.model.domain.Categories;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * The interface Api.
 */
public interface Api {
    /**
     * Gets categories.是一个bean（类别）
     *
     * @return the categories返回类别
     * @GET 就相当于Categories
     */
    @GET("discovery/categories")
    Call<Categories> getCategories();
}
