package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.MovieTagRelationDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.MovieTagRelationEntity;
import com.zwk.movie_recommend.service.MovieTagRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 16:05
 * @ Description：
 */
@Service("movieTagRelationService")
public class MovieTagRelationServiceImpl extends ServiceImpl<MovieTagRelationDao, MovieTagRelationEntity> implements MovieTagRelationService {

    @Autowired
    private MovieTagRelationDao movieTagRelationDao;

    @Override
    public List<MovieTagRelationEntity> getByMovieId(Long movieId) {

        return movieTagRelationDao.getByMovieId(movieId);
    }

    /**
     * 根据标签主键查询此标签中评分最高的电影
     * @param tagId
     * @return
     */
    @Override
    public List<MovieEntity> getMovieByTagId(Long tagId) {

        return movieTagRelationDao.getMovieByTagId(tagId);
    }

    @Override
    public List<MovieTagRelationEntity> getAll() {
        return movieTagRelationDao.getList();
    }
}
