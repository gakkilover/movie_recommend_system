package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 19:41
 * @description:
 **/
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    @Select(" select max(user_id)+1 from user where 1=1 ")
    Long getMaxId();

    @Select(" select * from  user where user_name='${userName}' ")
    List<UserEntity> login(@Param("userName")String userName);

    @Select(" select * from  user where 1=1 ${sql}")
    List<UserEntity> getUser(@Param("sql")String sql);
}
