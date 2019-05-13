package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.CollectRecordEntity;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 11:21
 * @ Description：
 */
public interface CollectRecordService extends IService<CollectRecordEntity> {
    // 根据用户id获取Browse记录
    CollectRecordEntity getBrowseByUserId(Long userid);

    //插入用户点击喜欢的收藏
    void insertCollectRecord(CollectRecordEntity collectRecord);
}
