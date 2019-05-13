package com.zwk.movie_recommend.config;

import com.zwk.movie_recommend.redis.CacheData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-14 15:35
 * @description: 缓存数据更新定时器
 **/
@Component
public class CacheDataTask {
    private Logger logger = LoggerFactory.getLogger(CacheDataTask.class);

    /**
     * 缓存数据定时更新（每10分钟更新一次）
     */
//    @Scheduled(cron = "0 0/10 * * * ? ")
    @Scheduled(cron = "0 0/10 * * * ? ")
    public void refreshCache(){
        logger.info("缓存定时任务执行开始");
        CacheData.cacheData.refreshCacheAll(); //todo
//        CalMovieHeat calMovieHeat = CalMovieHeat.getInstance();
//        calMovieHeat.calHeat();
        logger.info("缓存定时任务执行结束");
    }
}
