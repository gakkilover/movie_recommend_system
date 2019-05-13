package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.common.constant.Final;
import com.zwk.movie_recommend.dao.MovieSimilarDao;
import com.zwk.movie_recommend.entity.MovieSimilarEntity;
import com.zwk.movie_recommend.redis.RedisService;
import com.zwk.movie_recommend.service.MovieSimilarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 17:50
 * @ Description：
 */
@Service("similarTabService")
public class MovieSimilarServiceImpl extends ServiceImpl<MovieSimilarDao, MovieSimilarEntity> implements MovieSimilarService {

    @Autowired
    private MovieSimilarDao movieSimilarDao;


    @Override
    public List<MovieSimilarEntity> getSimilarTabByMovieId(Long movieId) {
        StringBuffer sql =new StringBuffer();
        sql.append(" and movie_id1=" + movieId);
        return movieSimilarDao.getList(sql.toString());
    }

    @Override
    public List<MovieSimilarEntity> getAll() {
        StringBuffer sql =new StringBuffer();
        List<MovieSimilarEntity> movieSimilarList = movieSimilarDao.getList(sql.toString());
        return movieSimilarList;
    }
}
