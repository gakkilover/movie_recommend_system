package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableField;
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
@TableName("movie")
public class MovieEntity implements Comparable<MovieEntity>, Serializable {
    private static final long serialVersionUID = -8200247262150717983L;
    @TableId
    private Long movieId;

    private String movieName;

    private Date movieShowyear;

    private String nation;

    private String movieDirector;

    private String movieLeadactors;

    private String screen;

    private String moviePic;

    private Double movieAverating;

    private Long movieRateNum;

    private String movieDescription;

    private String movieTags;

    private String backpost;

    @TableField(exist = false)
    private double recommendPriority;

    private double sumRate;

    @TableField(exist = false)
    private Long tagId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getMovieShowyear() {
        return movieShowyear;
    }

    public void setMovieShowyear(Date movieShowyear) {
        this.movieShowyear = movieShowyear;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String getMovieLeadactors() {
        return movieLeadactors;
    }

    public void setMovieLeadactors(String movieLeadactors) {
        this.movieLeadactors = movieLeadactors;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getMoviePic() {
        return moviePic;
    }

    public void setMoviePic(String moviePic) {
        this.moviePic = moviePic;
    }

    public Double getMovieAverating() {
        return movieAverating;
    }

    public void setMovieAverating(Double movieAverating) {
        this.movieAverating = movieAverating;
    }

    public Long getMovieRateNum() {
        return movieRateNum;
    }

    public void setMovieRateNum(Long movieRateNum) {
        this.movieRateNum = movieRateNum;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieTags() {
        return movieTags;
    }

    public void setMovieTags(String movieTags) {
        this.movieTags = movieTags;
    }

    public String getBackpost() {
        return backpost;
    }

    public void setBackpost(String backpost) {
        this.backpost = backpost;
    }

    public double getRecommendPriority() {
        return recommendPriority;
    }

    public void setRecommendPriority(double recommendPriority) {
        this.recommendPriority = recommendPriority;
    }

    @Override
    public int compareTo(MovieEntity o) {
        Double prioritySelf = this.getRecommendPriority();
        Double priorityOther = o.getRecommendPriority();
//        if( prioritySelf ==null && priorityOther != null){
//            return 0;
//        } else
        if( prioritySelf !=null && priorityOther == null){
            return 1;
        }else if(prioritySelf !=null && priorityOther != null){
            Double i = prioritySelf - priorityOther;//按照推荐优先级排序
            if(i > 0){
                return 1;
            }
        }
        return 0;
    }

    public double getSumRate() {
        return sumRate;
    }

    public void setSumRate(double sumRate) {
        this.sumRate = sumRate;
    }
}