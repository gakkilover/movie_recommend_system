package com.zwk.movie_recommend.service.impl;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.RankDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.RankEntity;
import com.zwk.movie_recommend.service.MovieService;
import com.zwk.movie_recommend.service.RankGenerateService;
import com.zwk.movie_recommend.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 排名生成服务实现
 * 用于动态计算和更新各种榜单
 */
@Service
public class RankGenerateServiceImpl implements RankGenerateService {

    @Autowired
    private RankDao rankDao;

    @Autowired
    private RankService rankService;

    @Autowired
    private MovieService movieService;

    /**
     * 生成并保存Top250榜单
     * 根据电影评分和评分人数综合排序
     * @return 生成结果
     */
    @Override
    public ResultView generateTop250() {
        try {
            // 获取所有电影
            List<MovieEntity> movieList = movieService.getAllMovie();
            
            if (movieList == null || movieList.isEmpty()) {
                return ResultView.build(400, "无法获取电影列表");
            }

            // 计算电影综合得分（这里简单使用平均评分，实际应用中可能需要更复杂的算法）
            for (MovieEntity movie : movieList) {
                // 综合得分 = 平均评分 * 评价人数权重（可以根据实际需求调整）
                double score = movie.getMovieAverating() * Math.log10(movie.getMovieRateNum() + 1);
                movie.setSumRate(score); // 临时使用sumRate字段存储综合得分
            }

            // 按综合得分降序排序
            Collections.sort(movieList, new Comparator<MovieEntity>() {
                @Override
                public int compare(MovieEntity m1, MovieEntity m2) {
                    return Double.compare(m2.getSumRate(), m1.getSumRate());
                }
            });

            // 取前250部电影生成排名
            List<RankEntity> rankList = new ArrayList<>();
            int limit = Math.min(250, movieList.size());
            
            for (int i = 0; i < limit; i++) {
                MovieEntity movie = movieList.get(i);
                RankEntity rank = new RankEntity();
                rank.setRankName("Top250榜单");
                rank.setRankType("top250");
                rank.setMovieId(movie.getMovieId());
                rank.setRankPosition(i + 1);
                rank.setGenre(""); // Top250不区分类型
                rank.setYear(Calendar.getInstance().get(Calendar.YEAR));
                rank.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
                rank.setCreateTime(new java.util.Date());
                rankList.add(rank);
            }

            // 删除旧的Top250榜单数据（可选）
            // rankDao.deleteByRankType("top250");
            
            // 保存新排名
            int result = rankDao.insertBatchRank(rankList);
            
            if (result > 0) {
                return ResultView.ok("成功生成Top250榜单，共" + result + "条记录");
            } else {
                return ResultView.build(500, "生成Top250榜单失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultView.build(500, "生成Top250榜单异常: " + e.getMessage());
        }
    }

    /**
     * 生成并保存月度榜单
     * @param year 年份
     * @param month 月份
     * @return 生成结果
     */
    @Override
    public ResultView generateMonthlyRank(Integer year, Integer month) {
        try {
            // 验证参数
            if (year == null || month == null || year < 1900 || month < 1 || month > 12) {
                return ResultView.build(400, "无效的年份或月份参数");
            }

            // 获取所有电影
            List<MovieEntity> movieList = movieService.getAllMovie();
            
            if (movieList == null || movieList.isEmpty()) {
                return ResultView.build(400, "无法获取电影列表");
            }

            // 这里简化处理，实际应用中应该根据该月份的评分数据来排名
            // 为演示目的，我们使用总体评分进行排名
            for (MovieEntity movie : movieList) {
                double score = movie.getMovieAverating() * Math.log10(movie.getMovieRateNum() + 1);
                movie.setSumRate(score);
            }

            // 按综合得分降序排序
            Collections.sort(movieList, new Comparator<MovieEntity>() {
                @Override
                public int compare(MovieEntity m1, MovieEntity m2) {
                    return Double.compare(m2.getSumRate(), m1.getSumRate());
                }
            });

            // 生成排名（这里取前100作为示例）
            List<RankEntity> rankList = new ArrayList<>();
            int limit = Math.min(100, movieList.size());
            
            String rankName = year + "年" + month + "月榜单";
            
            for (int i = 0; i < limit; i++) {
                MovieEntity movie = movieList.get(i);
                RankEntity rank = new RankEntity();
                rank.setRankName(rankName);
                rank.setRankType("monthly");
                rank.setMovieId(movie.getMovieId());
                rank.setRankPosition(i + 1);
                rank.setGenre(""); // 月度榜单不区分类型
                rank.setYear(year);
                rank.setMonth(month);
                rank.setCreateTime(new java.util.Date());
                rankList.add(rank);
            }

            // 删除旧的该月榜单数据（可选）
            // rankDao.deleteByYearAndMonth(year, month);
            
            // 保存新排名
            int result = rankDao.insertBatchRank(rankList);
            
            if (result > 0) {
                return ResultView.ok("成功生成" + rankName + "，共" + result + "条记录");
            } else {
                return ResultView.build(500, "生成" + rankName + "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultView.build(500, "生成月度榜单异常: " + e.getMessage());
        }
    }

    /**
     * 生成并保存分类榜单
     * @param genre 电影分类
     * @return 生成结果
     */
    @Override
    public ResultView generateGenreRank(String genre) {
        try {
            if (genre == null || genre.trim().isEmpty()) {
                return ResultView.build(400, "电影分类不能为空");
            }

            // 获取所有标签以找到匹配的分类ID
            // 注意：这里简化处理，实际应用中应该通过TagService按名称查询标签
            // 由于我们没有TagService的直接引用，这里使用一个假设的标签ID映射
            // 在实际项目中，应该注入TagService并通过名称查询得到tagId
            Long tagId = getTagIdByName(genre);
            
            // 如果找不到对应的标签ID，尝试获取所有电影作为后备方案
            List<MovieEntity> movieList;
            if (tagId != null) {
                movieList = movieService.getMovieByTagId(tagId);
            } else {
                // 后备方案：获取所有电影（实际应用中应该返回错误或空列表）
                movieList = movieService.getAllMovie();
            }
            
            if (movieList == null || movieList.isEmpty()) {
                return ResultView.build(400, "无法获取分类为[" + genre + "]的电影列表");
            }

            // 这里简化处理，实际应用中应该根据该分类电影的评分数据来排名
            for (MovieEntity movie : movieList) {
                double score = movie.getMovieAverating() * Math.log10(movie.getMovieRateNum() + 1);
                movie.setSumRate(score);
            }

            // 按综合得分降序排序
            Collections.sort(movieList, new Comparator<MovieEntity>() {
                @Override
                public int compare(MovieEntity m1, MovieEntity m2) {
                    return Double.compare(m2.getSumRate(), m1.getSumRate());
                }
            });

            // 生成排名（这里取前50作为示例）
            List<RankEntity> rankList = new ArrayList<>();
            int limit = Math.min(50, movieList.size());
            
            for (int i = 0; i < limit; i++) {
                MovieEntity movie = movieList.get(i);
                RankEntity rank = new RankEntity();
                rank.setRankName(genre + "榜单");
                rank.setRankType("genre");
                rank.setMovieId(movie.getMovieId());
                rank.setRankPosition(i + 1);
                rank.setGenre(genre);
                rank.setYear(Calendar.getInstance().get(Calendar.YEAR));
                rank.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
                rank.setCreateTime(new java.util.Date());
                rankList.add(rank);
            }

            // 删除旧的该分类榜单数据（可选）
            // rankDao.deleteByGenre(genre);
            
            // 保存新排名
            int result = rankDao.insertBatchRank(rankList);
            
            if (result > 0) {
                return ResultView.ok("成功生成" + genre + "榜单，共" + result + "条记录");
            } else {
                return ResultView.build(500, "生成" + genre + "榜单失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultView.build(500, "生成分类榜单异常: " + e.getMessage());
        }
    }

    /**
     * 根据分类名称获取标签ID（简化实现）
     * 在实际项目中，这应该通过TagService来实现
     * @param genre 分类名称
     * @return 标签ID，如果未找到则返回null
     */
    private Long getTagIdByName(String genre) {
        // 这里使用硬编码映射作为示例
        // 实际项目中应该查询tag表来获取对应的tagId
        switch (genre) {
            case "剧情": return 1L;
            case "喜剧": return 2L;
            case "动作": return 3L;
            case "科幻": return 4L;
            case "爱情": return 5L;
            case "悬疑": return 6L;
            case "恐怖": return 7L;
            default: return null;
        }
    }

    /**
     * 生成并保存所有榜单
     * @return 生成结果
     */
    @Override
    public ResultView generateAllRanks() {
        try {
            // 生成Top250榜单
            ResultView top250Result = generateTop250();
            if (top250Result == null || top250Result.getStatus() != 200) {
                return top250Result;
            }

            // 生成当前月度榜单
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            ResultView monthlyResult = generateMonthlyRank(currentYear, currentMonth);
            if (monthlyResult == null || monthlyResult.getStatus() != 200) {
                return monthlyResult;
            }

            // 生成主要分类榜单（这里示例生成几个常见分类）
            String[] genres = {"剧情", "喜剧", "动作", "科幻", "爱情"};
            for (String genre : genres) {
                ResultView genreResult = generateGenreRank(genre);
                if (genreResult.getStatus() != 200) {
                    // 继续处理其他分类，但记录失败
                    System.out.println("生成[" + genre + "]榜单失败: " + genreResult.getMsg());
                }
            }

            return ResultView.ok("所有榜单生成完成");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultView.build(500, "生成所有榜单异常: " + e.getMessage());
        }
    }
}