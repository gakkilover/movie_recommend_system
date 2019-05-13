package com.zwk.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-02 09:04
 * @ Description：缓存数据
 */
public class CacheLocal {

    private static Map<String,Object> cacheMap=new HashMap<>();

    /**
     * 取缓存数据
     */
    public static Object get(String key){
        return cacheMap.get(key);
    }

    /**
     * 存缓存数据
     */
    public static void set(String key,Object obj){
        cacheMap.put(key,obj);
    }
}
