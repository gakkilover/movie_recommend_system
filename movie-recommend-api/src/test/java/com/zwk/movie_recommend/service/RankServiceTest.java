package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.RankDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.RankEntity;
import com.zwk.movie_recommend.service.impl.RankServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：电影榜单服务单元测试 - 按照unit-test-generator技能生成
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RankService 单元测试")
public class RankServiceTest {

    @Mock
    private RankDao rankDao;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private RankServiceImpl rankService;

    private List<RankEntity> mockRankList;
    private RankEntity mockRankEntity;
    private MovieEntity mockMovie;

    @BeforeEach
    public void setUp() {
        mockRankList = new ArrayList<>();
        
        mockRankEntity = new RankEntity();
        mockRankEntity.setRankId(1L);
        mockRankEntity.setRankName("Top250");
        mockRankEntity.setRankType("top250");
        mockRankEntity.setMovieId(100L);
        mockRankEntity.setRankPosition(1);
        mockRankList.add(mockRankEntity);
        
        mockMovie = new MovieEntity();
        mockMovie.setMovieId(100L);
        mockMovie.setMovieName("Test Movie");
        mockMovie.setMovieAverating(8.5);
    }

    @Nested
    @DisplayName("getRankList - 获取榜单列表")
    class GetRankList {

        @Test
        @DisplayName("正常获取榜单列表")
        public void testGetRankList_Success() {
            ResultView resultView = rankService.getRankList();
            
            assertNotNull(resultView);
            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("返回数据类型验证")
        public void testGetRankList_DataType() {
            ResultView resultView = rankService.getRankList();
            
            assertTrue(resultView.getData() instanceof ArrayList);
        }
    }

    @Nested
    @DisplayName("getRankByType - 根据类型获取榜单")
    class GetRankByType {

        @Test
        @DisplayName("正常获取top250类型榜单")
        public void testGetRankByType_Top250_Success() {
            when(rankDao.selectByRankType("top250")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(100L)).thenReturn(mockMovie);

            ResultView resultView = rankService.getRankByType("top250");

            assertAll("验证top250榜单", () -> {
                assertNotNull(resultView);
                assertEquals(200, resultView.getStatus());
            });
            verify(rankDao).selectByRankType("top250");
        }

        @Test
        @DisplayName("正常获取monthly类型榜单")
        public void testGetRankByType_Monthly_Success() {
            when(rankDao.selectByRankType("monthly")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getRankByType("monthly");

            assertEquals(200, resultView.getStatus());
            verify(rankDao).selectByRankType("monthly");
        }

        @Test
        @DisplayName("正常获取genre类型榜单")
        public void testGetRankByType_Genre_Success() {
            when(rankDao.selectByRankType("genre")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getRankByType("genre");

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("榜单数据为空")
        public void testGetRankByType_Empty() {
            when(rankDao.selectByRankType("top250")).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getRankByType("top250");

            assertAll("验证空榜单", () -> {
                assertNotNull(resultView);
                assertEquals(404, resultView.getStatus());
                assertEquals("暂无榜单数据", resultView.getMessage());
            });
        }

        @Test
        @DisplayName("边界测试 - 不存在的榜单类型")
        public void testGetRankByType_NonExistent() {
            when(rankDao.selectByRankType("nonexistent")).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getRankByType("nonexistent");

            assertEquals(404, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - 空类型名称")
        public void testGetRankByType_EmptyType() {
            when(rankDao.selectByRankType("")).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getRankByType("");

            assertEquals(404, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getTop250 - 获取Top250榜单")
    class GetTop250 {

        @Test
        @DisplayName("正常获取Top250榜单")
        public void testGetTop250_Success() {
            List<RankEntity> topList = new ArrayList<>();
            RankEntity rank = new RankEntity();
            rank.setMovieId(100L);
            topList.add(rank);
            
            when(rankDao.selectTop250()).thenReturn(topList);
            when(movieService.getMovieByMovieid(100L)).thenReturn(mockMovie);

            ResultView resultView = rankService.getTop250();

            assertAll("验证Top250", () -> {
                assertNotNull(resultView);
                assertEquals(200, resultView.getStatus());
            });
            verify(rankDao).selectTop250();
        }

        @Test
        @DisplayName("Top250榜单为空")
        public void testGetTop250_Empty() {
            when(rankDao.selectTop250()).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getTop250();

            assertEquals(404, resultView.getStatus());
        }

        @Test
        @DisplayName("电影信息获取失败")
        public void testGetTop250_MovieNotFound() {
            List<RankEntity> topList = new ArrayList<>();
            RankEntity rank = new RankEntity();
            rank.setMovieId(999L);
            topList.add(rank);
            
            when(rankDao.selectTop250()).thenReturn(topList);
            when(movieService.getMovieByMovieid(999L)).thenReturn(null);

            ResultView resultView = rankService.getTop250();

            assertNotNull(resultView);
            assertEquals(200, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getCurrentMonthRank - 获取本月榜单")
    class GetCurrentMonthRank {

        @Test
        @DisplayName("正常获取本月榜单 - 有数据")
        public void testGetCurrentMonthRank_HasData() {
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            int currentMonth = cal.get(Calendar.MONTH) + 1;
            
            when(rankDao.selectMonthlyRank(currentYear, currentMonth)).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getCurrentMonthRank();

            assertEquals(200, resultView.getStatus());
            verify(rankDao).selectMonthlyRank(currentYear, currentMonth);
        }

        @Test
        @DisplayName("正常获取本月榜单 - 无数据使用最新")
        public void testGetCurrentMonthRank_UseLatest() {
            Calendar cal = Calendar.getInstance();
            int currentYear = cal.get(Calendar.YEAR);
            int currentMonth = cal.get(Calendar.MONTH) + 1;
            
            when(rankDao.selectMonthlyRank(currentYear, currentMonth)).thenReturn(new ArrayList<>());
            when(rankDao.selectLatestMonthlyRank()).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getCurrentMonthRank();

            assertAll("验证使用最新数据", () -> {
                assertEquals(200, resultView.getStatus());
                verify(rankDao).selectLatestMonthlyRank();
            });
        }

        @Test
        @DisplayName("本月和最新榜单都为空")
        public void testGetCurrentMonthRank_AllEmpty() {
            when(rankDao.selectMonthlyRank(anyInt(), anyInt())).thenReturn(new ArrayList<>());
            when(rankDao.selectLatestMonthlyRank()).thenReturn(null);

            ResultView resultView = rankService.getCurrentMonthRank();

            assertEquals(404, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getMonthlyRank - 获取指定月份榜单")
    class GetMonthlyRank {

        @Test
        @DisplayName("正常获取指定月份榜单")
        public void testGetMonthlyRank_Success() {
            when(rankDao.selectMonthlyRank(2026, 3)).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getMonthlyRank(2026, 3);

            assertAll("验证月份榜单", () -> {
                assertNotNull(resultView);
                assertEquals(200, resultView.getStatus());
            });
            verify(rankDao).selectMonthlyRank(2026, 3);
        }

        @Test
        @DisplayName("边界测试 - 1月")
        public void testGetMonthlyRank_January() {
            when(rankDao.selectMonthlyRank(2026, 1)).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getMonthlyRank(2026, 1);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - 12月")
        public void testGetMonthlyRank_December() {
            when(rankDao.selectMonthlyRank(2026, 12)).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getMonthlyRank(2026, 12);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - 年份边界 1900")
        public void testGetMonthlyRank_Year1900() {
            when(rankDao.selectMonthlyRank(1900, 1)).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getMonthlyRank(1900, 1);

            assertEquals(404, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - 年份边界 2099")
        public void testGetMonthlyRank_Year2099() {
            when(rankDao.selectMonthlyRank(2099, 12)).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getMonthlyRank(2099, 12);

            assertEquals(404, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - 无效月份 0")
        public void testGetMonthlyRank_InvalidMonth0() {
            when(rankDao.selectMonthlyRank(2026, 0)).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getMonthlyRank(2026, 0);

            assertEquals(404, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - 无效月份 13")
        public void testGetMonthlyRank_InvalidMonth13() {
            when(rankDao.selectMonthlyRank(2026, 13)).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getMonthlyRank(2026, 13);

            assertEquals(404, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getGenreRank - 获取分类榜单")
    class GetGenreRank {

        @Test
        @DisplayName("正常获取分类榜单")
        public void testGetGenreRank_Success() {
            when(rankDao.selectGenreRank("action")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getGenreRank("action");

            assertAll("验证分类榜单", () -> {
                assertNotNull(resultView);
                assertEquals(200, resultView.getStatus());
            });
            verify(rankDao).selectGenreRank("action");
        }

        @Test
        @DisplayName("常见分类 - action")
        public void testGetGenreRank_Action() {
            when(rankDao.selectGenreRank("action")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getGenreRank("action");

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("常见分类 - comedy")
        public void testGetGenreRank_Comedy() {
            when(rankDao.selectGenreRank("comedy")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getGenreRank("comedy");

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("常见分类 - drama")
        public void testGetGenreRank_Drama() {
            when(rankDao.selectGenreRank("drama")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getGenreRank("drama");

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("常见分类 - horror")
        public void testGetGenreRank_Horror() {
            when(rankDao.selectGenreRank("horror")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getGenreRank("horror");

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("常见分类 - sci-fi")
        public void testGetGenreRank_SciFi() {
            when(rankDao.selectGenreRank("sci-fi")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = rankService.getGenreRank("sci-fi");

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - 空分类名称")
        public void testGetGenreRank_Empty() {
            when(rankDao.selectGenreRank("")).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getGenreRank("");

            assertEquals(404, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - 不存在的分类")
        public void testGetGenreRank_NotFound() {
            when(rankDao.selectGenreRank("unknown")).thenReturn(new ArrayList<>());

            ResultView resultView = rankService.getGenreRank("unknown");

            assertEquals(404, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - 电影服务返回null")
        public void testGetGenreRank_MovieServiceNull() {
            when(rankDao.selectGenreRank("action")).thenReturn(mockRankList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(null);

            ResultView resultView = rankService.getGenreRank("action");

            assertNotNull(resultView);
            assertEquals(200, resultView.getStatus());
        }
    }
}
