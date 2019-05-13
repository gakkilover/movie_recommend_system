package com.zwk.movie_recommend.controller;

import com.zwk.common.constant.Final;
import com.zwk.common.utils.GetMessageCodeUtils;
import com.zwk.movie_recommend.calculate.CalMovieHeat;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.*;
import com.zwk.movie_recommend.redis.CacheData;
import com.zwk.movie_recommend.redis.RedisService;
import com.zwk.movie_recommend.service.*;
import com.zwk.movie_recommend.utils.RedisUtils2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-25 14:40
 * @ Description：
 */
@Controller
@Api(tags = "用户操作》",description = "接口")
//@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CollectRecordService collectRecordService;

    @Autowired
    private CollectDetailService collectDetailService;

    @Autowired
    private TagService tagService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CommentRecordService commentRecordService;

    @Autowired
    private RedisService redisService;

//    @Resource
//    private RedisUtils2 redisUtils2;


//    @Autowired
//    private JedisPool jedisPool;
//
//    @Autowired
//    private RedisUtils2 redisUtils2 ;

    //进入登录页面

    @ResponseBody
    @RequestMapping("/page/login")
    @ApiOperation("跳转到登录界面")
    public String log() {
        return "login";
    }



    // 判断登录账号是否存在
//    @RequestMapping(value = "/customer/login", method = RequestMethod.POST)
    @RequestMapping("/customer/login")
    @ResponseBody
    public ResultView login(String userName, String userPassword, HttpServletRequest request) throws ParseException {
        ResultView resultView = userService.userLogin(userName, userPassword);
        UserEntity user = null;
        // 判断是否登录成功
        if (resultView.getStatus() == 200) {
            user = (UserEntity) resultView.getData();
        }
        request.getSession().setAttribute("user", user);
        // 返回结果
        return resultView;
    }

    //用户退出
    @RequestMapping("/customer/logout")
    public String pagelogout( HttpServletRequest request){
        //注销seesion
        request.getSession().removeAttribute("user");
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("userstar");
        request.getSession().removeAttribute("reviews");
        request.getSession().removeAttribute("booluserunlikedmovie");
        request.getSession().removeAttribute("TopRegDefaultMovie");
        return "Home";
    }

    //跳转到主页
    @RequestMapping("/home")
    public String toHome(){
        return "Home";
    }

    //新用户选择喜欢的电影
    @RequestMapping(value = "/customer/register/movieSubmit",method = RequestMethod.POST)
    @ResponseBody
    public String  selectedMovie(String ids ,HttpServletRequest request){
        //没有选择电影则不插入数据
        if(ids== "" || ids==null){
            System.out.print("为空");
            return  "fail";
        }
        else {
            //获取的用户id
            Long userId = (Long) request.getSession().getAttribute("userId");

            CollectRecordEntity collectRecordEntity = new CollectRecordEntity();
            //存用户名
            collectRecordEntity.setUserId(userId);
            collectRecordEntity.setMovieIds(ids);
//            userService.selectFavorite(collectRecordEntity);
            collectRecordService.insertCollectRecord(collectRecordEntity);

            collectDetailService.insertByStrings(ids, userId);
            return "ok";
        }
    }

    //用户更新个人信息
    @RequestMapping("/updateUser")
    public ResultView updateUser(UserEntity userEntity,HttpServletRequest request){
        ResultView resultView = userService.updateUser(userEntity);
        if(resultView.getStatus().equals(Final.SUCCESS_STATUS))  {
            UserEntity userEntity1 = (UserEntity)resultView.getData();
            request.getSession().setAttribute("userInfo", userEntity1);
        }
        return resultView;
    }

    //编辑用户
    @RequestMapping("/editUser")
    public ResultView editUser(Long userId,HttpServletRequest request){
        UserEntity userEntity = userService.getUserById(userId);
        return ResultView.ok(userEntity);
    }

    //个人中心按钮
    @RequestMapping("/profile")
    public String showProfie() {
        return "profile";
    }

    // 点击个人中心按钮
    @RequestMapping(value = "/customer/profile")
    @ResponseBody
    public String goProfile(HttpServletRequest request) {
        // 拿到userid
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
        request.getSession().setAttribute("userInfo", user);
        Long userid = user.getUserId();
        // 影评的电影list
        List<CommentRecordEntity> commentRecordEntities = commentRecordService.getReviewListByUserId(userid);
        List<MovieEntity> movies = new ArrayList<MovieEntity>();
        // 喜欢的电影list
        CollectRecordEntity collectRecordEntity = collectRecordService.getBrowseByUserId(userid);
        if (collectRecordEntity !=null && null != collectRecordEntity.getMovieIds()) {
            String movieids = collectRecordEntity.getMovieIds().replace(".","").substring(1);
            String[] strmovieids = movieids.split(",");
            for (String strmovieid: strmovieids) {
                Long movieid = Long.parseLong(strmovieid);
                MovieEntity movieEntity = movieService.getMovieByMovieid(movieid);
                movies.add(movieEntity);
            }
        }
        // 为review list中添加电影url
        for (CommentRecordEntity commentRecordEntity : commentRecordEntities) {
            Long movieid = commentRecordEntity.getMovieId();
            MovieEntity movieEntity = movieService.getMovieByMovieid(movieid);
            commentRecordEntity.setMoviePic(movieEntity.getMoviePic()); //todo  设置电影图片url  应该连接电影表查询
        }
        request.getSession().setAttribute("movies", movies);
        request.getSession().setAttribute("commentRecordEntities", commentRecordEntities);

        return "success";
    }

}
