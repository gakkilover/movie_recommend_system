package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.CollectRecordDao;
import com.zwk.movie_recommend.dao.MovieDao;
import com.zwk.movie_recommend.dao.MovieSimilarDao;
import com.zwk.movie_recommend.dao.DefaultRecommendDao;
import com.zwk.movie_recommend.entity.*;
import com.zwk.movie_recommend.service.MovieService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 21:14
 * @description:
 **/
@Service("movieService")
public class MovieServiceImpl extends ServiceImpl<MovieDao, MovieEntity> implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private MovieSimilarDao movieSimilarDao;

    @Autowired
    private CollectRecordDao collectRecordDao;

    @Autowired
    private DefaultRecommendDao defaultRecommendDao;

    @Override
    public ResultView selectTopDefaultMovie(int limit) {
        List<DefaultRecommendEntity> list = defaultRecommendDao.selectTopDefaultMovie(limit);
        List<MovieEntity> movieList = new ArrayList<>();
        if (list == null || list.size() == 0) {
            return ResultView.build(400, "获取电影信息错误");
        } else{
            for (DefaultRecommendEntity defaultRecommendEntity : list) {
                MovieEntity movieEntity = movieDao.getByMovieId(defaultRecommendEntity.getMovieId());
                movieList.add(movieEntity);
            }
        }
        return ResultView.ok(movieList);
    }

    @Override
    public ResultView getBycategory(Long categoerId, int limit, String sort) {
        StringBuffer sql = new StringBuffer();
        if(categoerId == 0){
            sql.append(" select * from movie order by "+ sort +" desc  LIMIT " + limit +",20");
        }else {
            sql.append(" select * from movie m, movie_tag_relation mc where m.movie_id=mc.movie_id and mc.tag_id =" + categoerId);
            sql.append(" order by "+ sort +" desc  LIMIT " + limit +",20");
        }
        // 执行查询
        List<MovieEntity> list = movieDao.selectBycategory(sql.toString());
        return ResultView.ok(list);
    }

    @Override
    public ResultView getByMovieid(Long movieId) {
        // 执行查询
        MovieEntity movieEntity = movieDao.getByMovieId(movieId);
        return ResultView.ok(movieEntity);
    }

    @Override
    public ResultView select5SimilarMoviesById(Long movieId) {

        StringBuffer sql = new StringBuffer();
        sql.append(" and movie_id1=" + movieId);
        sql.append(" order by similar_rate ");
        List<MovieSimilarEntity> list = movieSimilarDao.getList(sql.toString());
        List<MovieEntity> movieList = new ArrayList<MovieEntity>();
        MovieEntity movieEntity = null;
        for (MovieSimilarEntity movieSimilarEntity : list) {
            movieEntity = movieDao.getByMovieId(movieSimilarEntity.getMovieId2());
            if (movieEntity != null) {
                movieList.add(movieEntity);
            }
        }
        return ResultView.ok(movieList);
    }

    @Override
    public int judgeIsLike(Long userid, String movieid) {
        return  collectRecordDao.judgeIsLike(userid, movieid);
    }

    @Override
    public MovieEntity getMovieByMovieid(Long movieId) {
        return movieDao.getByMovieId(movieId);
    }

    @Override
    public List<MovieEntity> selectMoviesByName(String moviename) {
        List<MovieEntity> list = movieDao.selectByName(moviename);
        return list;
    }

    @Override
    public String select5SimilarMovies(Long movieId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" and movie_id1=" + movieId);
        sql.append(" order by similar_rate ");
        List<String> movieStr = movieSimilarDao.selectSimilarMovies(sql.toString());
        String movieRet = StringUtils.join(movieStr, ",");
        return movieRet;
    }

    @Override
    public Double getAverageStar(Long movieId) {
        return movieDao.getAverageStar(movieId);
    }

    @Override
    public List<MovieEntity> getMovieByTagUser(Long userId) {
        return movieDao.getMovieByTagUser(userId);
    }

    @Override
    public List<MovieEntity> getMovieByHeat() {
        return movieDao.getMovieByTag();
    }

    @Override
    public List<MovieEntity> getAllMovie() {
        List<MovieEntity> movieList = movieDao.getAllMovie();
//        for (MovieEntity movie : movieList) {
//            double sumRate = movie.getMovieAverating()*movie.getMovieRateNum();
//            movie.setSumRate(sumRate);
//            movieDao.updateById(movie);
//        }
        return  movieList;
    }

    @Override
    public void updateMovieRate(Long movieId, double star) {
        MovieEntity movie = movieDao.getByMovieId(movieId);
        if(movie != null){
            double sumRate = movie.getSumRate()+star;
            Long movieRateNum = movie.getMovieRateNum()+1L;
            double averating = sumRate/(double)(movieRateNum);
            averating = (double) Math.round(averating * 100) / 100;
            movie.setSumRate(sumRate);
            movie.setMovieAverating(averating);
            movie.setMovieRateNum(movie.getMovieRateNum()+1);
            movieDao.updateById(movie);
        }
    }
}
