package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.CollectRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:13
 * @description:
 **/
@Mapper
public interface CollectRecordDao extends BaseMapper<CollectRecordEntity> {

    @Select(" select max(collect_record_id)+1 from collect_record where 1=1 ")
    Long getMaxId();

    @Select(" select count(*) from collect_record where user_id=#{userId} and movie_ids like '%,${1}%.' ")
    int judgeIsLike(@Param("movieId")Long movieId, @Param("userId")String userId);

    @Select(" select *  from  collect_record where 1=1 ${sql} ")
    List<CollectRecordEntity> getLsit(@Param("sql") String sql);


}
