package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2019-01-25 17:01
 * @description：收藏详情实体类（包含观看状态）
 */
@TableName("collect_detail")
public class CollectDetailEntity implements Serializable {

    private static final long serialVersionUID = 820910193171226537L;
    @TableId
    private Long collectDetailId;
    private Long userId;
    private Long movieId;
    private Date collectDate;
    private Integer watchStatus;
    private Date updateTime;

    public Long getCollectDetailId() {
        return collectDetailId;
    }

    public void setCollectDetailId(Long collectDetailId) {
        this.collectDetailId = collectDetailId;
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

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public Integer getWatchStatus() {
        return watchStatus;
    }

    public void setWatchStatus(Integer watchStatus) {
        this.watchStatus = watchStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
