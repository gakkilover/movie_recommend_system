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
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private ResultView errorResult;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();

        loggedInUser = new UserEntity();
        loggedInUser.setUserId(1L);
        loggedInUser.setUserName("testuser");

        successResult = ResultView.ok("操作成功");
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

            ResultView result = watchStatusController.setWatchStatus(movieId, watchStatus, request);

            assertNotNull(result);
            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(watchStatusService).setWatchStatus(loggedInUser.getUserId(), movieId, watchStatus);
        }

        @Test
        @DisplayName("正常设置在看状态")
        public void testSetWatchStatus_Watching_Success() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), eq(1)))
                    .thenReturn(successResult);

            ResultView result = watchStatusController.setWatchStatus(100L, 1, request);

            assertEquals(Integer.valueOf(200), result.getStatus());
        }

        @Test
        @DisplayName("正常设置已看状态")
        public void testSetWatchStatus_Watched_Success() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), eq(2)))
                    .thenReturn(successResult);

            ResultView result = watchStatusController.setWatchStatus(100L, 2, request);

            assertEquals(Integer.valueOf(200), result.getStatus());
        }

        @Test
        @DisplayName("正常取消观看状态")
        public void testSetWatchStatus_Cancel_Success() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), eq(3)))
                    .thenReturn(ResultView.ok("已取消"));

            ResultView result = watchStatusController.setWatchStatus(100L, 3, request);

            assertEquals(Integer.valueOf(200), result.getStatus());
        }

        @Test
        @DisplayName("未登录时设置观看状态")
        public void testSetWatchStatus_NotLoggedIn() {
            Long movieId = 100L;
            Integer watchStatus = 0;

            ResultView result = watchStatusController.setWatchStatus(movieId, watchStatus, request);

            assertEquals(Integer.valueOf(401), result.getStatus());
            assertEquals("请先登录", result.getMsg());
            verify(watchStatusService, never()).setWatchStatus(anyLong(), anyLong(), anyInt());
        }

        @Test
        @DisplayName("异常测试 - 服务返回错误")
        public void testSetWatchStatus_ServiceError() {
            setLoginUser();
            when(watchStatusService.setWatchStatus(anyLong(), anyLong(), anyInt()))
                    .thenReturn(errorResult);

            ResultView result = watchStatusController.setWatchStatus(100L, 0, request);

            assertEquals(Integer.valueOf(500), result.getStatus());
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

            ResultView result = watchStatusController.getWishList(request);

            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(watchStatusService).getWishList(loggedInUser.getUserId());
        }

        @Test
        @DisplayName("未登录获取想看列表")
        public void testGetWishList_NotLoggedIn() {
            ResultView result = watchStatusController.getWishList(request);

            assertEquals(Integer.valueOf(401), result.getStatus());
            verify(watchStatusService, never()).getWishList(anyLong());
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

            ResultView result = watchStatusController.getWatchingList(request);

            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(watchStatusService).getWatchingList(loggedInUser.getUserId());
        }

        @Test
        @DisplayName("未登录获取在看列表")
        public void testGetWatchingList_NotLoggedIn() {
            ResultView result = watchStatusController.getWatchingList(request);

            assertEquals(Integer.valueOf(401), result.getStatus());
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

            ResultView result = watchStatusController.getWatchedList(request);

            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(watchStatusService).getWatchedList(loggedInUser.getUserId());
        }

        @Test
        @DisplayName("未登录获取已看列表")
        public void testGetWatchedList_NotLoggedIn() {
            ResultView result = watchStatusController.getWatchedList(request);

            assertEquals(Integer.valueOf(401), result.getStatus());
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

            ResultView result = watchStatusController.removeWatchStatus(movieId, request);

            assertEquals(Integer.valueOf(200), result.getStatus());
            verify(watchStatusService).removeWatchStatus(loggedInUser.getUserId(), movieId);
        }

        @Test
        @DisplayName("未登录移除观看状态")
        public void testRemoveWatchStatus_NotLoggedIn() {
            ResultView result = watchStatusController.removeWatchStatus(100L, request);

            assertEquals(Integer.valueOf(401), result.getStatus());
            verify(watchStatusService, never()).removeWatchStatus(anyLong(), anyLong());
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

            ResultView result = watchStatusController.getWatchStatus(movieId, request);

            assertEquals(Integer.valueOf(200), result.getStatus());
            assertEquals(Integer.valueOf(0), result.getData());
        }

        @Test
        @DisplayName("正常获取观看状态 - 在看")
        public void testGetWatchStatus_Watching() {
            setLoginUser();
            when(watchStatusService.getWatchStatus(anyLong(), anyLong()))
                    .thenReturn(ResultView.ok(1));

            ResultView result = watchStatusController.getWatchStatus(100L, request);

            assertEquals(Integer.valueOf(1), result.getData());
        }

        @Test
        @DisplayName("正常获取观看状态 - 已看")
        public void testGetWatchStatus_Watched() {
            setLoginUser();
            when(watchStatusService.getWatchStatus(anyLong(), anyLong()))
                    .thenReturn(ResultView.ok(2));

            ResultView result = watchStatusController.getWatchStatus(100L, request);

            assertEquals(Integer.valueOf(2), result.getData());
        }

        @Test
        @DisplayName("未获取观看状态 - 未添加")
        public void testGetWatchStatus_NotAdded() {
            setLoginUser();
            when(watchStatusService.getWatchStatus(anyLong(), anyLong()))
                    .thenReturn(ResultView.ok(null));

            ResultView result = watchStatusController.getWatchStatus(100L, request);

            assertEquals(Integer.valueOf(200), result.getStatus());
            assertNull(result.getData());
        }

        @Test
        @DisplayName("未登录获取观看状态")
        public void testGetWatchStatus_NotLoggedIn() {
            ResultView result = watchStatusController.getWatchStatus(100L, request);

            assertEquals(Integer.valueOf(401), result.getStatus());
        }
    }
}
