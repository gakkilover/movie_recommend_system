package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.HeatMovieEntity;
import com.zwk.movie_recommend.entity.MovieEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-23 21:55
 * @description:
 **/
public interface HeatMovieDao extends BaseMapper<HeatMovieEntity> {

    @Select(" select max(heat_id)+1 from heat_movie where 1=1 ")
    Long getMaxId();

    @Select(" select * from heat_movie where 1=1 ${sql} ")
    List<HeatMovieEntity> getList(@Param("sql")String sql);

    @Select(" select * from heat_movie where 1=1 and movie_id = ${movieId} ")
    HeatMovieEntity getOne(@Param("movieId")Long movieId);

    @Select(" select m.* " +
            " from heat_movie hm " +
            " left JOIN movie m on hm.movie_id=m.movie_id " +
            " ORDER BY hm.update_date desc limit 0,10 ")
    List<MovieEntity> getTop10List();
}
