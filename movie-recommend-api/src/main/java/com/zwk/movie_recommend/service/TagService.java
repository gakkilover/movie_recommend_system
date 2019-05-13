package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.TagEntity;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 11:40
 * @ Description：
 */
public interface TagService extends IService<TagEntity> {
    //获取所有分类标签
    ResultView getCategoryList();
}
