package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.RankEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：电影榜单数据访问层
 */
@Mapper
public interface RankDao extends BaseMapper<RankEntity> {

    /**
     * 根据榜单类型获取榜单列表
     *
     * @param rankType 榜单类型
     * @return 榜单列表
     */
    List<RankEntity> selectByRankType(@Param("rankType") String rankType);

    /**
     * 获取Top250榜单
     *
     * @return Top250榜单列表
     */
    List<RankEntity> selectTop250();

    /**
     * 根据年份和月份获取月度榜单
     *
     * @param year  年份
     * @param month 月份
     * @return 月度榜单列表
     */
    List<RankEntity> selectMonthlyRank(@Param("year") Integer year, @Param("month") Integer month);

    /**
     * 根据分类获取分类榜单
     *
     * @param genre 电影分类/标签
     * @return 分类榜单列表
     */
    List<RankEntity> selectGenreRank(@Param("genre") String genre);

    /**
     * 获取最新月度榜单
     *
     * @return 最新月度榜单
     */
    RankEntity selectLatestMonthlyRank();
}
