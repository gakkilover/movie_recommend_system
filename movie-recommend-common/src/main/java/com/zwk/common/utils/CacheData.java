/*
package com.zwk.common.utils;


import com.zwk.movie_recommend.service.UserService;

import java.util.List;


*/
/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-14 17:16
 * @ Description：
 *//*


public class CacheData {


    private UserService userService=(UserService) SpringContextUtils.getBean("userService");


    */
/**
     * 数据存入缓存（更新操作）
     *//*


    public void refreshCache(){
        List userList=userService.userList(null);
        CacheUtil.set(CacheUtil.Table.USER.getKey(),userList);

    }


}
*/
