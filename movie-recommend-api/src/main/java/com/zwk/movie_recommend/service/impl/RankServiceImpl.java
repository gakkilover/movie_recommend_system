package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.RankDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.RankEntity;
import com.zwk.movie_recommend.service.MovieService;
import com.zwk.movie_recommend.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

/**
 * @author ：zwk
 * @email ：zwk0@qq.com
 * @date ：Created in 2026-03-15
 * @description：电影榜单服务实现类
 */
@Service
public class RankServiceImpl extends ServiceImpl<RankDao, RankEntity> implements RankService {

    @Autowired
    private RankDao rankDao;

    @Autowired
    private MovieService movieService;

    @Override
    public ResultView getRankList() {
        return null;
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

}
