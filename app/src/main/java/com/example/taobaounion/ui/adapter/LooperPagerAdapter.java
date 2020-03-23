package com.example.taobaounion.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.taobaounion.model.domain.HomePagerContentBean;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/18 0018  10:25
 */
public class LooperPagerAdapter extends PagerAdapter {
    private List<HomePagerContentBean.DataBean> mDataBeans =new ArrayList<>();

    public List<HomePagerContentBean.DataBean> getDataBeans() {
        return mDataBeans;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition=position % mDataBeans.size();//取模算法
        LogUtils.d(this,"mDataBeans"+mDataBeans.size());
        //size = 5 == >position % mData.size();
        //a % b = a - (a/b) * b
        //0 --> 0(1 --> 1):
        //1 --> 1
        //2 --> 2
        //3 --> 3
        //4 --> 4
        //5 --> 0
        //6 --> 1
        HomePagerContentBean.DataBean dataBean = mDataBeans.get(realPosition);
        String coverPath = UrlUtils.getCoverPath(dataBean.getPict_url());//得到图片尾部地址
        //建立图片
        ImageView iv=new ImageView(container.getContext());
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(container.getContext()).load(coverPath).into(iv);
        container.addView(iv);
        return iv;
    }

    public int getDataSize(){
        return  mDataBeans.size();//打出的结果是0
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    public void setData(List<HomePagerContentBean.DataBean> contents) {
        mDataBeans.clear();
        mDataBeans.addAll(contents);
        /* This method should be called by the application if the data backing this adapter has changed
          and associated views should update.*/
        notifyDataSetChanged();
        LogUtils.d(this,"contents----->"+mDataBeans.size());//打出的结果是5
    }



    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
