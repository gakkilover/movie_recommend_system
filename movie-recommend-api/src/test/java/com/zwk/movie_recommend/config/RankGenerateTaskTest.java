package com.zwk.movie_recommend.config;

import static org.mockito.Mockito.*;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.service.RankGenerateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Unit tests for RankGenerateTask
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({RankGenerateTask.class, LoggerFactory.class})
public class RankGenerateTaskTest {

    @Mock
    private RankGenerateService rankGenerateService;

    @InjectMocks
    private RankGenerateTask rankGenerateTask;

    @Mock
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Mock LoggerFactory.getLogger to return our mock logger
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.when(LoggerFactory.getLogger(RankGenerateTask.class)).thenReturn(logger);
    }

    /**
     * Test generateTop250Task when service returns success
     */
    @Test
    public void testGenerateTop250TaskSuccess() {
        // Arrange
        ResultView successResult = new ResultView();
        successResult.setStatus(200);
        successResult.setMsg("Success");
        when(rankGenerateService.generateTop250()).thenReturn(successResult);

        // Act
        rankGenerateTask.generateTop250Task();

        // Assert
        verify(rankGenerateService).generateTop250();
        verify(logger).info("开始执行Top250榜单生成任务");
        verify(logger).info("Top250榜单生成任务执行成功: Success");
        verify(logger).info("Top250榜单生成任务执行结束");
    }

    /**
     * Test generateTop250Task when service returns failure
     */
    @Test
    public void testGenerateTop250TaskFailure() {
        // Arrange
        ResultView failureResult = new ResultView();
        failureResult.setStatus(500);
        failureResult.setMsg("Failure");
        when(rankGenerateService.generateTop250()).thenReturn(failureResult);

        // Act
        rankGenerateTask.generateTop250Task();

        // Assert
        verify(rankGenerateService).generateTop250();
        verify(logger).info("开始执行Top250榜单生成任务");
        verify(logger).error("Top250榜单生成任务执行失败: Failure");
        verify(logger).info("Top250榜单生成任务执行结束");
    }

    /**
     * Test generateTop250Task when service returns null
     */
    @Test
    public void testGenerateTop250TaskNullResult() {
        // Arrange
        when(rankGenerateService.generateTop250()).thenReturn(null);

        // Act
        rankGenerateTask.generateTop250Task();

        // Assert
        verify(rankGenerateService).generateTop250();
        verify(logger).info("开始执行Top250榜单生成任务");
        verify(logger).error("Top250榜单生成任务执行失败: 结果为null");
        verify(logger).info("Top250榜单生成任务执行结束");
    }

    /**
     * Test generateTop250Task when service throws exception
     */
    @Test
    public void testGenerateTop250TaskException() {
        // Arrange
        when(rankGenerateService.generateTop250()).thenThrow(new RuntimeException("Test exception"));

        // Act
        rankGenerateTask.generateTop250Task();

        // Assert
        verify(rankGenerateService).generateTop250();
        verify(logger).info("开始执行Top250榜单生成任务");
        verify(logger).error("Top250榜单生成任务执行异常", any(RuntimeException.class));
        verify(logger).info("Top250榜单生成任务执行结束");
    }

    /**
     * Test generateMonthlyRankTask when service returns success
     */
    @Test
    public void testGenerateMonthlyRankTaskSuccess() {
        // Arrange
        ResultView successResult = new ResultView();
        successResult.setStatus(200);
        successResult.setMsg("Monthly success");
        when(rankGenerateService.generateMonthlyRank(anyInt(), anyInt())).thenReturn(successResult);

        // Act
        rankGenerateTask.generateMonthlyRankTask();

        // Assert
        verify(rankGenerateService).generateMonthlyRank(anyInt(), anyInt());
        verify(logger).info("开始执行月度榜单生成任务");
        verify(logger).info("月度榜单生成任务执行成功: Monthly success");
        verify(logger).info("月度榜单生成任务执行结束");
    }

    /**
     * Test generateGenreRankTask when service returns success
     */
    @Test
    public void testGenerateGenreRankTaskSuccess() {
        // Arrange
        ResultView successResult = new ResultView();
        successResult.setStatus(200);
        successResult.setMsg("Genre success");
        when(rankGenerateService.generateGenreRank(anyString())).thenReturn(successResult);

        // Act
        rankGenerateTask.generateGenreRankTask();

        // Assert
        verify(rankGenerateService, atLeastOnce()).generateGenreRank(anyString());
        verify(logger).info("开始执行分类榜单生成任务");
        // Verify that info log was called for each genre (7 genres in the list)
        verify(logger, times(7)).info("{}榜单生成任务执行成功: {}", anyString(), eq("Genre success"));
        verify(logger).info("分类榜单生成任务执行结束");
    }

    /**
     * Test generateAllRanksTask when service returns success
     */
    @Test
    public void testGenerateAllRanksTaskSuccess() {
        // Arrange
        ResultView successResult = new ResultView();
        successResult.setStatus(200);
        successResult.setMsg("All ranks success");
        when(rankGenerateService.generateTop250()).thenReturn(successResult);
        when(rankGenerateService.generateMonthlyRank(anyInt(), anyInt())).thenReturn(successResult);
        when(rankGenerateService.generateGenreRank(anyString())).thenReturn(successResult);

        // Act
        rankGenerateTask.generateAllRanksTask();

        // Assert
        verify(rankGenerateService).generateTop250();
        verify(rankGenerateService).generateMonthlyRank(anyInt(), anyInt());
        verify(rankGenerateService, atLeastOnce()).generateGenreRank(anyString());
        verify(logger).info("开始执行所有榜单生成任务");
        verify(logger).info("所有榜单生成任务执行成功: All ranks success");
        verify(logger).info("所有榜单生成任务执行结束");
    }
}