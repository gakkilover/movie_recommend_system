package com.zwk.movie_recommend.redis;

import com.zwk.movie_recommend.utils.CookieUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-13 10:20
 * @ Description：
 */
public class SessionCookie {
    /**
     * 获取Request对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)

                RequestContextHolder.getRequestAttributes()).getRequest();
    }



    public static String getUserJSESSIONId(){
        String ticket=null;
        if(null!=getRequest()){
            ticket=CookieUtils.getCookieValue(getRequest(), "SESSION_TCIKET");//todo
        }
        return ticket;
    }
}
