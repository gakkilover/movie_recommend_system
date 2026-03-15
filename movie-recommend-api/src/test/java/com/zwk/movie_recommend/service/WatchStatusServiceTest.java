package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.CollectDetailDao;
import com.zwk.movie_recommend.entity.CollectDetailEntity;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.service.impl.WatchStatusServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：观看状态服务单元测试 - 按照unit-test-generator技能生成
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("WatchStatusService 单元测试")
public class WatchStatusServiceTest {

    @Mock
    private CollectDetailDao collectDetailDao;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private WatchStatusServiceImpl watchStatusService;

    private CollectDetailEntity mockCollectDetail;
    private MovieEntity mockMovie;
    private static final Long USER_ID = 1L;
    private static final Long MOVIE_ID = 100L;

    @BeforeEach
    public void setUp() {
        mockCollectDetail = new CollectDetailEntity();
        mockCollectDetail.setCollectDetailId(1L);
        mockCollectDetail.setUserId(USER_ID);
        mockCollectDetail.setMovieId(MOVIE_ID);
        mockCollectDetail.setCollectDate(new Date());
        mockCollectDetail.setWatchStatus(0);

        mockMovie = new MovieEntity();
        mockMovie.setMovieId(MOVIE_ID);
        mockMovie.setMovieName("Test Movie");
        mockMovie.setMovieAverating(8.5);
    }

    @Nested
    @DisplayName("setWatchStatus - 设置观看状态")
    class SetWatchStatus {

        @Test
        @DisplayName("正常设置想看状态 - 新记录")
        public void testSetWatchStatus_Wish_NewRecord() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);
            when(collectDetailDao.insert(any(CollectDetailEntity.class))).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, 0);

            assertAll("验证想看状态设置", () -> {
                assertNotNull(resultView);
                assertEquals(200, resultView.getStatus());
                assertEquals("已加入想看", resultView.getMessage());
            });
            verify(collectDetailDao).insert(any(CollectDetailEntity.class));
        }

        @Test
        @DisplayName("正常设置想看状态 - 更新记录")
        public void testSetWatchStatus_Wish_UpdateRecord() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(mockCollectDetail);
            when(collectDetailDao.updateById(any(CollectDetailEntity.class))).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, 0);

            assertEquals(200, resultView.getStatus());
            verify(collectDetailDao).updateById(any(CollectDetailEntity.class));
        }

        @Test
        @DisplayName("正常设置在看状态")
        public void testSetWatchStatus_Watching() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);
            when(collectDetailDao.insert(any(CollectDetailEntity.class))).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, 1);

            assertAll("验证在看状态", () -> {
                assertEquals(200, resultView.getStatus());
                assertEquals("已加入在看", resultView.getMessage());
            });
        }

        @Test
        @DisplayName("正常设置已看状态")
        public void testSetWatchStatus_Watched() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);
            when(collectDetailDao.insert(any(CollectDetailEntity.class))).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, 2);

            assertAll("验证已看状态", () -> {
                assertEquals(200, resultView.getStatus());
                assertEquals("已标记为已看", resultView.getMessage());
            });
        }

        @Test
        @DisplayName("正常取消观看状态")
        public void testSetWatchStatus_Cancel() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(mockCollectDetail);
            when(collectDetailDao.deleteById(1L)).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, 3);

            assertAll("验证取消状态", () -> {
                assertEquals(200, resultView.getStatus());
                assertEquals("已取消", resultView.getMessage());
            });
            verify(collectDetailDao).deleteById(1L);
        }

        @Test
        @DisplayName("异常测试 - userId为null")
        public void testSetWatchStatus_NullUserId() {
            ResultView resultView = watchStatusService.setWatchStatus(null, MOVIE_ID, 0);

            assertEquals(400, resultView.getStatus());
            assertEquals("参数不完整", resultView.getMessage());
        }

        @Test
        @DisplayName("异常测试 - movieId为null")
        public void testSetWatchStatus_NullMovieId() {
            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, null, 0);

            assertEquals(400, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - watchStatus为null")
        public void testSetWatchStatus_NullStatus() {
            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, null);

            assertEquals(400, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - watchStatus为负数")
        public void testSetWatchStatus_NegativeStatus() {
            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, -1);

            assertEquals(400, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - watchStatus超出范围")
        public void testSetWatchStatus_OutOfRange() {
            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, 4);

            assertEquals(400, resultView.getStatus());
            assertEquals("无效的观看状态", resultView.getMessage());
        }

        @Test
        @DisplayName("边界测试 - watchStatus为最大值3")
        public void testSetWatchStatus_MaxStatus() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(mockCollectDetail);
            when(collectDetailDao.deleteById(1L)).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, MOVIE_ID, 3);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - movieId为0")
        public void testSetWatchStatus_MovieIdZero() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);
            when(collectDetailDao.insert(any(CollectDetailEntity.class))).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, 0L, 0);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("边界测试 - movieId为负数")
        public void testSetWatchStatus_MovieIdNegative() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);
            when(collectDetailDao.insert(any(CollectDetailEntity.class))).thenReturn(1);

            ResultView resultView = watchStatusService.setWatchStatus(USER_ID, -1L, 0);

            assertEquals(200, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getWishList - 获取想看列表")
    class GetWishList {

        @Test
        @DisplayName("正常获取想看列表")
        public void testGetWishList_Success() {
            List<CollectDetailEntity> mockList = new ArrayList<>();
            mockCollectDetail.setWatchStatus(0);
            mockList.add(mockCollectDetail);
            
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(mockList);
            when(movieService.getMovieByMovieid(MOVIE_ID)).thenReturn(mockMovie);

            ResultView resultView = watchStatusService.getWishList(USER_ID);

            assertAll("验证想看列表", () -> {
                assertNotNull(resultView);
                assertEquals(200, resultView.getStatus());
            });
            verify(collectDetailDao).selectList(any(EntityWrapper.class));
        }

        @Test
        @DisplayName("想看列表为空")
        public void testGetWishList_Empty() {
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(new ArrayList<>());

            ResultView resultView = watchStatusService.getWishList(USER_ID);

            assertAll("验证空列表", () -> {
                assertNotNull(resultView);
                assertEquals(200, resultView.getStatus());
                assertNotNull(resultView.getData());
            });
        }

        @Test
        @DisplayName("异常测试 - userId为null")
        public void testGetWishList_NullUserId() {
            ResultView resultView = watchStatusService.getWishList(null);

            assertEquals(400, resultView.getStatus());
            assertEquals("用户ID不能为空", resultView.getMessage());
        }

        @Test
        @DisplayName("电影信息获取失败")
        public void testGetWishList_MovieNotFound() {
            List<CollectDetailEntity> mockList = new ArrayList<>();
            mockCollectDetail.setWatchStatus(0);
            mockList.add(mockCollectDetail);
            
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(mockList);
            when(movieService.getMovieByMovieid(MOVIE_ID)).thenReturn(null);

            ResultView resultView = watchStatusService.getWishList(USER_ID);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("多条想看记录")
        public void testGetWishList_MultipleRecords() {
            List<CollectDetailEntity> mockList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                CollectDetailEntity detail = new CollectDetailEntity();
                detail.setWatchStatus(0);
                detail.setMovieId((long) (100 + i));
                mockList.add(detail);
            }
            
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(mockList);
            when(movieService.getMovieByMovieid(anyLong())).thenReturn(mockMovie);

            ResultView resultView = watchStatusService.getWishList(USER_ID);

            assertEquals(200, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getWatchingList - 获取在看列表")
    class GetWatchingList {

        @Test
        @DisplayName("正常获取在看列表")
        public void testGetWatchingList_Success() {
            List<CollectDetailEntity> mockList = new ArrayList<>();
            mockCollectDetail.setWatchStatus(1);
            mockList.add(mockCollectDetail);
            
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(mockList);
            when(movieService.getMovieByMovieid(MOVIE_ID)).thenReturn(mockMovie);

            ResultView resultView = watchStatusService.getWatchingList(USER_ID);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("在看列表为空")
        public void testGetWatchingList_Empty() {
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(new ArrayList<>());

            ResultView resultView = watchStatusService.getWatchingList(USER_ID);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - userId为null")
        public void testGetWatchingList_NullUserId() {
            ResultView resultView = watchStatusService.getWatchingList(null);

            assertEquals(400, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getWatchedList - 获取已看列表")
    class GetWatchedList {

        @Test
        @DisplayName("正常获取已看列表")
        public void testGetWatchedList_Success() {
            List<CollectDetailEntity> mockList = new ArrayList<>();
            mockCollectDetail.setWatchStatus(2);
            mockList.add(mockCollectDetail);
            
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(mockList);
            when(movieService.getMovieByMovieid(MOVIE_ID)).thenReturn(mockMovie);

            ResultView resultView = watchStatusService.getWatchedList(USER_ID);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("已看列表为空")
        public void testGetWatchedList_Empty() {
            when(collectDetailDao.selectList(any(EntityWrapper.class))).thenReturn(new ArrayList<>());

            ResultView resultView = watchStatusService.getWatchedList(USER_ID);

            assertEquals(200, resultView.getStatus());
        }

        @Test
        @DisplayName("异常测试 - userId为null")
        public void testGetWatchedList_NullUserId() {
            ResultView resultView = watchStatusService.getWatchedList(null);

            assertEquals(400, resultView.getStatus());
        }
    }

    @Nested
    @DisplayName("getWatchStatus - 获取电影观看状态")
    class GetWatchStatus {

        @Test
        @DisplayName("正常获取观看状态 - 想看")
        public void testGetWatchStatus_Wish() {
            mockCollectDetail.setWatchStatus(0);
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(mockCollectDetail);

            ResultView resultView = watchStatusService.getWatchStatus(USER_ID, MOVIE_ID);

            assertAll("验证想看状态", () -> {
                assertEquals(200, resultView.getStatus());
                assertEquals(0, resultView.getData());
            });
        }

        @Test
        @DisplayName("正常获取观看状态 - 在看")
        public void testGetWatchStatus_Watching() {
            mockCollectDetail.setWatchStatus(1);
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(mockCollectDetail);

            ResultView resultView = watchStatusService.getWatchStatus(USER_ID, MOVIE_ID);

            assertEquals(1, resultView.getData());
        }

        @Test
        @DisplayName("正常获取观看状态 - 已看")
        public void testGetWatchStatus_Watched() {
            mockCollectDetail.setWatchStatus(2);
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(mockCollectDetail);

            ResultView resultView = watchStatusService.getWatchStatus(USER_ID, MOVIE_ID);

            assertEquals(2, resultView.getData());
        }

        @Test
        @DisplayName("未添加观看状态")
        public void testGetWatchStatus_NotAdded() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);

            ResultView resultView = watchStatusService.getWatchStatus(USER_ID, MOVIE_ID);

            assertAll("验证未添加状态", () -> {
                assertEquals(200, resultView.getStatus());
                assertNull(resultView.getData());
            });
        }
    }

    @Nested
    @DisplayName("removeWatchStatus - 移除观看状态")
    class RemoveWatchStatus {

        @Test
        @DisplayName("正常移除观看状态")
        public void testRemoveWatchStatus_Success() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(mockCollectDetail);
            when(collectDetailDao.deleteById(1L)).thenReturn(1);

            ResultView resultView = watchStatusService.removeWatchStatus(USER_ID, MOVIE_ID);

            assertAll("验证移除成功", () -> {
                assertEquals(200, resultView.getStatus());
                assertEquals("已移除", resultView.getMessage());
            });
            verify(collectDetailDao).deleteById(1L);
        }

        @Test
        @DisplayName("记录不存在")
        public void testRemoveWatchStatus_NotFound() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);

            ResultView resultView = watchStatusService.removeWatchStatus(USER_ID, MOVIE_ID);

            assertAll("验证记录不存在", () -> {
                assertEquals(404, resultView.getStatus());
                assertEquals("记录不存在", resultView.getMessage());
            });
        }

        @Test
        @DisplayName("边界测试 - 不存在的movieId")
        public void testRemoveWatchStatus_NonExistentMovie() {
            when(collectDetailDao.selectOne(any(EntityWrapper.class))).thenReturn(null);

            ResultView resultView = watchStatusService.removeWatchStatus(USER_ID, 999L);

            assertEquals(404, resultView.getStatus());
        }
    }
}
