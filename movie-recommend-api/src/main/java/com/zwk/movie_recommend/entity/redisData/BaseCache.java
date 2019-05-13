package com.zwk.movie_recommend.entity.redisData;

import com.zwk.common.utils.JSONUtils;
import com.zwk.movie_recommend.redis.RedisUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-13 16:04
 * @ Description：缓存数据抽象类
 */
public abstract class BaseCache {
    /**
     * 表枚举
     */
    public enum TABLE {

        //之所以把这两张表的数据缓存起来是因为数据量大，查询的次数也稍多，使用缓存查询效率大大提升
        MOVIE("电影"),  ////电影缓存表
        MOVIE_SIMILAR("电影相似度表");  //电电影相似度缓存表

        private String tname;

        private TABLE(String name) {
            this.tname = name;
        }

        public String getTname() {
            return tname;
        }

        public void setTname(String tname) {
            this.tname = tname;
        }
    };

    protected final String PRE_ID = "ID_";
    protected final String PRE_NBR = "NBR_";
    private JdbcTemplate jdbcTemplate;

    private String cacheName = "";

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public abstract void initCache();

    public Map<String, Object> getCache() {
        Map<String, Object> cache = (Map<String, Object>) JSONUtils
                .jsonToMap(RedisUtils.redisUtils.get(getCacheName()));
        if (cache == null) {
            cache = new HashMap<>();
            setCache(cache);
        }
        return cache;
    }

    /**
     * class[0] 实体类对象类型
     *
     * class[1] 返回数据类型
     *
     * @param key
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object getBaseCacheFroRedis(String key, Class... clazz) {
        if (clazz.length > 2)
            throw new ArrayIndexOutOfBoundsException();
        Object o = null;
        Map<String, Object> map = getCache();
        if (map == null) {
            return null;
        } else {
            Object obj = map.get(key);
            if (obj == null) {
                return null;
            }
            String t = obj.toString();
            try {
                if (clazz.length ==2 && clazz[1] != null
                        && clazz[1].newInstance() instanceof ArrayList) {
                    o = JSONUtils.jsonToObjectList(t, clazz[0]);
                } else {
                    o = JSONUtils.jsonToObject(t, clazz[0]);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return o;
        }
    }

    public void setCache(Map<String, Object> valMap) {
        RedisUtils.redisUtils.set(getCacheName(),
                JSONUtils.toJsonString(valMap));
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
