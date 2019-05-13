package com.zwk.movie_recommend.controller;

import com.zwk.common.constant.Final;
import com.zwk.movie_recommend.entity.CollectDetailEntity;
import com.zwk.movie_recommend.entity.CollectRecordEntity;
import com.zwk.movie_recommend.entity.UserTagEntity;
import com.zwk.movie_recommend.service.CollectDetailService;
import com.zwk.movie_recommend.service.CollectRecordService;
import com.zwk.movie_recommend.service.UserTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-18 11:16
 * @ Description：
 */
@Controller
public class UserTagController {

    @Autowired
    private CollectDetailService collectDetailService;

    @Autowired
    private CollectRecordService collectRecordService;

    @Autowired
    private UserTagService userTagService;
    /**
     * 用户注册完成，选择喜欢的电影标签
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/likedTags", method = RequestMethod.POST)
    @ResponseBody
    public String likedmovie(HttpServletRequest request) throws ParseException {
        String tagIds = request.getParameter("tagIds");
        Long userid = (Long) request.getSession().getAttribute("userId");
        userTagService.insertUserTag(tagIds, userid);
        return Final.SUCCESS;
    }

}
