package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-25 17:01
 * @ Description：
 */
@TableName("recommend_record")
public class RecommendRecordEntity implements Serializable {
    private static final long serialVersionUID = -8695672705101082883L;
    @TableId
    private Long recommendRecordId;

    private Long userId;

    private Date recommendDate;

    private String movieIds;

    public Long getRecommendRecordId() {
        return recommendRecordId;
    }

    public void setRecommendRecordId(Long recommendRecordId) {
        this.recommendRecordId = recommendRecordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(Date recommendDate) {
        this.recommendDate = recommendDate;
    }

    public String getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(String movieIds) {
        this.movieIds = movieIds;
    }
}