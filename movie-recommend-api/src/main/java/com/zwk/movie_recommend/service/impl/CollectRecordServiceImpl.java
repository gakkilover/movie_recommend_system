package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.CollectRecordDao;
import com.zwk.movie_recommend.entity.CollectRecordEntity;
import com.zwk.movie_recommend.service.CollectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-09 20:49
 * @description:
 **/
@Service("rowseService")
public class CollectRecordServiceImpl extends ServiceImpl<CollectRecordDao, CollectRecordEntity> implements CollectRecordService {

    @Autowired
    private CollectRecordDao collectRecordDao;

    @Override
    public CollectRecordEntity getBrowseByUserId(Long userid) {
        StringBuffer sql = new StringBuffer();
        sql.append(" and user_id= " + userid);
        List<CollectRecordEntity> browseList = collectRecordDao.getLsit(sql.toString());
        if(browseList.size()!=0)
            return browseList.get(0);
        else
            return  null;
    }

    @Override
    public void insertCollectRecord(CollectRecordEntity collectRecord) {

        StringBuffer sql = new StringBuffer();
        sql.append(" and user_id= " + collectRecord.getUserId());
        List<CollectRecordEntity> collectRecordtList = collectRecordDao.getLsit(sql.toString());
        CollectRecordEntity hisRecord = null;
        if(collectRecordtList !=null &&collectRecordtList.size()>0){
            hisRecord = collectRecordtList.get(0);
            hisRecord.setMovieIds(hisRecord.getMovieIds()+collectRecord.getMovieIds());
            hisRecord.setCollectDate(new Timestamp(System.currentTimeMillis()));
            collectRecordDao.updateById(hisRecord);
        }else{
            collectRecord.setCollectRecordId(collectRecordDao.getMaxId());
            collectRecord.setCollectDate(new Timestamp(System.currentTimeMillis()));
            collectRecordDao.insert(collectRecord);
        }
    }
}
