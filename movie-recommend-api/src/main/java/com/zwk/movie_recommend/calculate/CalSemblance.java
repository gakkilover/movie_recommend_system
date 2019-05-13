package com.zwk.movie_recommend.calculate;

import com.zwk.common.constant.Final;
import com.zwk.movie_recommend.entity.*;
import com.zwk.movie_recommend.redis.RedisService;
import com.zwk.movie_recommend.service.MovieTagRelationService;
import com.zwk.movie_recommend.utils.SpringBeanUtils;
import com.zwk.movie_recommend.service.MovieService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-02 22:23
 * @description: 计算电影相似度的入口，相似度 = 加权值 + 增强因子 + 削弱因子
 **/
public class CalSemblance {


    public CalSemblance(){

    }

    private static class CalSemblanceContainer{
        private static CalSemblance instance = new CalSemblance();
    }

    public static CalSemblance getInstance(){
        return  CalSemblanceContainer.instance;
    }

//    @Autowired
    private static MovieService movieService = null;
    private static MovieTagRelationService movieTagRelationService = null;
    private static RedisService redisService = null;
    static {
        movieService = (MovieService) SpringBeanUtils.getBean("movieService");
        movieTagRelationService = (MovieTagRelationService) SpringBeanUtils.getBean("movieTagRelationService");
        redisService = (RedisService) SpringBeanUtils.getBean("redisService");
    }

//    private MovieService movieService = new MovieServicelmpl();

    /**
     *
     * @param commentRecordEntityList 用户最近的评分记录（20条）
     * @param similartabList 与刚评分电影p的最相似的电影集合（1000条）
     */
    public List calSemblance(List<CommentRecordEntity> commentRecordEntityList, List<MovieSimilarEntity> similartabList, Long movieId){

        //计算相似电影中每个电影的推荐优先级
        List<MovieEntity> recommendLevelList = new ArrayList<>();
        if(similartabList == null ||similartabList.size() == 0){
            List<MovieTagRelationEntity> movieTagList = movieTagRelationService.getByMovieId(movieId);
            for (MovieTagRelationEntity movieTag:movieTagList) {
                MovieEntity movie = new MovieEntity();
                movie = movieTagRelationService.getMovieByTagId(movieTag.getTagId()).get(0);
                recommendLevelList.add(movie);
            }
            return  recommendLevelList;
        }
        //遍历求出每个电影（最相似的电影合集）的推荐优先级
        for (MovieSimilarEntity movieSimilarEntity : similartabList) {
//            MovieEntity movieEntity = movieService.getMovieByMovieid(movieSimilarEntity.getMovieId2());
            MovieEntity movieEntity = (MovieEntity) redisService.get(Final.REDIS_MOVIE + movieSimilarEntity.getMovieId2());
            if(null != movieEntity) {
                double semblance = 0;    //相似度
                int amount = 0;          //当前电影与已评分电影中相似度大于0.6的个数
                int highAmount = 0;      //评分记录中与刚评分电影相似的且本身评分较高的电影个数（用于计算增强因子）
                int lowAmount = 0;       //评分记录中与刚评分电影相似的且本身评分较低的电影个数（用于计算削弱因子）
                double sigma = 0;        //电影与已评分电影的相似度求和
                //最小相似度大于0.6时有效，否则忽略，0.6为阈值
                for (CommentRecordEntity commentRecordEntity : commentRecordEntityList) {
                    boolean flag = true; //处理review与similartab的相似度是否大于0.6  todo
                    if (flag) {
                        amount++;
                        sigma += commentRecordEntity.getCommentStar() * movieSimilarEntity.getSimilarRate();
                        // 高/低评分电影个数，用于计算增强因子
                        if (commentRecordEntity.getCommentStar() <= Final.LOW_AVERATING) {
                            lowAmount++;
                        }
                        if (commentRecordEntity.getCommentStar() >= Final.HIGH_AVERATING) {
                            highAmount++;
                        }
                    }
                }
                if (amount != 0) {
                    semblance = sigma / amount + calcFactor(highAmount) - calcFactor(lowAmount);
                }
                movieEntity.setRecommendPriority(semblance);
                recommendLevelList.add(movieEntity);
            }
        }
        return recommendLevelList;
    }

    /**
     * @param amount 相似电影评分较高的个数（>=3）||相似电影评分较低的个数（<3）
     */
    //计算增强||削弱因子入口
    public  double calcFactor(int amount){
        //如果评分较高或者较低的电影个数都小于1，按1个计算，不然增强因子或者削弱因子计算的时候没有意义
        amount = Math.max(amount, 1);
        return Math.log10(amount);
    }

    /**
     * 合并最新一次的推荐结果和上一次推荐结果得到最终推荐电影列表 如果不够五条
     * @param updateList  最新一次的推荐结果
     * @param historyList 上一次历史推荐结果
     * @return
     */
    public  List mergeRecommendList(List<MovieEntity> updateList, List<MovieEntity> historyList){
        List<MovieEntity> reusltList = new ArrayList<>();
        for (int i = 0; i < historyList.size(); i++) {
            historyList.get(i).setRecommendPriority(0);
        }
        reusltList.addAll(updateList);
        reusltList.addAll(historyList);
//        for (MovieEntity update: updateList) {
//            for (MovieEntity history: historyList) {
//                if(update == history){
//                    reusltList.add(update);
//                }
//            }
//        }
        //todo 合并逻辑有待完善  关于是否足够5个电影需要拼装   如何办？
        Collections.sort(reusltList);
        if(reusltList.size()>10){
            reusltList = reusltList.subList(0, 10);
        }
        return reusltList;
    }

    public String getMovieIds(List<MovieEntity> resultList){
        StringBuffer sb = new StringBuffer();
        for (int i=1; i<resultList.size()&&i<4; i++){
            sb.append(resultList.get(i).getMovieId()+",");
        }
        if(resultList.size()>=5){
            sb.append(resultList.get(4).getMovieId()+",");
        }
        return sb.toString();
    }

}
