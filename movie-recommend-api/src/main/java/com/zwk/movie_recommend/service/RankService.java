package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.RankEntity;

import java.util.List;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：电影榜单服务接口
 */
public interface RankService extends IService<RankEntity> {

    /**
     * 获取所有榜单类型列表
     *
     * @return 榜单列表
     */
    ResultView getRankList();

    /**
     * 根据榜单类型获取榜单
     *
     * @param rankType 榜单类型 (top250/monthly/genre)
     * @return 榜单列表
     */
    ResultView getRankByType(String rankType);

    /**
     * 获取Top250榜单
     *
     * @return Top250榜单列表
     */
    ResultView getTop250();

    /**
     * 获取本月榜单
     *
     * @return 本月榜单列表
     */
    ResultView getCurrentMonthRank();

    /**
     * 获取指定月份的榜单
     *
     * @param year  年份
     * @param month 月份
     * @return 月度榜单列表
     */
    ResultView getMonthlyRank(Integer year, Integer month);

    /**
     * 获取分类榜单
     *
     * @param genre 电影分类
     * @return 分类榜单列表
     */
    ResultView getGenreRank(String genre);
}
