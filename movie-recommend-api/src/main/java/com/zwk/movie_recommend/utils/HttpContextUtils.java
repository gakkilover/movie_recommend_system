package com.zwk.movie_recommend.utils;
import com.zwk.common.utils.JSONUtils;
import com.zwk.movie_recommend.redis.RedisUtils;
import com.zwk.movie_recommend.redis.SessionCookie;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-13 10:10
 * @ Description：
 */
public class HttpContextUtils {
    /**
     * 获取请求对象
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)

                RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 从请求体的session中获取数据
     * @param key
     * @return
     */
    public static Object  getValueInSession(String key){
        return getRequest().getSession().getAttribute("");
    }

    /**
     * 将数据存到session中
     * @param key
     * @param value
     */
    public static void  setValueInSession(String key, Object value){
        getRequest().getSession().setAttribute(key, value);
    }

    /**
     * 获取用户存放在Map域对象里的信息
     * @param <T>
     * @param key 键
     * @param clazz 返回的类型
     * @return null or Object
     */
    public static <T> T getSessionAttribute(String key, Class<T> clazz) {
        Map<String, Object> map = getSessionMapForRedis();
        if (map == null) {
            return null;
        } else {
            Object obj = map.get(key);
            if (obj == null) {
                return null;
            }
            String t = obj.toString();
            T o = com.zwk.common.utils.JSONUtils.jsonToObject(t, clazz);
            return o;
        }
    }

    /**
     * 获取整个session（Map）
     *
     * @return
     */
    private static Map<String, Object> getSessionMapForRedis() {
        String ticket = SessionCookie.getUserJSESSIONId();
        if (ticket == null) {
            return null;
        }
        Map<String, Object> map = null;
        if (ticket != null) {
            map = JSONUtils.jsonToMap(com.zwk.movie_recommend.redis.RedisUtils.redisUtils.get(ticket));
        }
        return map;
    }

    /**
     * 直接对在Redis里获取数据
     * @param key
     * @return
     */

    public static String getRedisSessionAttribute(String key) {

        String value=RedisUtils.redisUtils.get(key);
        return value;

    }

    /**
     * 直接在Redis设置数据
     * @param key
     * @param value
     */
    public static void setRedisSessionAttribute(String key, String value) {

        RedisUtils.redisUtils.set(key,value, 1800);

    }

    public static void setRedisForByte(byte[] key, byte[] value) {

        RedisUtils.redisUtils.set(key, value, 1800);

    }




    /**
     * 用户登录创建session ---redis
     *
     * @param key
     * @param value
     */
    public static void setFirstSessionAttribute(String key, Object value) {
        Map<String, Object> sessionMap = new HashMap<String, Object>();
        sessionMap.put(key, value);
        RedisUtils.redisUtils.set(key, JSONUtils.mapToJsonString(sessionMap), 7200);

    }




    /**
     * 通用session操作
     *
     * @param key
     * @param value
     */
    public static void setSessionAttribute(String key, Object value) {

        Map<String, Object> map = getSessionMapForRedis();

        map.put(key, value);

        RedisUtils.redisUtils.set(SessionCookie.getUserJSESSIONId(), JSONUtils.mapToJsonString(map), 1800);

    }

    /**
     * 可以设置整个Map的生存时间
     * @param key
     * @param value
     * @param time
     */
    public static void setSessionAttribute(String key, Object value, Integer time) {
        Map<String, Object> map = getSessionMapForRedis();

        map.put(key, value);

        RedisUtils.redisUtils.set(SessionCookie.getUserJSESSIONId(), JSONUtils.mapToJsonString(map), time);
    }

    /**
     * 删除Redis中的键
     * @param key
     */
    public static void removeSessionAttribute(String key) {
        RedisUtils.redisUtils.expire(key, 0);

    }

    /**
     * 批量删除Redis中的键
     * @param map
     */
    public static void removeSessionAttribute(Map<String,String> map) {
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry entry : set) {
            RedisUtils.redisUtils.expire((String)entry.getValue(), 0);
        }


    }

    /**
     * 删除Map对象中的数据
     *
     * @param key
     */
    public static void removeSessionForMap(String key) {
        String ticket=SessionCookie.getUserJSESSIONId();
        Map sessionmap = getSessionMapForRedis();
        sessionmap.remove(key);
        RedisUtils.redisUtils.set(ticket, JSONUtils.mapToJsonString(sessionmap), 1800);


    }

    /**
     * 对Redis中的键的生存时间进行刷新
     * @param key
     */
    public static void refreshSessionAttribute(String key) {

        RedisUtils.redisUtils.expire(key, 7200);


    }



    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @return
     */
    public static String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
