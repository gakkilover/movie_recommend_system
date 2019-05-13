package com.zwk.movie_recommend.entity.redisData;

import com.zwk.common.utils.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-14 15:17
 * @description: 电影之间相似度缓存数据
 **/
public class MovieSimilarCache extends BaseCache{

    private Logger logger = LoggerFactory.getLogger(MovieSimilarCache.class);

    private Long movieId1;

    private Long movieId2;

    private Double similarRate;

    public MovieSimilarCache(){
        setCacheName(TABLE.MOVIE_SIMILAR.name());
    }

    private final String sql = " SELECT MOVIE_ID1, MOVIE_ID2, SIMILAR_ARTE FROM MOVIE_SIMILAR WHERE 1=1 ";

    @Override
    public void initCache() {
        logger.info("开始同步电影相似度movie_similar数据到缓存");
        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);
        Map<String,Object> map = new HashMap<>();
        List<MovieSimilarCache> movieSimilarCacheList = new ArrayList<>();
        for (Map<String, Object> cacheMap : list) {

        }
        logger.info("结束同步电影相似度movie_similar数据到缓存");
    }

    public Long getMovieId1() {
        return movieId1;
    }

    public void setMovieId1(Long movieId1) {
        this.movieId1 = movieId1;
    }

    public Long getMovieId2() {
        return movieId2;
    }

    public void setMovieId2(Long movieId2) {
        this.movieId2 = movieId2;
    }

    public Double getSimilarRate() {
        return similarRate;
    }

    public void setSimilarRate(Double similarRate) {
        this.similarRate = similarRate;
    }


}
