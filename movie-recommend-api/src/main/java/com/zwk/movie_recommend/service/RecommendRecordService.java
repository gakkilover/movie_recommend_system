package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.RecommendRecordEntity;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 17:07
 * @ Description：
 */
public interface RecommendRecordService extends IService<RecommendRecordEntity> {
    // 根据用户id获取推荐电影信息
    RecommendRecordEntity getRectabByUserId(Long userid);

    int insertRectab(RecommendRecordEntity recommendRecordEntity);

    void updateRectab(RecommendRecordEntity recommendRecordEntity);
}
