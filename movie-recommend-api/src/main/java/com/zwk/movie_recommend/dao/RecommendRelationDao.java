package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.RecommendRelationEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:26
 * @description:
 **/
@Mapper
public interface RecommendRelationDao extends BaseMapper<RecommendRelationEntity> {

    @Select(" select m.* from recommend_relation rr " +
            " LEFT JOIN movie m on m.movie_id=rr.movie_id " +
            " where 1=1 ${sql} ")
    List<MovieEntity> getList(@Param("sql")String sql);

    @Select(" select max(recommend_relation_id)+1 from recommend_relation where 1=1 ")
    Long getMaxId();

    @Delete(" delete from recommend_relation where user_id=${userId}")
    void deleteRelation(@Param("userId")Long userId);
}
