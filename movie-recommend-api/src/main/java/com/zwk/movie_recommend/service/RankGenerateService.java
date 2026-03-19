package com.zwk.movie_recommend.service;

import com.zwk.movie_recommend.entity.RankEntity;
import com.zwk.movie_recommend.common.ResultView;

import java.util.List;

/**
 * 排名生成服务
 * 用于动态计算和更新各种榜单
 */
public interface RankGenerateService {

    /**
     * 生成并保存Top250榜单
     * @return 生成结果
     */
    ResultView generateTop250();

    /**
     * 生成并保存月度榜单
     * @param year 年份
     * @param month 月份
     * @return 生成结果
     */
    ResultView generateMonthlyRank(Integer year, Integer month);

    /**
     * 生成并保存分类榜单
     * @param genre 电影分类
     * @return 生成结果
     */
    ResultView generateGenreRank(String genre);

    /**
     * 生成并保存所有榜单
     * @return 生成结果
     */
    ResultView generateAllRanks();
}