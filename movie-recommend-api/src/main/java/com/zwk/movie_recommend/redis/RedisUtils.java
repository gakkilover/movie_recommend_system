package com.zwk.movie_recommend.redis;

import com.zwk.common.utils.SpringContextUtils;
import com.zwk.movie_recommend.utils.SpringBeanUtils;
import com.zwk.movie_recommend.utils.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-13 10:13
 * @ Description：
 */
@Component
public class RedisUtils {
    private Logger log = LoggerFactory.getLogger(RedisUtils.class);


    @Autowired
    public static JedisPool JedisPool;
    public static RedisUtils redisUtils;

    static {
        //JedisPool = SpringContextHolder.getBean("jedisPool");//todo
        redisUtils = new RedisUtils();
    }

    public Jedis Jedis() {
        return JedisPool.getResource();
    }

    /**
     * 给数据库中名称为key的string赋予值value
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        String result;
        try {
            jedis = Jedis();
            result = jedis.set(key, value);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    public String set(byte[] key, byte[] value, Integer seconds) {
        Jedis jedis = null;
        String result;
        try {
            jedis = Jedis();
            result = jedis.set(key, value);
            jedis.expire(key, seconds);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    /**
     * 给数据库中名称为key的string赋予值value,同时设置key过期时限
     *
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    public String set(String key, String value, Integer seconds) {
        Jedis jedis = null;
        String result;
        try {
            jedis = Jedis();
            result = jedis.set(key, value);
            jedis.expire(key, seconds);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    /**
     * 返回数据库中名称为key的string的value
     *
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = null;
        String result;
        try {
            jedis = Jedis();
            result = jedis.get(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    public byte[] get(byte[] key) {
        Jedis jedis = null;
        byte[] result;
        try {
            jedis = Jedis();
            result = jedis.get(key);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    /**
     * 向名称为key的hash中添加元素field<—>value
     *
     * @param key
     * @param field
     * @param value
     * @return
     */
    public long hset(String key, String field, String value) {
        Jedis jedis = null;
        long result;
        try {
            jedis = Jedis();
            result = jedis.hset(key, field, value);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    /**
     * 返回名称为key的hash中field对应的value
     *
     * @param key
     * @param field
     * @return
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        String result;
        try {
            jedis = Jedis();
            result = jedis.hget(key, field);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    /**
     * 存储Map数据类型
     *
     * @param key
     * @param hash
     * @return
     */
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        String result;
        try {
            jedis = Jedis();
            result = jedis.hmset(key, hash);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    /**
     * 返回List列表数据
     *
     * @param key
     * @param fields
     * @return
     */
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List<String> result;
        try {
            jedis = Jedis();
            result = jedis.hmget(key, fields);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }

    /*
     * 清空所有缓存数据
     */
    public String flushdb() {
        Jedis jedis;
        String result;
        try {
            jedis = JedisPool.getResource();
            result = jedis.flushDB();
        } finally {

        }
        return result;
    }

    /**
     * key续期
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {

        Jedis jedis = null;
        Long result;
        try {
            jedis = Jedis();
            result = jedis.expire(key, seconds);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return result;
    }
}
