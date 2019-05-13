package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.DefaultRecommendEntity;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 21:00
 * @description:
 **/
public interface DefaultRecommendService extends IService<DefaultRecommendEntity> {
    //选择默认的电影10个在用户注册时用
    public List<MovieEntity> SelectRegDefaultMovie();
}
