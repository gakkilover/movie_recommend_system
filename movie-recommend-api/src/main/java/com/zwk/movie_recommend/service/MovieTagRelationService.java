package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.MovieTagRelationEntity;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 16:05
 * @ Description：
 */
public interface MovieTagRelationService extends IService<MovieTagRelationEntity> {

    List<MovieTagRelationEntity> getByMovieId(Long movieId);

    List<MovieEntity> getMovieByTagId(Long tagId);

    List<MovieTagRelationEntity> getAll();
}
