package com.example.taobaounion.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.taobaounion.model.domain.Categories;
import com.example.taobaounion.ui.fragment.HomePagerFragment;
import com.example.taobaounion.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/3 0003  15:56
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    private List<Categories.DataBean> categoryList=new ArrayList<>();
    public HomePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categoryList.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {//这里是得到我们滑到的那一项
        LogUtils.d(this,"getItem - >"+position);
        Categories.DataBean dataBean = categoryList.get(position);
        HomePagerFragment homePagerFragment=HomePagerFragment.newInstance(dataBean);//这里是输入那一项的ID
        return homePagerFragment;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    /**
     * Set categories.
     *
     * @param categories the categories就是bean
     *                   清理
     *                   加入数据
     *                   刷新
     */
    public void setCategories(Categories categories){
        categoryList.clear();
        List<Categories.DataBean> data = categories.getData();
        categoryList.addAll(data);
        notifyDataSetChanged();
    }
}
