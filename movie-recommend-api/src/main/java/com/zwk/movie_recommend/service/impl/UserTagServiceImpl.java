package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.UserTagDao;
import com.zwk.movie_recommend.entity.UserTagEntity;
import com.zwk.movie_recommend.service.UserTagService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-17 15:26
 * @ Description：
 */
@Service("userTagService")
public class UserTagServiceImpl extends ServiceImpl<UserTagDao, UserTagEntity> implements UserTagService {

    @Autowired
    private UserTagDao userTagDao;

    @Override
    public void insertUserTag(String tagIds, Long userId) {
        String[] tagIdList = tagIds.split(",");
        for (String tagId: tagIdList) {
            if(!tagId.equals("") && StringUtils.isNotBlank(tagId)) {
                UserTagEntity userTagEntity = new UserTagEntity();
                userTagEntity.setUserTagId(userTagDao.getMaxId());
                userTagEntity.setRecordDate(new Date());
                userTagEntity.setUserId(userId);
                userTagEntity.setTagIds(tagId);
                userTagDao.insert(userTagEntity);
            }
        }
    }

    @Override
    public List<UserTagEntity> getByUserId(Long userId) {
        StringBuffer sql = new StringBuffer();
        sql.append(" and user_id= "+userId);
        return userTagDao.getList(sql.toString());
    }
}
