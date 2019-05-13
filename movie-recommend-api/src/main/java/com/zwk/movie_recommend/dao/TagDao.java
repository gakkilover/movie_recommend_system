package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.TagEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:27
 * @description:
 **/
@Mapper
public interface TagDao extends BaseMapper<TagEntity> {

    @Select(" select * from tag where 1=1 ${sql} ")
    List<TagEntity> getList(@Param("sql")String sql);
}
