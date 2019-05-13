package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.dao.SearchRecordDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.SearchRecordEntity;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 16:07
 * @ Description：
 */
public interface SearchRecordService extends IService<SearchRecordEntity>{

    void insertSearchRecord(Long movieId, Long userId, String searchInfo);

    List<MovieEntity> getSearchRecord(Long userId);
}

