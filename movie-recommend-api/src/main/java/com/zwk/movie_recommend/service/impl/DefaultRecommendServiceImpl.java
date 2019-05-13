package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.MovieDao;
import com.zwk.movie_recommend.dao.DefaultRecommendDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.DefaultRecommendEntity;
import com.zwk.movie_recommend.service.DefaultRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 21:03
 * @description:
 **/
@Service("topDefaultMoviesService")
public class DefaultRecommendServiceImpl extends ServiceImpl<DefaultRecommendDao, DefaultRecommendEntity> implements DefaultRecommendService {

    @Autowired
    private DefaultRecommendDao defaultRecommendDao;

    @Autowired
    private MovieDao movieDao;

    @Override
    public List<MovieEntity> SelectRegDefaultMovie() {
        List<DefaultRecommendEntity> topList = defaultRecommendDao.selectRegDefaultMovie();
        List<MovieEntity> movieList =new ArrayList<>();
        for (DefaultRecommendEntity topMovie: topList) {
            MovieEntity movieEntity = movieDao.getByMovieId(topMovie.getMovieId());
            movieList.add(movieEntity);
        }
        return movieList;
    }
}
