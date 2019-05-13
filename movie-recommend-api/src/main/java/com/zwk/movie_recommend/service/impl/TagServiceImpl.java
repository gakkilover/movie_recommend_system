package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.TagDao;
import com.zwk.movie_recommend.entity.TagEntity;
import com.zwk.movie_recommend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 11:43
 * @ Description：
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, TagEntity> implements TagService {

    @Autowired
    private TagDao tagDao;

    @Override
    public ResultView getCategoryList() {
        List<TagEntity> list = tagDao.getList("");
        if (list == null || list.size() == 0) {
            return ResultView.build(400, "获取分类标签错误");
        }
        return ResultView.ok(list);
    }
}
