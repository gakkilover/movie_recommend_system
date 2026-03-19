package com.zwk.movie_recommend.config;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.service.RankGenerateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 排名生成定时任务
 * 用于定期自动更新各种榜单
 */
@Component
public class RankGenerateTask {
    private Logger logger = LoggerFactory.getLogger(RankGenerateTask.class);

    @Autowired
    private RankGenerateService rankGenerateService;

    /**
     * 每天凌晨2点更新Top250榜单
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void generateTop250Task() {
        logger.info("开始执行Top250榜单生成任务");
        try {
            ResultView result = rankGenerateService.generateTop250();
            if (result != null && result.getStatus() == 200) {
                logger.info("Top250榜单生成任务执行成功: {}", result.getMsg());
            } else {
                logger.error("Top250榜单生成任务执行失败: {}", result != null ? result.getMsg() : "结果为null");
            }
        } catch (Exception e) {
            logger.error("Top250榜单生成任务执行异常", e);
        }
        logger.info("Top250榜单生成任务执行结束");
    }

    /**
     * 每天凌晨3点更新当前月度榜单
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void generateMonthlyRankTask() {
        logger.info("开始执行月度榜单生成任务");
        try {
            // 获取当前年月
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            int year = calendar.get(java.util.Calendar.YEAR);
            int month = calendar.get(java.util.Calendar.MONTH) + 1;
            
            ResultView result = rankGenerateService.generateMonthlyRank(year, month);
            if (result != null && result.getStatus() == 200) {
                logger.info("月度榜单生成任务执行成功: {}", result.getMsg());
            } else {
                logger.error("月度榜单生成任务执行失败: {}", result != null ? result.getMsg() : "结果为null");
            }
        } catch (Exception e) {
            logger.error("月度榜单生成任务执行异常", e);
        }
        logger.info("月度榜单生成任务执行结束");
    }

    /**
     * 每周日凌晨4点更新分类榜单
     */
    @Scheduled(cron = "0 0 4 ? * SUN")
    public void generateGenreRankTask() {
        logger.info("开始执行分类榜单生成任务");
        try {
            // 生成几个主要分类的榜单
            String[] genres = {"剧情", "喜剧", "动作", "科幻", "爱情", "悬疑", "恐怖"};
            for (String genre : genres) {
                ResultView result = rankGenerateService.generateGenreRank(genre);
                if (result != null && result.getStatus() == 200) {
                    logger.info("{}榜单生成任务执行成功: {}", genre, result.getMsg());
                } else {
                    logger.error("{}榜单生成任务执行失败: {}", genre, result != null ? result.getMsg() : "结果为null");
                }
            }
        } catch (Exception e) {
            logger.error("分类榜单生成任务执行异常", e);
        }
        logger.info("分类榜单生成任务执行结束");
    }

    /**
     * 每月1日早上5点重新生成所有榜单
     */
    @Scheduled(cron = "0 0 5 1 * ?")
    public void generateAllRanksTask() {
        logger.info("开始执行所有榜单生成任务");
        try {
            ResultView result = rankGenerateService.generateAllRanks();
            if (result != null && result.getStatus() == 200) {
                logger.info("所有榜单生成任务执行成功: {}", result.getMsg());
            } else {
                logger.error("所有榜单生成任务执行失败: {}", result != null ? result.getMsg () : "结果为null");
            }
        } catch (Exception e) {
            logger.error("所有榜单生成任务执行异常", e);
        }
        logger.info("所有榜单生成任务执行结束");
    }
}