package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.zwk.common.constant.Final;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.CollectDetailDao;
import com.zwk.movie_recommend.entity.CollectDetailEntity;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.service.MovieService;
import com.zwk.movie_recommend.service.WatchStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：观看状态服务实现类（想看/在看/已看）
 */
@Service
public class WatchStatusServiceImpl extends ServiceImpl<CollectDetailDao, CollectDetailEntity> implements WatchStatusService {

    public static final int WATCH_STATUS_WISH = 0;
    public static final int WATCH_STATUS_WATCHING = 1;
    public static final int WATCH_STATUS_WATCHED = 2;
    public static final int WATCH_STATUS_CANCELLED = 3;

    @Autowired
    private CollectDetailDao collectDetailDao;

    @Autowired
    private MovieService movieService;

    @Override
    public ResultView setWatchStatus(Long userId, Long movieId, Integer watchStatus) {
        if (userId == null || movieId == null || watchStatus == null) {
            return ResultView.build(400, "参数不完整");
        }

        if (watchStatus < 0 || watchStatus > 3) {
            return ResultView.build(400, "无效的观看状态");
        }

        EntityWrapper<CollectDetailEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("movie_id", movieId);
        CollectDetailEntity existEntity = selectOne(wrapper);

        Date now = new Date();

        if (watchStatus == WATCH_STATUS_CANCELLED) {
            if (existEntity != null) {
                deleteById(existEntity.getCollectDetailId());
            }
            return ResultView.ok("已取消");
        }

        if (existEntity == null) {
            existEntity = new CollectDetailEntity();
            existEntity.setUserId(userId);
            existEntity.setMovieId(movieId);
            existEntity.setCollectDate(now);
        }
        existEntity.setWatchStatus(watchStatus);
        existEntity.setUpdateTime(now);

        if (existEntity.getCollectDetailId() == null) {
            insert(existEntity);
        } else {
            updateById(existEntity);
        }

        String statusMsg = getStatusMessage(watchStatus);
        return ResultView.ok(statusMsg);
    }

    @Override
    public ResultView getWishList(Long userId) {
        return getListByStatus(userId, WATCH_STATUS_WISH);
    }

    @Override
    public ResultView getWatchingList(Long userId) {
        return getListByStatus(userId, WATCH_STATUS_WATCHING);
    }

    @Override
    public ResultView getWatchedList(Long userId) {
        return getListByStatus(userId, WATCH_STATUS_WATCHED);
    }

    @Override
    public ResultView removeWatchStatus(Long userId, Long movieId) {
        EntityWrapper<CollectDetailEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("movie_id", movieId);
        CollectDetailEntity entity = selectOne(wrapper);

        if (entity != null) {
            deleteById(entity.getCollectDetailId());
            return ResultView.ok("已移除");
        }
        return ResultView.build(404, "记录不存在");
    }

    @Override
    public ResultView getWatchStatus(Long userId, Long movieId) {
        EntityWrapper<CollectDetailEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("movie_id", movieId);
        CollectDetailEntity entity = selectOne(wrapper);

        if (entity != null) {
            return ResultView.ok(entity.getWatchStatus());
        }
        return ResultView.ok(null);
    }

    private ResultView getListByStatus(Long userId, Integer watchStatus) {
        if (userId == null) {
            return ResultView.build(400, "用户ID不能为空");
        }

        EntityWrapper<CollectDetailEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("watch_status", watchStatus);
        wrapper.orderDesc(Lists.newArrayList("collect_date"));
        List<CollectDetailEntity> list = selectList(wrapper);

        List<MovieEntity> movies = new ArrayList<>();
        for (CollectDetailEntity entity : list) {
            MovieEntity movie = movieService.getMovieByMovieid(entity.getMovieId());
            if (movie != null) {
                movies.add(movie);
            }
        }

        return ResultView.ok(movies);
    }

    private String getStatusMessage(Integer watchStatus) {
        switch (watchStatus) {
            case WATCH_STATUS_WISH:
                return "已加入想看";
            case WATCH_STATUS_WATCHING:
                return "已加入在看";
            case WATCH_STATUS_WATCHED:
                return "已标记为已看";
            case WATCH_STATUS_CANCELLED:
                return "已取消";
            default:
                return "操作成功";
        }
    }
}
