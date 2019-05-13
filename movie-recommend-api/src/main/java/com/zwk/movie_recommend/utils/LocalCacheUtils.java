package com.zwk.movie_recommend.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-29 15:50
 * @ Description：本地缓存类，用于存储暂存数据
 */
public class LocalCacheUtils {
    public static Map data = new HashMap();

    public static void set(String key, Object value){
        data.put(key, value);
    }

    public static Object get(String key){
        return data.get(key);
    }

}
