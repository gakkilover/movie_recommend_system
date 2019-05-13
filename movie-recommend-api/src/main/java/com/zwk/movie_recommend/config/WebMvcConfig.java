package com.zwk.movie_recommend.config;

import com.zwk.common.constant.Final;
import com.zwk.movie_recommend.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-05-10 15:00
 * @description: 设置web配置
 **/
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> list = new ArrayList<>();
        list.add("/register");
        list.add("/homepage");
        list.add("/index");
        list.add("/Customer/Description");
        list.add("/MovieDescription");
        list.add("/typesortmovie");
        list.add("/getSimiMovies");
        list.add("/search");
        list.add("/customer/**");
        list.add("/tag/tagList");
        list.add("/home");
        list.add("/likedTags");
        list.add("/login");
        list.add("/page/login");
        list.add("/customer/login");
        list.add("/error");
        list.add("/");
        list.add("");
//        registry.addInterceptor(authorizationInterceptor).
//                addPathPatterns("/**").addPathPatterns("/getstar").
//                excludePathPatterns(list);
    }
}
