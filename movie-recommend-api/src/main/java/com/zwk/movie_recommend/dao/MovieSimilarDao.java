package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.MovieSimilarEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:52
 * @description:
 **/
@Mapper
public interface MovieSimilarDao extends BaseMapper<MovieSimilarEntity> {

    @Select(" select * from movie_similar where 1=1 ${sql} ")
    List<MovieSimilarEntity> getList(@Param("sql")String sql);

    @Select(" select movieId2 from movie_similar where 1=1 ${sql} ")
    List<String> selectSimilarMovies(@Param("sql")String sql);
}
