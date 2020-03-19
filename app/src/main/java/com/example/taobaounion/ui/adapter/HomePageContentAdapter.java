package com.example.taobaounion.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.taobaounion.R;
import com.example.taobaounion.model.domain.HomePagerContentBean;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/13 0013  21:46
 */
public class HomePageContentAdapter extends RecyclerView.Adapter<HomePageContentAdapter.InnerHolder> {
    List<HomePagerContentBean.DataBean> dataBeans=new ArrayList<>();
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_pager_content, parent, false);
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HomePagerContentBean.DataBean dataBean = dataBeans.get(position);
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return dataBeans.size();
    }

    public void setData(List<HomePagerContentBean.DataBean> contents) {
        dataBeans.clear();
        dataBeans.addAll(contents);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_cover)
        public ImageView iv_cover;//图片封面

        @BindView(R.id.goods_title)
        public TextView tv_titile;//物品名

        @BindView(R.id.goods_save_money)
        public TextView TVGoodsSaveMoney;//省钱

        @BindView(R.id.goods_special_price)
        public TextView TVGoodsSpecialPrice;//省钱

        @BindView(R.id.goods_original_price)
        public TextView TVGoodsOriginalPrice;//原价

        @BindView(R.id.goods_purchased)
        public TextView TVGoodsPurchased;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void setData(HomePagerContentBean.DataBean dataBean) {
            Context context = itemView.getContext();
            tv_titile.setText(dataBean.getTitle());
            Glide.with(itemView.getContext()).load(UrlUtils.getCoverPath(dataBean.getPict_url())).into(iv_cover);

            String finalPrise = dataBean.getZk_final_price();
            long couponAmount = dataBean.getCoupon_amount();
            LogUtils.d(this,"finalPrise---->"+finalPrise);
            LogUtils.d(this,"couponAmount--->"+couponAmount);
            //打印"//gw.alicdn.com/bao/uploaded/i4/2206638096577/O1CN01p0B2z91ySJVfGqLCJ_!!0-item_pic.jpg"是没有协议开头的
            LogUtils.d(this,"dataBeanUrl------>" + dataBean.getPict_url());
            TVGoodsSaveMoney.setText(String.format(itemView.getContext().getString(R.string.text_goods_off_prise),couponAmount));
            float resultPrise=Float.parseFloat(finalPrise)-couponAmount;
            TVGoodsOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);//这里是划掉字
            TVGoodsOriginalPrice.setText(String.format(context.getString(R.string.text_goods_original_prise), finalPrise));
            TVGoodsSpecialPrice.setText(String.format("%.2f",resultPrise));
            TVGoodsPurchased.setText(String.format(context.getString(R.string.text_goods_sell_count),dataBean.getVolume()));

        }
    }
}
