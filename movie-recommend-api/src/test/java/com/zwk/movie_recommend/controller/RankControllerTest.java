package com.zwk.movie_recommend.controller;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.service.RankService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RankController 单元测试")
public class RankControllerTest {

    @Mock
    private RankService rankService;

    @InjectMocks
    private RankController rankController;

    private ResultView successResult;
    private ResultView emptyResult;
    private ResultView errorResult;

    @BeforeEach
    public void setUp() {
        successResult = ResultView.ok(new ArrayList<>());
        emptyResult = ResultView.build(404, "暂无榜单数据");
        errorResult = ResultView.build(500, "服务器内部错误");
    }

    @Nested
    @DisplayName("getRankList - 获取榜单列表")
    class GetRankList {

        @Test
        @DisplayName("正常获取榜单列表")
        public void testGetRankList_Success() {
            when(rankService.getRankList()).thenReturn(successResult);

            ResultView result = rankController.getRankList();

            assertNotNull(result);
            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(rankService, times(1)).getRankList();
        }

        @Test
        @DisplayName("榜单列表为空")
        public void testGetRankList_Empty() {
            when(rankService.getRankList()).thenReturn(emptyResult);

            ResultView result = rankController.getRankList();

            assertEquals(Integer.valueOf(404), result.getStatus());
        }
    }

    @Nested
    @DisplayName("getRankByType - 根据类型获取榜单")
    class GetRankByType {

        @Test
        @DisplayName("正常获取指定类型榜单")
        public void testGetRankByType_Success() {
            String rankType = "top250";
            when(rankService.getRankByType(rankType)).thenReturn(successResult);

            ResultView result = rankController.getRankByType(rankType);

            assertNotNull(result);
            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(rankService, times(1)).getRankByType(rankType);
        }

        @Test
        @DisplayName("获取不存在的榜单类型")
        public void testGetRankByType_NotFound() {
            String rankType = "nonexistent";
            when(rankService.getRankByType(rankType)).thenReturn(emptyResult);

            ResultView result = rankController.getRankByType(rankType);

            assertEquals(Integer.valueOf(404), result.getStatus());
        }

        @Test
        @DisplayName("测试不同榜单类型参数 - monthly")
        public void testGetRankByType_MonthlyType() {
            String rankType = "monthly";
            when(rankService.getRankByType(rankType)).thenReturn(successResult);

            rankController.getRankByType(rankType);

            verify(rankService).getRankByType("monthly");
        }

        @Test
        @DisplayName("测试不同榜单类型参数 - genre")
        public void testGetRankByType_GenreType() {
            String rankType = "genre";
            when(rankService.getRankByType(rankType)).thenReturn(successResult);

            rankController.getRankByType(rankType);

            verify(rankService).getRankByType("genre");
        }
    }

    @Nested
    @DisplayName("getTop250 - 获取Top250榜单")
    class GetTop250 {

        @Test
        @DisplayName("正常获取Top250榜单")
        public void testGetTop250_Success() {
            when(rankService.getTop250()).thenReturn(successResult);

            ResultView result = rankController.getTop250();

            assertNotNull(result);
            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(rankService, times(1)).getTop250();
        }

        @Test
        @DisplayName("Top250数据为空")
        public void testGetTop250_Empty() {
            when(rankService.getTop250()).thenReturn(emptyResult);

            ResultView result = rankController.getTop250();

            assertEquals(Integer.valueOf(404), result.getStatus());
        }

        @Test
        @DisplayName("Top250服务异常")
        public void testGetTop250_Error() {
            when(rankService.getTop250()).thenReturn(errorResult);

            ResultView result = rankController.getTop250();

            assertEquals(Integer.valueOf(500), result.getStatus());
        }
    }

    @Nested
    @DisplayName("getCurrentMonthRank - 获取本月榜单")
    class GetCurrentMonthRank {

        @Test
        @DisplayName("正常获取本月榜单")
        public void testGetCurrentMonthRank_Success() {
            when(rankService.getCurrentMonthRank()).thenReturn(successResult);

            ResultView result = rankController.getCurrentMonthRank();

            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(rankService).getCurrentMonthRank();
        }

        @Test
        @DisplayName("本月榜单为空时使用最新数据")
        public void testGetCurrentMonthRank_Empty() {
            when(rankService.getCurrentMonthRank()).thenReturn(emptyResult);

            ResultView result = rankController.getCurrentMonthRank();

            assertEquals(Integer.valueOf(404), result.getStatus());
        }
    }

    @Nested
    @DisplayName("getMonthlyRank - 获取指定月份榜单")
    class GetMonthlyRank {

        @Test
        @DisplayName("正常获取指定月份榜单")
        public void testGetMonthlyRank_Success() {
            Integer year = 2026;
            Integer month = 3;
            when(rankService.getMonthlyRank(year, month)).thenReturn(successResult);

            ResultView result = rankController.getMonthlyRank(year, month);

            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(rankService).getMonthlyRank(year, month);
        }

        @Test
        @DisplayName("边界测试 - 1月")
        public void testGetMonthlyRank_January() {
            when(rankService.getMonthlyRank(2026, 1)).thenReturn(successResult);

            rankController.getMonthlyRank(2026, 1);

            verify(rankService).getMonthlyRank(2026, 1);
        }

        @Test
        @DisplayName("边界测试 - 12月")
        public void testGetMonthlyRank_December() {
            when(rankService.getMonthlyRank(2026, 12)).thenReturn(successResult);

            rankController.getMonthlyRank(2026, 12);

            verify(rankService).getMonthlyRank(2026, 12);
        }

        @Test
        @DisplayName("异常测试 - 无效月份")
        public void testGetMonthlyRank_InvalidMonth() {
            when(rankService.getMonthlyRank(2026, 13)).thenReturn(emptyResult);

            ResultView result = rankController.getMonthlyRank(2026, 13);

            assertEquals(Integer.valueOf(404), result.getStatus());
        }
    }

    @Nested
    @DisplayName("getGenreRank - 获取分类榜单")
    class GetGenreRank {

        @Test
        @DisplayName("正常获取分类榜单")
        public void testGetGenreRank_Success() {
            String genre = "action";
            when(rankService.getGenreRank(genre)).thenReturn(successResult);

            ResultView result = rankController.getGenreRank(genre);

            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(rankService).getGenreRank(genre);
        }

        @Test
        @DisplayName("常见电影分类 - action")
        public void testGetGenreRank_Action() {
            when(rankService.getGenreRank("action")).thenReturn(successResult);

            rankController.getGenreRank("action");

            verify(rankService).getGenreRank("action");
        }

        @Test
        @DisplayName("常见电影分类 - comedy")
        public void testGetGenreRank_Comedy() {
            when(rankService.getGenreRank("comedy")).thenReturn(successResult);

            rankController.getGenreRank("comedy");

            verify(rankService).getGenreRank("comedy");
        }

        @Test
        @DisplayName("常见电影分类 - drama")
        public void testGetGenreRank_Drama() {
            when(rankService.getGenreRank("drama")).thenReturn(successResult);

            rankController.getGenreRank("drama");

            verify(rankService).getGenreRank("drama");
        }

        @Test
        @DisplayName("常见电影分类 - horror")
        public void testGetGenreRank_Horror() {
            when(rankService.getGenreRank("horror")).thenReturn(successResult);

            rankController.getGenreRank("horror");

            verify(rankService).getGenreRank("horror");
        }

        @Test
        @DisplayName("空分类名称")
        public void testGetGenreRank_Empty() {
            when(rankService.getGenreRank("")).thenReturn(emptyResult);

            rankController.getGenreRank("");

            verify(rankService).getGenreRank("");
        }

        @Test
        @DisplayName("不存在的分类")
        public void testGetGenreRank_NotFound() {
            String genre = "unknown_genre";
            when(rankService.getGenreRank(genre)).thenReturn(emptyResult);

            ResultView result = rankController.getGenreRank(genre);

            assertEquals(Integer.valueOf(404), result.getStatus());
        }
    }
}
