package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.MovieTagRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:53
 * @description:
 **/
@Mapper
public interface MovieTagRelationDao extends BaseMapper<MovieTagRelationEntity> {

    @Select(" select * from movie_tag_relation where movie_id=${movieId} ")
    List<MovieTagRelationEntity> getByMovieId(@Param("movieId") Long movieId);

    @Select(" SELECT m.* FROM(" +
            " SELECT * FROM movie_tag_relation WHERE tag_id = ${tagId}) mtr " +
            " LEFT JOIN ( " +
            " SELECT * FROM movie WHERE movie_averating = 5) m  " +
            " ON mtr.movie_id = m.movie_id " +
            " ORDER BY m.movie_averating DESC,m.movie_rate_num ")
    List<MovieEntity> getMovieByTagId(@Param("tagId") Long tagId);

    @Select(" select * from movie_tag_relation where 1=1 ")
    List<MovieTagRelationEntity> getList();
}
