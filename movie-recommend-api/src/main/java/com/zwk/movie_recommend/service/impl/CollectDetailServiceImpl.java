package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.CollectDetailDao;
import com.zwk.movie_recommend.entity.CollectDetailEntity;
import com.zwk.movie_recommend.service.CollectDetailService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 16:03
 * @ Description：
 */
@Service("collectDetailService")
public class CollectDetailServiceImpl extends ServiceImpl<CollectDetailDao, CollectDetailEntity> implements CollectDetailService {

    @Autowired
    private CollectDetailDao collectDetailDao;

    @Override
    public void insertCollectDetail(CollectDetailEntity collectDetail) {
        collectDetail.setCollectDetailId(collectDetailDao.getMaxId());
        collectDetail.setCollectDate(new Timestamp(System.currentTimeMillis()));
        collectDetailDao.insert(collectDetail);
    }

    @Override
    public void insertByStrings(String strs, Long userId) {
        String[] movieIds = strs.split(",");
        CollectDetailEntity collectDetail = new CollectDetailEntity();
        for (String movieId: movieIds) {
            if(movieId != null && StringUtils.isNotBlank(movieId)){
                collectDetail.setCollectDetailId(collectDetailDao.getMaxId());
                collectDetail.setUserId(userId);
                collectDetail.setMovieId(Long.parseLong(movieId));
                collectDetail.setCollectDate(new Date());
                collectDetailDao.insert(collectDetail);
            }
        }
    }

    @Override
    public List<CollectDetailEntity> getTop10Collect() {
        StringBuffer sql = new StringBuffer();
        sql.append(" order by collect_date desc limit 0,10 ");
        return collectDetailDao.getList(sql.toString());
    }

    @Override
    public List<CollectDetailEntity> getByUser(Long userId, Long movieId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" and user_id= " + userId + " and movie_id= " + movieId);
        return collectDetailDao.getList(sql.toString());
    }


}
