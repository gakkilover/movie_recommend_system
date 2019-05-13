package com.zwk.movie_recommend.controller;

import com.zwk.common.constant.Final;
import com.zwk.common.utils.GetMessageCodeUtils;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.TagEntity;
import com.zwk.movie_recommend.entity.UserEntity;
import com.zwk.movie_recommend.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-23 11:26
 * @ Description：
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultRecommendService defaultRecommendService;

    @Autowired
    private CollectRecordService collectRecordService;

    @Autowired
    private CollectDetailService collectDetailService;

    @Autowired
    private TagService tagService;

    //进入注册页面
    @RequestMapping("/customer/register")
    @ApiOperation("注册界面")
    public String reg(HttpServletRequest request, Model model) {
        //选择topmovies给用户选择她喜欢的电影
        List<MovieEntity> list = defaultRecommendService.SelectRegDefaultMovie();
        request.getSession().setAttribute("TopRegDefaultMovie",list);

        //给刚注册的用户提供标签选择
        ResultView resultView = tagService.getCategoryList();
        List<TagEntity> tagList = null;
        if(resultView.getStatus().equals(200)){
            tagList = (List<TagEntity>)resultView.getData();
        }
        request.getSession().setAttribute("tagList" , tagList);
        return "register";
    }

    //检查用户名/邮箱是否符合规范(在没有点击注册按钮前检查)
    @RequestMapping("/customer/check/{param}/{type}")
    @ResponseBody
    public ResultView checkData(@PathVariable String param, @PathVariable Integer type)   {
        //后端decode解码(如果前端输入的是中文)
        try {
            String str = URLDecoder.decode(param, "UTF-8");
            ResultView resultView = userService.checkData(str, type);
            return resultView;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return  null;
        }
    }

    //点击注册按钮后先对用户名和邮箱进行检查
    @RequestMapping("/customer/checkboth/{paramName}/{paramEmail}/{type}")
    @ResponseBody
    public ResultView checkDataBoth(@PathVariable String paramName, @PathVariable String paramEmail, @PathVariable Integer type) {
        //后端进行decode如果前端传中文值
        try {
            String str = URLDecoder.decode(paramName, "UTF-8");
            ResultView resultView = userService.checkDataBoth(str,paramEmail,type);
            return resultView;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return  null;
        }
    }

    //对用户进行注册(在全部检查完成后)
    @RequestMapping(value = "/customer/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultView register(UserEntity user, HttpServletRequest request) {
        //修改3.18 返回用户id,用于用户选择喜欢的电影后把相应信息存broswer表
        String phone = (String) request.getSession().getAttribute("phoneCode");
        if(!phone.equals(user.getPhoneCode())){
            return ResultView.build(400,"手机验证码不正确");
        }
        Long userId = 0L;
        ResultView resultView = userService.register(user);
        if (resultView.getStatus() == 200) {
            userId = (Long) resultView.getData();
        }
        request.getSession().setAttribute("userId", userId);
        return resultView;
    }

    //用户手机验证码
    @RequestMapping("/sendCode")
    public ResultView sendCode(@Param("userPhone")String userPhone, HttpServletRequest request){
        String phoneCode = GetMessageCodeUtils.getCode(userPhone);
        request.getSession().setAttribute("phoneCode", phoneCode);
        return ResultView.ok(Final.SUCCESS);
    }



}
