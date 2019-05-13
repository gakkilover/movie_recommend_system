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
@TableName("movie_tag_relation")
public class MovieTagRelationEntity implements Serializable {
    private static final long serialVersionUID = -7461160988276055818L;
    @TableId
    private Long movieTagId;

    private Long movieId;

    private Long tagId;

    public Long getMovieTagId() {
        return movieTagId;
    }

    public void setMovieTagId(Long movieTagId) {
        this.movieTagId = movieTagId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}