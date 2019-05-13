package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-23 20:50
 * @description:
 **/
@TableName("heat_movie")
public class HeatMovieEntity implements Serializable {

    private static final long serialVersionUID = 7738482529434757802L;

    @TableId
    private Long heatId;

    private Long movieId;

    private Double heatNumber;

    private Date updateDate;

    public Long getHeatId() {
        return heatId;
    }

    public void setHeatId(Long heatId) {
        this.heatId = heatId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Double getHeatNumber() {
        return heatNumber;
    }

    public void setHeatNumber(Double heatNumber) {
        this.heatNumber = heatNumber;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

//    @Override
//    public int compareTo(HeatMovieEntity o) {
//        Double prioritySelf = this.getHeatNumber();
//        Double priorityOther = o.getHeatNumber();
//        if( prioritySelf !=null && priorityOther == null){
//            return 1;
//        }else if(prioritySelf !=null && priorityOther != null){
//            Double i = prioritySelf - priorityOther;//按照热度高低排序
//            if(i > 0){
//                return 1;
//            }
//        }
//        return 0;
//    }
}
