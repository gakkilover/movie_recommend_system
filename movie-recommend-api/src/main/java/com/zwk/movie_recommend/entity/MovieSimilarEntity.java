package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;


/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-25 17:01
 * @ Description：
 */
@TableName("movie_similar")
public class MovieSimilarEntity implements Serializable {
    private static final long serialVersionUID = -7929366096006471265L;
    private Long movieId1;

    private Long movieId2;

    private Double similarRate;

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