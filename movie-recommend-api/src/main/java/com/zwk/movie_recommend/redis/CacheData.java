package com.zwk.movie_recommend.redis;

import com.zwk.common.constant.Final;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.MovieSimilarEntity;
import com.zwk.movie_recommend.entity.MovieTagRelationEntity;
import com.zwk.movie_recommend.entity.redisData.BaseCache;
import com.zwk.movie_recommend.entity.redisData.MovieCache;
import com.zwk.movie_recommend.entity.redisData.MovieSimilarCache;
import com.zwk.movie_recommend.service.MovieService;
import com.zwk.movie_recommend.service.MovieSimilarService;
import com.zwk.movie_recommend.service.MovieTagRelationService;
import com.zwk.movie_recommend.utils.SpringBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-14 15:40
 * @description: 系统缓存
 **/
@Component("CacheData")
@Order(1) //启动类的执行优先级（顺序）
public final class CacheData implements ApplicationRunner{
    private Logger logger = LoggerFactory.getLogger(CacheData.class);

    private static JdbcTemplate jdbcTemplate;
    @Resource(type = JdbcTemplate.class)
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        CacheData.jdbcTemplate = jdbcTemplate;
    }

    @Resource
    private RedisService redisService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieTagRelationService movieTagRelationService;

    @Autowired
    private MovieSimilarService movieSimilarService;

    public static CacheData cacheData = new CacheData();


    private void init() {
        //将数据存入redis
        logger.info("开始初始化缓存数据");
        logger.info("-----------------");
        List<MovieEntity> movieList = movieService.getAllMovie();
        List<MovieSimilarEntity> movieSimilarList = movieSimilarService.getAll();
        List<MovieTagRelationEntity> movieTagRelationList = movieTagRelationService.getAll();
        logger.info("开始初始化电影数据···");
        redisService.set(Final.REDIS_MOVIE, movieList);
        movieList.forEach((movie)->{
            redisService.set(Final.REDIS_MOVIE + movie.getMovieId(), movie);
        });
        logger.info("结束初始化电影数据");
        logger.info("·········");
        logger.info("开始初始化电影相似度数据···");
        redisService.set(Final.REDIS_MOVIE_SIMILAR, movieSimilarList);
        movieSimilarList.forEach((movieSimilar)->{
            redisService.set(Final.REDIS_MOVIE_SIMILAR + movieSimilar.getMovieId1(), movieSimilar);
        });
        logger.info("结束初始化电影相似度数据···");
        logger.info("·········");
        logger.info("开始初始化电影标签数据···");
        redisService.set(Final.REDIS_MOVIE_TAG_RELATION, movieTagRelationList);
        movieTagRelationList.forEach((movieTag)->{
            redisService.set(Final.REDIS_MOVIE_TAG_RELATION + movieTag.getMovieId(), movieTag);
        });
        logger.info("结束初始化电影标签数据···");
    }

    public synchronized boolean refreshCacheAll() {
        init();
        return true;
    }



    @Override
    public void run(ApplicationArguments args) throws Exception {
        init();
    }
}
