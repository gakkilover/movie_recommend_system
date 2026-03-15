package com.zwk.movie_recommend.controller;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.UserEntity;
import com.zwk.movie_recommend.service.WatchStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：观看状态控制器（想看/在看/已看）
 */
@Api(tags = "观看状态", description = "想看/在看/已看功能")
@RestController
@RequestMapping("/api/watch")
public class WatchStatusController {

    @Autowired
    private WatchStatusService watchStatusService;

    /**
     * 设置观看状态
     * watchStatus: 0-想看, 1-在看, 2-已看, 3-取消
     */
    @PostMapping("/setStatus")
    @ApiOperation("设置观看状态")
    public ResultView setWatchStatus(
            @RequestParam Long movieId,
            @RequestParam Integer watchStatus,
            HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultView.build(401, "请先登录");
        }
        return watchStatusService.setWatchStatus(user.getUserId(), movieId, watchStatus);
    }

    /**
     * 获取想看列表
     */
    @GetMapping("/wishList")
    @ApiOperation("获取想看列表")
    public ResultView getWishList(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultView.build(401, "请先登录");
        }
        return watchStatusService.getWishList(user.getUserId());
    }

    /**
     * 获取在看列表
     */
    @GetMapping("/watchingList")
    @ApiOperation("获取在看列表")
    public ResultView getWatchingList(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultView.build(401, "请先登录");
        }
        return watchStatusService.getWatchingList(user.getUserId());
    }

    /**
     * 获取已看列表
     */
    @GetMapping("/watchedList")
    @ApiOperation("获取已看列表")
    public ResultView getWatchedList(HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultView.build(401, "请先登录");
        }
        return watchStatusService.getWatchedList(user.getUserId());
    }

    /**
     * 移除观看状态
     */
    @PostMapping("/remove")
    @ApiOperation("移除观看状态")
    public ResultView removeWatchStatus(
            @RequestParam Long movieId,
            HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultView.build(401, "请先登录");
        }
        return watchStatusService.removeWatchStatus(user.getUserId(), movieId);
    }

    /**
     * 获取电影观看状态
     */
    @GetMapping("/status")
    @ApiOperation("获取电影观看状态")
    public ResultView getWatchStatus(
            @RequestParam Long movieId,
            HttpServletRequest request) {
        UserEntity user = (UserEntity) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultView.build(401, "请先登录");
        }
        return watchStatusService.getWatchStatus(user.getUserId(), movieId);
    }
}
