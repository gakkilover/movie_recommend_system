package com.zwk.movie_recommend.controller;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.service.RankService;
import io.swagger.annotations.ApiOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：电影榜单控制器单元测试 - 按照unit-test-generator技能生成
 */
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

            ResponseEntity<ResultView> response = rankController.getRankList();

            assertAll("验证榜单列表响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(200, response.getBody().getStatus());
            });
            verify(rankService, times(1)).getRankList();
        }

        @Test
        @DisplayName("榜单列表为空")
        public void testGetRankList_Empty() {
            when(rankService.getRankList()).thenReturn(emptyResult);

            ResponseEntity<ResultView> response = rankController.getRankList();

            assertAll("验证空榜单列表响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(404, response.getBody().getStatus());
            });
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

            ResponseEntity<ResultView> response = rankController.getRankByType(rankType);

            assertAll("验证类型榜单响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(200, response.getBody().getStatus());
            });
            verify(rankService, times(1)).getRankByType(rankType);
        }

        @Test
        @DisplayName("获取不存在的榜单类型")
        public void testGetRankByType_NotFound() {
            String rankType = "nonexistent";
            when(rankService.getRankByType(rankType)).thenReturn(emptyResult);

            ResponseEntity<ResultView> response = rankController.getRankByType(rankType);

            assertAll("验证不存在类型响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(404, response.getBody().getStatus());
            });
        }

        @Test
        @DisplayName("测试不同榜单类型参数 - monthly")
        public void testGetRankByType_MonthlyType() {
            String rankType = "monthly";
            when(rankService.getRankByType(rankType)).thenReturn(successResult);

            ResponseEntity<ResultView> response = rankController.getRankByType(rankType);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(rankService).getRankByType("monthly");
        }

        @Test
        @DisplayName("测试不同榜单类型参数 - genre")
        public void testGetRankByType_GenreType() {
            String rankType = "genre";
            when(rankService.getRankByType(rankType)).thenReturn(successResult);

            ResponseEntity<ResultView> response = rankController.getRankByType(rankType);

            assertEquals(HttpStatus.OK, response.getStatusCode());
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

            ResponseEntity<ResultView> response = rankController.getTop250();

            assertAll("验证Top250响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(200, response.getBody().getStatus());
            });
            verify(rankService, times(1)).getTop250();
        }

        @Test
        @DisplayName("Top250数据为空")
        public void testGetTop250_Empty() {
            when(rankService.getTop250()).thenReturn(emptyResult);

            ResponseEntity<ResultView> response = rankController.getTop250();

            assertAll("验证空Top250响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(404, response.getBody().getStatus());
            });
        }

        @Test
        @DisplayName("Top250服务异常")
        public void testGetTop250_Error() {
            when(rankService.getTop250()).thenReturn(errorResult);

            ResponseEntity<ResultView> response = rankController.getTop250();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(500, response.getBody().getStatus());
        }
    }

    @Nested
    @DisplayName("getCurrentMonthRank - 获取本月榜单")
    class GetCurrentMonthRank {

        @Test
        @DisplayName("正常获取本月榜单")
        public void testGetCurrentMonthRank_Success() {
            when(rankService.getCurrentMonthRank()).thenReturn(successResult);

            ResponseEntity<ResultView> response = rankController.getCurrentMonthRank();

            assertAll("验证本月榜单响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(200, response.getBody().getStatus());
            });
            verify(rankService).getCurrentMonthRank();
        }

        @Test
        @DisplayName("本月榜单为空时使用最新数据")
        public void testGetCurrentMonthRank_Empty() {
            when(rankService.getCurrentMonthRank()).thenReturn(emptyResult);

            ResponseEntity<ResultView> response = rankController.getCurrentMonthRank();

            assertEquals(HttpStatus.OK, response.getStatusCode());
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

            ResponseEntity<ResultView> response = rankController.getMonthlyRank(year, month);

            assertAll("验证月份榜单响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(200, response.getBody().getStatus());
            });
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
        @DisplayName("边界测试 - 年份边界")
        public void testGetMonthlyRank_YearBoundary() {
            when(rankService.getMonthlyRank(1900, 1)).thenReturn(emptyResult);
            when(rankService.getMonthlyRank(2099, 12)).thenReturn(emptyResult);

            rankController.getMonthlyRank(1900, 1);
            rankController.getMonthlyRank(2099, 12);

            verify(rankService, times(2)).getMonthlyRank(anyInt(), anyInt());
        }

        @Test
        @DisplayName("异常测试 - 无效月份")
        public void testGetMonthlyRank_InvalidMonth() {
            when(rankService.getMonthlyRank(2026, 13)).thenReturn(emptyResult);

            ResponseEntity<ResultView> response = rankController.getMonthlyRank(2026, 13);

            assertEquals(HttpStatus.OK, response.getStatusCode());
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

            ResponseEntity<ResultView> response = rankController.getGenreRank(genre);

            assertAll("验证分类榜单响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(200, response.getBody().getStatus());
            });
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

            ResponseEntity<ResultView> response = rankController.getGenreRank(genre);

            assertEquals(404, response.getBody().getStatus());
        }
    }
}
