package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.CommentRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:32
 * @description:
 **/
@Mapper
public interface CommentRecordDao extends BaseMapper<CommentRecordEntity> {

    @Select(" select max(comment_id)+1 from comment_record where 1=1 ")
    Long getMaxId();

    @Select(" select * from comment_record where 1=1 ${sql} ")
    List<CommentRecordEntity> getList(@Param("sql")String sql);


}
