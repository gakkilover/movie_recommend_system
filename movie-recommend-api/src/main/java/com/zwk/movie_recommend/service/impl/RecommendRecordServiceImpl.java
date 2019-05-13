package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.RecommendRecordDao;
import com.zwk.movie_recommend.entity.RecommendRecordEntity;
import com.zwk.movie_recommend.service.RecommendRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 17:09
 * @ Description：
 */
@Service("rectabService")
public class RecommendRecordServiceImpl extends ServiceImpl<RecommendRecordDao, RecommendRecordEntity> implements RecommendRecordService {

    @Autowired
    private RecommendRecordDao recommendRecordDao;

    @Override
    public RecommendRecordEntity getRectabByUserId(Long userid) {

        List<RecommendRecordEntity> rectabList=null;
        rectabList = recommendRecordDao.getByUserId(userid);
        if(rectabList != null && rectabList.size()>0)
            return rectabList.get(0);
        else
            return null;
    }

    @Override
    public int insertRectab(RecommendRecordEntity recommendRecordEntity) {
        recommendRecordEntity.setRecommendRecordId(recommendRecordDao.getMaxId());
        recommendRecordEntity.setRecommendDate(new Date());
        return recommendRecordDao.insert(recommendRecordEntity);
    }

    @Override
    public void updateRectab(RecommendRecordEntity recommendRecordEntity) {
        recommendRecordDao.updateByUserId(recommendRecordEntity.getUserId(), recommendRecordEntity.getMovieIds(), new Timestamp(System.currentTimeMillis()));
    }
}
