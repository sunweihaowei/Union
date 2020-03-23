package com.example.taobaounion.utils;

import android.widget.Toast;

import com.example.taobaounion.base.BaseApplication;

import java.util.PrimitiveIterator;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/23 0023  15:25
 */
public class ToastUtil {
    private static Toast sToast;
    public static void showToast(String tips){
        if (sToast==null){
            sToast=Toast.makeText(BaseApplication.getAppContext(),tips,Toast.LENGTH_SHORT);
        }else {
            sToast.setText(tips);
        }
        sToast.show();
    }
}
