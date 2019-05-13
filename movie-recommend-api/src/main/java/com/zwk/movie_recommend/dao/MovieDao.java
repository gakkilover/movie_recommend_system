package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.MovieEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:29
 * @description:
 **/
@Mapper
public interface MovieDao extends BaseMapper<MovieEntity> {

    @Select("select * from movie where movie_id=${movieId}")
    MovieEntity getByMovieId(@Param("movieId") Long movieId);

    @Select(" ${sql} ")
    List<MovieEntity>  selectBycategory(@Param("sql")String sql);

    @Select(" select * from movie where movie_name like '%${movieName}%' ")
    List<MovieEntity> selectByName(@Param("movieName")String movieName);

    @Select(" select avg(comment_star) from comment_record where movie_id=${movieId} group by movie_id LIMIT 0,10 ")
    Double getAverageStar(@Param("movieId")Long movieId);

    @Select(" select r1.* from ( " +
            " SELECT mt.tag_id, m.* FROM user_tag ut " +
            " LEFT JOIN movie_tag_relation mt ON ut.tag_id = mt.tag_id " +
            " LEFT JOIN movie m ON mt.movie_id = m.movie_id " +
            " where ut.user_id = ${userId} " +
            " )as r1 " +
            " INNER JOIN( " +
            " SELECT mt.tag_id, max(m.movie_averating)as maxAverating, m.* FROM user_tag ut " +
            " LEFT JOIN movie_tag_relation mt ON ut.tag_id = mt.tag_id " +
            " LEFT JOIN movie m ON mt.movie_id = m.movie_id " +
            " where ut.user_id = ${userId} GROUP BY mt.tag_id " +
            " )as r2 on r1.tag_id=r2.tag_id and r1.movie_averating=r2.maxAverating " +
            " GROUP BY r1.tag_id ")
    List<MovieEntity> getMovieByTagUser(@Param("userId")Long userId);

    @Select(" SELECT r1.* FROM( " +
            " SELECT mt.tag_id, m.* FROM tag t " +
            " LEFT JOIN movie_tag_relation mt ON t.tag_id = mt.tag_id " +
            " LEFT JOIN movie m ON m.movie_id = mt.movie_id" +
            " ) AS r1 " +
            " INNER JOIN ( " +
            " SELECT mt.tag_id, MAX(m.movie_averating) AS maxAverating FROM tag t " +
            " LEFT JOIN movie_tag_relation mt ON t.tag_id = mt.tag_id " +
            " LEFT JOIN movie m ON m.movie_id = mt.movie_id GROUP BY t.tag_id " +
            " ) AS r2 ON r1.tag_id = r2.tag_id " +
            " AND r1.movie_averating = r2.maxAverating " +
            " GROUP BY r1.tag_id")
    List<MovieEntity> getMovieByTag();

    @Select(" select * from movie where 1=1 ")
    List<MovieEntity> getAllMovie();
}
