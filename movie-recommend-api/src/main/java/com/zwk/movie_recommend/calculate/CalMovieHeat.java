package com.zwk.movie_recommend.calculate;

import com.zwk.movie_recommend.entity.CollectDetailEntity;
import com.zwk.movie_recommend.entity.CommentRecordEntity;
import com.zwk.movie_recommend.entity.HeatMovieEntity;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.service.CollectDetailService;
import com.zwk.movie_recommend.service.CommentRecordService;
import com.zwk.movie_recommend.service.HeatMovieService;
import com.zwk.movie_recommend.service.MovieService;
import com.zwk.movie_recommend.utils.SpringBeanUtils;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import java.util.*;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-23 22:02
 * @description: 计算电影热度  热度 = 电影评分平均值 + 电影加权值（收藏加0.1分）
 **/
public class CalMovieHeat {

    public CalMovieHeat(){

    }

    private static class CalMovieHeatContainer{
        private static CalMovieHeat instance = new CalMovieHeat();
    }

    public static CalMovieHeat getInstance(){
        return CalMovieHeatContainer.instance;
    }

    private static MovieService movieService = null;
    private static HeatMovieService heatMovieService =null;
    private static CollectDetailService collectDetailService = null;
    private static CommentRecordService commentRecordService = null;
    static {
        movieService = (MovieService) SpringBeanUtils.getBean("movieService");
        heatMovieService = (HeatMovieService) SpringBeanUtils.getBean("heatMovieService");
        collectDetailService = (CollectDetailService) SpringBeanUtils.getBean("collectDetailService");
        commentRecordService = (CommentRecordService) SpringBeanUtils.getBean("commentRecordService");
    }

    public void calHeat(){
        //获取最新的10条收藏记录
        List<CollectDetailEntity> collectList = collectDetailService.getTop10Collect();

        //获取最新的10条评分记录
        List<CommentRecordEntity> commentList = commentRecordService.getTop10Comment();
        //获取最新的10条热度电影
        List<MovieEntity> heatList = heatMovieService.getTop10List();

        //创建一个新的列表用于存储最新的推荐结果
        List<HeatMovieEntity> resultList = new ArrayList<>();

        for (CollectDetailEntity collect :collectList) {
            HeatMovieEntity heat =new HeatMovieEntity();
            heat.setHeatNumber(0d);
            heat.setMovieId(collect.getMovieId());
            resultList.add(heat);
        }
        for (CommentRecordEntity comment :commentList) {
            HeatMovieEntity heat = new HeatMovieEntity();
            heat.setHeatNumber(0d);
            heat.setMovieId(comment.getMovieId());
            resultList.add(heat);
        }
        for (MovieEntity heatMovie :heatList) {
            HeatMovieEntity heat = new HeatMovieEntity();
            heat.setHeatNumber(0d);
            heat.setMovieId(heatMovie.getMovieId());
            resultList.add(heat);
        }
        //先将之前的热度电影放到结果列表里
        //resultList.addAll(heatList);
        //接下来计算电影热度,先计算收藏的加权值
        for (CollectDetailEntity collect :collectList) {
            for (HeatMovieEntity heat :resultList) {
                //如果电影id相同，说明此热度电影被收藏，热度变化
                if(heat.getMovieId().equals(collect.getMovieId())){
                    heat.setHeatNumber(heat.getHeatNumber()+0.1);
                }
            }
        }
        //再计算平均评分的基值
        for (HeatMovieEntity heat :resultList) {
            Double avgStar = movieService.getAverageStar(heat.getMovieId());
            if(avgStar != null) {
                heat.setHeatNumber(heat.getHeatNumber() + avgStar);
            }
        }
        //去重（存在重复的热度电影）
        List<HeatMovieEntity> updateList = resultList.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparingLong(HeatMovieEntity::getMovieId))), ArrayList::new)
        );
        //按照电影热度排序（升序）
        updateList.sort(Comparator.comparing(it-> it.getHeatNumber() ));
        //由升序改为降序
        Collections.reverse(updateList);
        //获取前十条热度数据
        if(updateList.size()>10){
            updateList = updateList.subList(0,10);
        }
        //更新到数据库
        for (HeatMovieEntity heat :updateList) {
            heatMovieService.insertOrUpdateHeat(heat);
        }
    }
}
