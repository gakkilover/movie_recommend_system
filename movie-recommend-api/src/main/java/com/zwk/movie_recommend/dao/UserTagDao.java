package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.UserTagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-17 15:23
 * @ Description：
 */
@Mapper
public interface UserTagDao extends BaseMapper<UserTagEntity> {

    @Select(" select max(user_tag_id)+1 from user_tag where 1=1 ")
    Long getMaxId();

    @Select(" select * from user_tag where 1=1 ${sql} ")
    List<UserTagEntity> getList(@Param("sql")String sql);
}
