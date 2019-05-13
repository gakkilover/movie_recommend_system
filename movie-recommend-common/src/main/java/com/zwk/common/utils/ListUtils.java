package com.zwk.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListUtils {
    /**
     * 将set转换为list.
     * @param set
     * @return
     * @author Luxb
     * 2014年11月27日 Luxb
     */
    public static List toList(Set set) {
        List retList = new ArrayList();
        if (set != null) {
            retList.addAll(set);
        }
        return retList;
    }

    /**
     * 获取搜索记录前100条 ，以防渲染数据过慢 影响用户体验
     * @param list
     * @return
     */
    public static List getTop10(List list){
        if (list.size() >= 100){
            return list.subList(0, 100);
        }else {
            return list;
        }
    }
    
    public static boolean isEmpty(List list){
    	return list == null || list.isEmpty();
    }
    
    public static boolean isNotEmpty(List list){
    	return !isEmpty(list);
    }

}
