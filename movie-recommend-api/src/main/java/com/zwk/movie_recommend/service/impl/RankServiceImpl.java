package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.common.constant.Final;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.RankDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.RankEntity;
import com.zwk.movie_recommend.service.MovieService;
import com.zwk.movie_recommend.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

    /**
     * 获取分类榜单
     *
     * @param genre 电影分类
     * @return 分类榜单列表
     */
    ResultView getGenreRank(String genre);

    /**
     * 保存单个榜单数据
     *
     * @param rankEntity 榜单实体
     * @return 保存结果
     */
    ResultView saveRank(RankEntity rankEntity);

    /**
     * 批量保存榜单数据
     *
     * @param rankList 榜单列表
     * @return 保存结果
     */
    ResultView saveRankList(List<RankEntity> rankList);
}

    @Override
    public ResultView getRankByType(String rankType) {
        List<RankEntity> rankList = rankDao.selectByRankType(rankType);
        return buildRankResult(rankList);
    }

    @Override
    public ResultView getTop250() {
        List<RankEntity> rankList = rankDao.selectTop250();
        return buildRankResult(rankList);
    }

    @Override
    public ResultView getCurrentMonthRank() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        List<RankEntity> rankList = rankDao.selectMonthlyRank(year, month);
        if (rankList == null || rankList.isEmpty()) {
            List<RankEntity> latestList = rankDao.selectLatestMonthlyRank();
            if (latestList != null && !latestList.isEmpty()) {
                rankList = latestList;
            }
        }
        return buildRankResult(rankList);
    }

    @Override
    public ResultView getMonthlyRank(Integer year, Integer month) {
        List<RankEntity> rankList = rankDao.selectMonthlyRank(year, month);
        return buildRankResult(rankList);
    }

    @Override
    public ResultView getGenreRank(String genre) {
        List<RankEntity> rankList = rankDao.selectGenreRank(genre);
        return buildRankResult(rankList);
    }

    @Override
    public ResultView saveRank(RankEntity rankEntity) {
        int result = rankDao.insertRank(rankEntity);
        return result > 0 ? ResultView.ok() : ResultView.build(500, "保存排名失败");
    }

    @Override
    public ResultView saveRankList(List<RankEntity> rankList) {
        int result = rankDao.insertBatchRank(rankList);
        return result > 0 ? ResultView.ok() : ResultView.build(500, "批量保存排名失败");
    }

    private ResultView buildRankResult(List<RankEntity> rankList) {
        if (rankList == null || rankList.isEmpty()) {
            return ResultView.build(404, "暂无榜单数据");
        }

        for (RankEntity rankEntity : rankList) {
            MovieEntity movie = movieService.getMovieByMovieid(rankEntity.getMovieId());
            rankEntity.setMovie(movie);
        }

        return ResultView.ok(rankList);
    }
}
