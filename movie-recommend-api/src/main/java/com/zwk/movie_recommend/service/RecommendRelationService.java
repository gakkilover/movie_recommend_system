package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.RecommendRelationEntity;
import com.zwk.movie_recommend.entity.MovieEntity;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 11:09
 * @ Description：
 */
public interface RecommendRelationService extends IService<RecommendRelationEntity> {
    List<MovieEntity> selectAlsMoviesByUserId(Long userid);

    void insertRelation(Long userId, String movieds);

    void updateRelation(Long userId, String movieds);
}
