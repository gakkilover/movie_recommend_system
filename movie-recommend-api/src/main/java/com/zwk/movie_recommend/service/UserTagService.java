package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.entity.UserTagEntity;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-17 15:25
 * @ Description：
 */
public interface UserTagService extends IService<UserTagEntity> {

    void insertUserTag(String tagIds, Long userId);

    List<UserTagEntity> getByUserId(Long userId);

}
