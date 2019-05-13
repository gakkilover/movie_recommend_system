package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.SearchRecordDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.SearchRecordEntity;
import com.zwk.movie_recommend.service.SearchRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 16:09
 * @ Description：
 */
@Service("searchRecordService")
public class SearchRecordServiceImpl extends ServiceImpl<SearchRecordDao, SearchRecordEntity> implements SearchRecordService {

    @Autowired
    private SearchRecordDao searchRecordDao;

    @Override
    public void insertSearchRecord(Long movieId, Long userId, String searchInfo) {
        SearchRecordEntity searchRecord = new SearchRecordEntity();
        searchRecord.setSearchRecordId(searchRecordDao.getMaxId());
        searchRecord.setUserId(userId);
        searchRecord.setMovieId(movieId);
        searchRecord.setSearchInfo(searchInfo);
        searchRecord.setSearchDate(new Date());
        searchRecordDao.insert(searchRecord);
    }

    @Override
    public List<MovieEntity> getSearchRecord(Long userId) {

        return searchRecordDao.getSearchRecord(userId);
    }
}
