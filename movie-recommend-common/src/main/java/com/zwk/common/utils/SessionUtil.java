package com.zwk.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-02 10:45
 * @ Description：session信息
 */
public class SessionUtil {
    public static HttpServletRequest request=null;
    //获取http请求
    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    //获取session信息
    public static String getSessionInfo(String msg){
        request=getHttpServletRequest();
        HttpSession session=request.getSession();
        Enumeration<String> enumLIst=request.getAttributeNames();
        return (String)session.getAttribute(msg);
    }

    public static void setSessionAttribute(String key ,String value){
        request=getHttpServletRequest();
        request.setAttribute(key, value);
    }
}
