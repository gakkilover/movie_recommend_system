package com.zwk.movie_recommend.controller;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.TagEntity;
import com.zwk.movie_recommend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15 10:00
 * @description：电影标签查询
 */
@Controller
public class TagController {

    @Autowired
    private TagService tagService;
    /**
     * 查询所有电影标签
     * @return
     */
    @RequestMapping("/tag/tagList")
    public ResultView tagList(HttpServletRequest request){
        ResultView resultView = tagService.getCategoryList();
        List<TagEntity> tagList = null;
        if(resultView.getStatus().equals(200)){
            tagList = (List<TagEntity>)resultView.getData();
        }
        request.getSession().setAttribute("tagList" , tagList);
        return tagService.getCategoryList();
    }

}
