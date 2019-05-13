package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.MovieSimilarEntity;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 17:49
 * @ Description：
 */
public interface MovieSimilarService extends IService<MovieSimilarEntity> {

    /**
     * 由电影id获取相似的电影
     * @param movieId
     * @return
     */
    List<MovieSimilarEntity> getSimilarTabByMovieId(Long movieId);


    List<MovieSimilarEntity> getAll();
}
