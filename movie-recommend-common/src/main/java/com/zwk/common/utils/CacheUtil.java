package com.zwk.common.utils;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in   2018-12-24 16:27
 * @ Description：缓存工具类
 */
public class CacheUtil {

    /**
     * 缓存表数据
     */
    public enum Table{

        FOOD("FOOD","菜品表"),
        USER("USER","用户表");


        //定义为final防止被修改
        private final String key;
        private final String value;
        /**
         * 私有构造器
         */
        private Table(String key,String value){
            this.key=key;
            this.value=value;
        }

        //根据key获取枚举表数据
        public static Table getEnumByKey(String key){
            if(key==null){
                return null;
            }
            for (Table table: Table.values()) {
                if(table.getKey().equals(key)){
                    return table;
                }
            }
            return null;
        }


        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
    }

    //取缓存数据
    public static Object get(String key) {
        return CacheLocal.get(key);
    }
    //存缓存数据
    public static void set(String key,Object obj) {
         CacheLocal.set(key,obj);
    }
}
