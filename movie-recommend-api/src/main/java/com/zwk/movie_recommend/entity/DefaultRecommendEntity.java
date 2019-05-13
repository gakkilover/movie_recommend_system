//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
@TableName("default_recommend")
public class DefaultRecommendEntity implements Serializable {
    private static final long serialVersionUID = -5322408341172129138L;
    @TableId
    private Long defaultRecommendId;

    private Long movieId;

    public Long getDefaultRecommendId() {
        return defaultRecommendId;
    }

    public void setDefaultRecommendId(Long defaultRecommendId) {
        this.defaultRecommendId = defaultRecommendId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
