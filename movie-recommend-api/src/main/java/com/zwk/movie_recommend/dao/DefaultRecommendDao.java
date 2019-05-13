package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.DefaultRecommendEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:54
 * @description:
 **/
@Mapper
public interface DefaultRecommendDao extends BaseMapper<DefaultRecommendEntity> {

    @Select(" SELECT * FROM default_recommend t,movie m WHERE t.movie_id=m.movie_id limit 10")
    List<DefaultRecommendEntity> selectRegDefaultMovie();

    @Select(" select * FROM default_recommend t,movie m WHERE t.movie_id=m.movie_id limit #{limit} ")
    List<DefaultRecommendEntity> selectTopDefaultMovie(@Param("limit")Integer limit);
}
