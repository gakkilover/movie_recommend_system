package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.CollectDetailEntity;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 16:02
 * @ Description：
 */
public interface CollectDetailService extends IService<CollectDetailEntity> {

    void insertCollectDetail(CollectDetailEntity collectDetail);

    void insertByStrings(String movieIds, Long userId);

    List<CollectDetailEntity> getTop10Collect();

    List<CollectDetailEntity> getByUser(Long userId,Long movieId);
}
