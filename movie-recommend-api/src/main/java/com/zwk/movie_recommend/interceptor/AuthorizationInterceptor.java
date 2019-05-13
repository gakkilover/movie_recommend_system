package com.zwk.movie_recommend.interceptor;

import com.zwk.movie_recommend.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-05-10 14:45
 * @description: 权限验证（用户判断用户是否登录以及各功能的使用）
 **/
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);
    /**
     * 进入拦截器立即执行验证
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("开始执行用户权限验证");

        HttpSession session = request.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");
        //用户未登录，则不具有评分，评论，查看个人信息等绝大部分功能
        if(user == null){
            logger.info("用户未登录");
            //response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }else {
            logger.info("用户已登录");
            return true;
        }
    }

    /***
     * 生成视图之前执行，可以对视图进行修改
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 生成视图时执行，可以用来处理异常，并记录在日志中
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
