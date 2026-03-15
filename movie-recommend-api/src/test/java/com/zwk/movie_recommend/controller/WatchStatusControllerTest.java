package com.zwk.movie_recommend.controller;

import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.UserEntity;
import com.zwk.movie_recommend.service.WatchStatusService;
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
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author     ：zwk
 * @email      ：zwk0@qq.com
 * @date       ：Created in 2026-03-15
 * @description：观看状态控制器单元测试 - 按照unit-test-generator技能生成
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("WatchStatusController 单元测试")
public class WatchStatusControllerTest {

    @Mock
    private WatchStatusService watchStatusService;

    @InjectMocks
    private WatchStatusController watchStatusController;

    private MockHttpServletRequest request;
    private UserEntity loggedInUser;
    private ResultView successResult;
    private ResultView notLoginResult;
    private ResultView errorResult;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        
        loggedInUser = new UserEntity();
        loggedInUser.setUserId(1L);
        loggedInUser.setUserName("testuser");
        
        successResult = ResultView.ok("操作成功");
        notLoginResult = ResultView.build(401, "请先登录");
        errorResult = ResultView.build(500, "服务器内部错误");
    }

    private void setLoginUser() {
        request.getSession().setAttribute("user", loggedInUser);
    }

    @Nested
    @DisplayName("setWatchStatus - 设置观看状态")
    class SetWatchStatus {

        @Test
        @DisplayName("正常设置想看状态")
        public void testSetWatchStatus_Wish_Success() {
            setLoginUser();
            Long movieId = 100L;
            Integer watchStatus = 0;
            when(watchStatusService.setWatchStatus(loggedInUser.getUserId(), movieId, watchStatus))
                    .thenReturn(successResult);

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(movieId, watchStatus, request);

            assertAll("验证想看状态设置响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(200, response.getBody().getStatus());
            });
            verify(watchStatusService).setWatchStatus(loggedInUser.getUserId(), movieId, watchStatus);
        }

        @Test
        @DisplayName("正常设置在看状态")
        public void testSetWatchStatus_Watching_Success() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), eq(1)))
                    .thenReturn(successResult);

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(100L, 1, request);

            assertEquals(200, response.getBody().getStatus());
        }

        @Test
        @DisplayName("正常设置已看状态")
        public void testSetWatchStatus_Watched_Success() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), eq(2)))
                    .thenReturn(successResult);

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(100L, 2, request);

            assertEquals(200, response.getBody().getStatus());
        }

        @Test
        @DisplayName("正常取消观看状态")
        public void testSetWatchStatus_Cancel_Success() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), eq(3)))
                    .thenReturn(ResultView.ok("已取消"));

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(100L, 3, request);

            assertEquals(200, response.getBody().getStatus());
        }

        @Test
        @DisplayName("未登录时设置观看状态")
        public void testSetWatchStatus_NotLoggedIn() {
            Long movieId = 100L;
            Integer watchStatus = 0;

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(movieId, watchStatus, request);

            assertAll("验证未登录响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertNotNull(response.getBody());
                assertEquals(401, response.getBody().getStatus());
                assertEquals("请先登录", response.getBody().getMessage());
            });
            verify(watchStatusService, never()).setWatchStatus(anyLong(), anyLong(), anyInt());
        }

        @Test
        @DisplayName("边界测试 - 电影ID为0")
        public void testSetWatchStatus_MovieIdZero() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), eq(0L), anyInt()))
                    .thenReturn(errorResult);

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(0L, 0, request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("边界测试 - 电影ID为负数")
        public void testSetWatchStatus_MovieIdNegative() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), eq(-1L), anyInt()))
                    .thenReturn(errorResult);

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(-1L, 0, request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        @DisplayName("异常测试 - 服务返回错误")
        public void testSetWatchStatus_ServiceError() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), anyInt()))
                    .thenReturn(errorResult);

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(100L, 0, request);

            assertEquals(500, response.getBody().getStatus());
        }

        @Test
        @DisplayName("异常测试 - 无效的观看状态")
        public void testSetWatchStatus_InvalidStatus() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), eq(99)))
                    .thenReturn(ResultView.build(400, "无效状态"));

            ResponseEntity<ResultView> response = watchStatusController.setWatchStatus(100L, 99, request);

            assertEquals(HttpStatus.OK, response.getStatusCode());
        }
    }

    @Nested
    @DisplayName("getWishList - 获取想看列表")
    class GetWishList {

        @Test
        @DisplayName("正常获取想看列表")
        public void testGetWishList_Success() {
            setLoginUser();
            when(watchStatusService.getWishList(loggedInUser.getUserId()))
                    .thenReturn(ResultView.ok(new ArrayList<>()));

            ResponseEntity<ResultView> response = watchStatusController.getWishList(request);

            assertAll("验证想看列表响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(200, response.getBody().getStatus());
            });
            verify(watchStatusService).getWishList(loggedInUser.getUserId());
        }

        @Test
        @DisplayName("未登录获取想看列表")
        public void testGetWishList_NotLoggedIn() {
            ResponseEntity<ResultView> response = watchStatusController.getWishList(request);

            assertAll("验证未登录响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(401, response.getBody().getStatus());
            });
            verify(watchStatusService, never()).getWishList(anyLong());
        }

        @Test
        @DisplayName("想看列表为空")
        public void testGetWishList_Empty() {
            setLoginUser();
            when(watchStatusService.getWishList(loggedInUser.getUserId()))
                    .thenReturn(ResultView.ok(new ArrayList<>()));

            ResponseEntity<ResultView> response = watchStatusController.getWishList(request);

            assertEquals(200, response.getBody().getStatus());
        }
    }

    @Nested
    @DisplayName("getWatchingList - 获取在看列表")
    class GetWatchingList {

        @Test
        @DisplayName("正常获取在看列表")
        public void testGetWatchingList_Success() {
            setLoginUser();
            when(watchStatusService.getWatchingList(loggedInUser.getUserId()))
                    .thenReturn(ResultView.ok(new ArrayList<>()));

            ResponseEntity<ResultView> response = watchStatusController.getWatchingList(request);

            assertEquals(200, response.getBody().getStatus());
            verify(watchStatusService).getWatchingList(loggedInUser.getUserId());
        }

        @Test
        @DisplayName("未登录获取在看列表")
        public void testGetWatchingList_NotLoggedIn() {
            ResponseEntity<ResultView> response = watchStatusController.getWatchingList(request);

            assertEquals(401, response.getBody().getStatus());
        }
    }

    @Nested
    @DisplayName("getWatchedList - 获取已看列表")
    class GetWatchedList {

        @Test
        @DisplayName("正常获取已看列表")
        public void testGetWatchedList_Success() {
            setLoginUser();
            when(watchStatusService.getWatchedList(loggedInUser.getUserId()))
                    .thenReturn(ResultView.ok(new ArrayList<>()));

            ResponseEntity<ResultView> response = watchStatusController.getWatchedList(request);

            assertEquals(200, response.getBody().getStatus());
            verify(watchStatusService).getWatchedList(loggedInUser.getUserId());
        }

        @Test
        @DisplayName("未登录获取已看列表")
        public void testGetWatchedList_NotLoggedIn() {
            ResponseEntity<ResultView> response = watchStatusController.getWatchedList(request);

            assertEquals(401, response.getBody().getStatus());
        }
    }

    @Nested
    @DisplayName("removeWatchStatus - 移除观看状态")
    class RemoveWatchStatus {

        @Test
        @DisplayName("正常移除观看状态")
        public void testRemoveWatchStatus_Success() {
            setLoginUser();
            Long movieId = 100L;
            when(watchStatusService.removeWatchStatus(loggedInUser.getUserId(), movieId))
                    .thenReturn(ResultView.ok("已移除"));

            ResponseEntity<ResultView> response = watchStatusController.removeWatchStatus(movieId, request);

            assertAll("验证移除响应", () -> {
                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(200, response.getBody().getStatus());
            });
            verify(watchStatusService).removeWatchStatus(loggedInUser.getUserId(), movieId);
        }

        @Test
        @DisplayName("未登录移除观看状态")
        public void testRemoveWatchStatus_NotLoggedIn() {
            ResponseEntity<ResultView> response = watchStatusController.removeWatchStatus(100L, request);

            assertEquals(401, response.getBody().getStatus());
            verify(watchStatusService, never()).removeWatchStatus(anyLong(), anyLong());
        }

        @Test
        @DisplayName("移除不存在的记录")
        public void testRemoveWatchStatus_NotFound() {
            setLoginUser();
            when(watchStatusService.removeWatchStatus(anyLong(), anyLong()))
                    .thenReturn(ResultView.build(404, "记录不存在"));

            ResponseEntity<ResultView> response = watchStatusController.removeWatchStatus(999L, request);

            assertEquals(404, response.getBody().getStatus());
        }
    }

    @Nested
    @DisplayName("getWatchStatus - 获取电影观看状态")
    class GetWatchStatus {

        @Test
        @DisplayName("正常获取观看状态 - 想看")
        public void testGetWatchStatus_Wish() {
            setLoginUser();
            Long movieId = 100L;
            when(watchStatusService.getWatchStatus(loggedInUser.getUserId(), movieId))
                    .thenReturn(ResultView.ok(0));

            ResponseEntity<ResultView> response = watchStatusController.getWatchStatus(movieId, request);

            assertAll("验证想看状态", () -> {
                assertEquals(200, response.getBody().getStatus());
                assertEquals(0, response.getBody().getData());
            });
        }

        @Test
        @DisplayName("正常获取观看状态 - 在看")
        public void testGetWatchStatus_Watching() {
            setLoginUser();
            when(watchStatusService.getWatchStatus(anyLong(), anyLong()))
                    .thenReturn(ResultView.ok(1));

            ResponseEntity<ResultView> response = watchStatusController.getWatchStatus(100L, request);

            assertEquals(1, response.getBody().getData());
        }

        @Test
        @DisplayName("正常获取观看状态 - 已看")
        public void testGetWatchStatus_Watched() {
            setLoginUser();
            when(watchStatusService.getWatchStatus(anyLong(), anyLong()))
                    .thenReturn(ResultView.ok(2));

            ResponseEntity<ResultView> response = watchStatusController.getWatchStatus(100L, request);

            assertEquals(2, response.getBody().getData());
        }

        @Test
        @DisplayName("未获取观看状态 - 未添加")
        public void testGetWatchStatus_NotAdded() {
            setLoginUser();
            when(watchStatusService.getWatchStatus(anyLong(), anyLong()))
                    .thenReturn(ResultView.ok(null));

            ResponseEntity<ResultView> response = watchStatusController.getWatchStatus(100L, request);

            assertAll("验证未添加状态", () -> {
                assertEquals(200, response.getBody().getStatus());
                assertNull(response.getBody().getData());
            });
        }

        @Test
        @DisplayName("未登录获取观看状态")
        public void testGetWatchStatus_NotLoggedIn() {
            ResponseEntity<ResultView> response = watchStatusController.getWatchStatus(100L, request);

            assertEquals(401, response.getBody().getStatus());
        }
    }
}
