package com.example.taobaounion.utils;

/**
 * 项目名称：TaobaoUnion
 * 创建人：sunweihao
 * 创建时间：2020/3/11 0011  11:27
 */
public class UrlUtils {
    public static String createHomePagerUrl(int materialId,int page){
        ///discovery/{materialId}/{page}
        return "discovery"+"/"+materialId+"/"+page;
    }
}
