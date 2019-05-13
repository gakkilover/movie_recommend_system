package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.HeatMovieEntity;
import com.zwk.movie_recommend.entity.MovieEntity;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-23 21:56
 * @description:
 **/
public interface HeatMovieService extends IService<HeatMovieEntity> {

    void insertOrUpdateHeat(HeatMovieEntity heatMovieEntity);

    //获取前10个热度电影
    List<MovieEntity> getTop10List();
}
