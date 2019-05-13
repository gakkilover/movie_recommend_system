package com.zwk.movie_recommend.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.SearchRecordEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-12 15:56
 * @ Description：
 */
public interface SearchRecordDao extends BaseMapper<SearchRecordEntity> {

    @Select(" select max(search_record_id)+1 from search_record where 1=1 ")
    Long getMaxId();

    @Select(" select m.* from movie m " +
            " LEFT JOIN search_record sr on sr.movie_id=m.movie_id " +
            " where sr.user_id=${userId} ")
    List<MovieEntity> getSearchRecord(@Param("userId")Long userId);
}
