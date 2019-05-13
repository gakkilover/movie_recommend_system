package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.CommentRecordEntity;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 17:27
 * @ Description：
 */
public interface CommentRecordService extends IService<CommentRecordEntity> {

    //进入个人中心前获取其全部的影评
    List<CommentRecordEntity> getReviewListByUserId(Long userId);

    /**
     * 用户评分
     * @param commentRecordEntity
     */
    void  setStar(CommentRecordEntity commentRecordEntity);

    //搜索影评,用于用户评价一部电影后立即获取其评价的信息
    ResultView getReview(Long userid, Long movieid);

    List<CommentRecordEntity> getTop10Comment();

}
