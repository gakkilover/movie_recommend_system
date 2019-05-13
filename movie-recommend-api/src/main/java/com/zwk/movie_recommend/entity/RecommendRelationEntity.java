package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;


/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-25 17:01
 * @ Description：
 */
@TableName("recommend_relation")
public class RecommendRelationEntity implements Serializable {
    private static final long serialVersionUID = 2586701424409494867L;
    @TableId
    private Long recommendRelationId;

    private Long userId;

    private Long movieId;

    private Double recommendScore;

    public Long getRecommendRelationId() {
        return recommendRelationId;
    }

    public void setRecommendRelationId(Long recommendRelationId) {
        this.recommendRelationId = recommendRelationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Double getRecommendScore() {
        return recommendScore;
    }

    public void setRecommendScore(Double recommendScore) {
        this.recommendScore = recommendScore;
    }
}