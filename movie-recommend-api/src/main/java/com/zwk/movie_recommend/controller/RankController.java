package com.zwk.movie_recommend.controller;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.RankEntity;
import com.zwk.movie_recommend.service.RankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：电影榜单控制器
 */
@Api(tags = "电影榜单", description = "Top250、月度榜单、分类榜单")
@RestController
@RequestMapping("/api/rank")
public class RankController {

    @Autowired
    private RankService rankService;

    /**
     * 获取所有榜单列表
     */
    @GetMapping("/list")
    @ApiOperation("获取榜单列表")
    public ResultView getRankList() {
        return rankService.getRankList();
    }

    /**
     * 根据类型获取榜单
     */
    @GetMapping("/{rankType}")
    @ApiOperation("根据类型获取榜单")
    public ResultView getRankByType(@PathVariable String rankType) {
        return rankService.getRankByType(rankType);
    }

    /**
     * 获取Top250榜单
     */
    @GetMapping("/top250")
    @ApiOperation("获取Top250榜单")
    public ResultView getTop250() {
        return rankService.getTop250();
    }

    /**
     * 获取本月榜单
     */
    @GetMapping("/monthly")
    @ApiOperation("获取本月榜单")
    public ResultView getCurrentMonthRank() {
        return rankService.getCurrentMonthRank();
    }

    /**
     * 获取指定月份榜单
     */
    @GetMapping("/monthly/{year}/{month}")
    @ApiOperation("获取指定月份榜单")
    public ResultView getMonthlyRank(@PathVariable Integer year, @PathVariable Integer month) {
        return rankService.getMonthlyRank(year, month);
    }

    /**
     * 获取分类榜单
     */
    @GetMapping("/genre/{genre}")
    @ApiOperation("获取分类榜单")
    public ResultView getGenreRank(@PathVariable String genre) {
        return rankService.getGenreRank(genre);
    }
}
