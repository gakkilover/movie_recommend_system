package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.CollectDetailEntity;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：观看状态服务接口（想看/在看/已看）
 */
public interface WatchStatusService extends IService<CollectDetailEntity> {

    /**
     * 设置观看状态
     *
     * @param userId      用户ID
     * @param movieId     电影ID
     * @param watchStatus 观看状态: 0-想看, 1-在看, 2-已看, 3-取消
     * @return 操作结果
     */
    ResultView setWatchStatus(Long userId, Long movieId, Integer watchStatus);

    /**
     * 获取想看列表
     *
     * @param userId 用户ID
     * @return 想看电影列表
     */
    ResultView getWishList(Long userId);

    /**
     * 获取在看列表
     *
     * @param userId 用户ID
     * @return 在看电影列表
     */
    ResultView getWatchingList(Long userId);

    /**
     * 获取已看列表
     *
     * @param userId 用户ID
     * @return 已看电影列表
     */
    ResultView getWatchedList(Long userId);

    /**
     * 移除观看状态
     *
     * @param userId  用户ID
     * @param movieId 电影ID
     * @return 操作结果
     */
    ResultView removeWatchStatus(Long userId, Long movieId);

    /**
     * 获取电影观看状态
     *
     * @param userId  用户ID
     * @param movieId 电影ID
     * @return 观看状态
     */
    ResultView getWatchStatus(Long userId, Long movieId);
}
