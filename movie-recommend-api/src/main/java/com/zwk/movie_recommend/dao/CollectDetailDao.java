package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.CollectDetailEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 15:54
 * @ Description：
 */
public interface CollectDetailDao extends BaseMapper<CollectDetailEntity> {

    @Select(" select max(collect_detail_id)+1 from collect_detail where 1=1 ")
    Long getMaxId();

    @Select(" select * from  collect_detail where 1=1 ${sql} ")
    List<CollectDetailEntity> getList(@Param("sql")String sql);
}
