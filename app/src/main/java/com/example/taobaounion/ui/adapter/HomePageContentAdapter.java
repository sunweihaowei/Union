package com.example.taobaounion.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taobaounion.R;
import com.example.taobaounion.model.domain.HomePagerContentBean;

import java.util.ArrayList;
import java.util.List;

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
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
