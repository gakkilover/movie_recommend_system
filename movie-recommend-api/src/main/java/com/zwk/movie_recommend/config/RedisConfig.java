package com.zwk.movie_recommend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-15 15:12
 * @ Description：
 */
@Configuration
@EnableCaching
public class RedisConfig  {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    //    @Value("${spring.redis.timeout}")
    private int timeout=Protocol.DEFAULT_TIMEOUT;

    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait}")
    private long maxWaitMillis;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.block-when-exhausted}")
    private boolean  blockWhenExhausted;
    private Logger logger = LoggerFactory.getLogger(RedisConfig.class);


    @Bean(name = "jedisPool")
    public JedisPool redisPoolFactory()  throws Exception{
        logger.info("===================JedisPool注入成功===================");
        logger.info("============redis地址：" + host + ":" + port+"===========");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        // 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(blockWhenExhausted);
        // 是否启用pool的jmx管理功能, 默认true
        jedisPoolConfig.setJmxEnabled(true);
        JedisPool jedisPool;
        if(password == null || password.equals("")){
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
        }else{
            jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout,password);
        }
        logger.info("===================redis初始化!!!===================");
        return jedisPool;
    }
}
