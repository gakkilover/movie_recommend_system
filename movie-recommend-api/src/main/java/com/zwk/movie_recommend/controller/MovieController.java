package com.zwk.movie_recommend.controller;

import com.zwk.common.utils.ListUtils;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-16 09:41
 * @ Description：
 */
@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    //搜索电影
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public ResultView selectMoviesByName(HttpServletRequest request){
        String moviename = request.getParameter("search_text");
        request.getSession().setAttribute("searchInfo", moviename);
        //     HttpContextUtils.setValueInSession("searchInfo", moviename);
        if(moviename == null || moviename ==""){
            System.out.print("不能为空");
            return null;
        }
        else{
            System.out.print("搜索内容"+moviename);
            List<MovieEntity> list = movieService.selectMoviesByName(moviename);
            list = ListUtils.getTop10(list);
            ResultView resultView = ResultView.ok(list);
            return resultView;
        }
    }
}
