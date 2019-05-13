package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.MovieEntity;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 21:13
 * @description:
 **/
public interface MovieService extends IService<MovieEntity> {

    //选择默认的电影5部(暂时用于推荐表中用户推荐电影不足五部的时候增加)
    ResultView selectTopDefaultMovie(int limit);

    //电影排序分类选择20部一次
    ResultView getBycategory(Long categoerId, int limit, String sort);

    //搜索电影by id
    ResultView getByMovieid(Long movieId);

    //相似电影10部
    ResultView select5SimilarMoviesById(Long movieId);

    //判断用户对电影的喜爱
    int judgeIsLike(Long userid,String movieid);


    //好像可以合并？和上面一个方法同样的功能
    MovieEntity getMovieByMovieid(Long movieId);

    //电影名称搜索电影排序时用
    List<MovieEntity> selectMoviesByName(String moviename);

    //用户评价将相似电影写入recab
    String select5SimilarMovies(Long movieId);

    Double getAverageStar(Long movieId);

    //通过用户标签查各热度电影
    List<MovieEntity> getMovieByTagUser(Long userId);

    //通过标签查各热度电影
    List<MovieEntity> getMovieByHeat();

    List<MovieEntity> getAllMovie();

    void updateMovieRate(Long movieId,double star);
}
