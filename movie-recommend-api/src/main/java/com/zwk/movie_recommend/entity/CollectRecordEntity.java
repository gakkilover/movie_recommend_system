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
@TableName("collect_record")
public class CollectRecordEntity implements Serializable {

    private static final long serialVersionUID = 7738482529434757809L;

    @TableId
    private Long collectRecordId;
    private Long userId;
    private String movieIds;
    private Date collectDate;

    public Long getCollectRecordId() {
        return collectRecordId;
    }

    public void setCollectRecordId(Long collectRecordId) {
        this.collectRecordId = collectRecordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(String movieIds) {
        this.movieIds = movieIds;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }
}
