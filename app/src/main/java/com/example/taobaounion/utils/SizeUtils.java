package com.example.taobaounion.utils;

import android.content.Context;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/18 0018  13:58
 */
public class SizeUtils {
    public static int dip2xp(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*scale + 0.5f);//缩放
    }
}
