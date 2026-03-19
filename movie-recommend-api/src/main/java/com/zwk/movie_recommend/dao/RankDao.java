package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.RankEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @Select("SELECT * FROM movie_rank WHERE rank_type = #{rankType}")
    List<RankEntity> selectByRankType(@Param("rankType") String rankType);

    /**
     * 获取Top250榜单
     *
     * @return Top250榜单列表
     */
    @Select("SELECT * FROM movie_rank WHERE rank_type = 'top250' ORDER BY rank_position LIMIT 250")
    List<RankEntity> selectTop250();

    /**
     * 根据年份和月份获取月度榜单
     *
     * @param year  年份
     * @param month 月份
     * @return 月度榜单列表
     */
    @Select("SELECT * FROM movie_rank WHERE rank_type = 'monthly' AND YEAR(create_time) = #{year} AND MONTH(create_time) = #{month} ORDER BY rank_position")
    List<RankEntity> selectMonthlyRank(@Param("year") Integer year, @Param("month") Integer month);

    /**
     * 根据分类获取分类榜单
     *
     * @param genre 电影分类/标签
     * @return 分类榜单列表
     */
    @Select("SELECT * FROM movie_rank WHERE rank_type = 'genre' AND genre = #{genre} ORDER BY rank_position")
    List<RankEntity> selectGenreRank(@Param("genre") String genre);

    /**
     * 获取最新月度榜单
     *
     * @return 最新月度榜单列表
     */
    @Select("SELECT * FROM movie_rank WHERE rank_type = 'monthly' AND YEAR(create_time) = YEAR(CURDATE()) AND MONTH(create_time) = MONTH(CURDATE()) ORDER BY rank_position")
    List<RankEntity> selectLatestMonthlyRank();

    /**
     * 插入榜单数据
     *
     * @param rankEntity 榜单实体
     * @return 影响行数
     */
    @Insert("INSERT INTO movie_rank (rank_name, rank_type, movie_id, rank_position, genre, year, month, create_time) " +
            "VALUES (#{rankName}, #{rankType}, #{movieId}, #{rankPosition}, #{genre}, #{year}, #{month}, #{createTime})")
    int insertRank(RankEntity rankEntity);

    /**
     * 批量插入榜单数据
     *
     * @param rankList 榜单列表
     * @return 影响行数
     */
    @Insert("INSERT INTO movie_rank (rank_name, rank_type, movie_id, rank_position, genre, year, month, create_time) VALUES " +
            "<foreach collection=\"list\" item=\"rank\" index=\"index\" separator=\",\">" +
            "(#{rank.rankName}, #{rank.rankType}, #{rank.movieId}, #{rank.rankPosition}, #{rank.genre}, #{rank.year}, #{rank.month}, #{rank.createTime})" +
            "</foreach>")
    int insertBatchRank(List<RankEntity> rankList);
}
