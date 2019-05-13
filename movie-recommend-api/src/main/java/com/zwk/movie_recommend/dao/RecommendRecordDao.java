package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.RecommendRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 20:30
 * @description:
 **/
@Mapper
public interface RecommendRecordDao extends BaseMapper<RecommendRecordEntity> {

    @Select(" select * from recommend_record where user_id=#{userId}")
    List<RecommendRecordEntity> getByUserId(@Param("userId")Long userId);

    @Update("update recommend_record set movie_ids='${movieids}',recommend_date='${recDate}' where user_id=${userId} ORDER BY recommend_date desc LIMIT 1 ")
    void updateByUserId(@Param("userId")Long userId, @Param("movieids")String movieids, @Param("recDate")Date recDate);

    @Select(" select max(recommend_record_id)+1 from recommend_record where 1=1 ")
    Long getMaxId();
}
