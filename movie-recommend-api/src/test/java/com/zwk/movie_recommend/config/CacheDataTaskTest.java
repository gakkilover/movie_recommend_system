package com.zwk.movie_recommend.config;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.any;

import com.zwk.movie_recommend.redis.CacheData;
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
 * Unit tests for CacheDataTask
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CacheDataTask.class, LoggerFactory.class})
public class CacheDataTaskTest {

    @Mock
    private CacheData cacheData;

    @InjectMocks
    private CacheDataTask cacheDataTask;

    @Mock
    private Logger logger;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        // Mock LoggerFactory.getLogger to return our mock logger
        PowerMockito.mockStatic(LoggerFactory.class);
        PowerMockito.when(LoggerFactory.getLogger(CacheDataTask.class)).thenReturn(logger);
    }

    /**
     * Test refreshCache method
     */
    @Test
    public void testRefreshCache() {
        // Act
        cacheDataTask.refreshCache();

        // Assert
        verify(logger).info("缓存定时任务执行开始");
        verify(cacheData).refreshCacheAll();
        verify(logger).info("缓存定时任务执行结束");
    }

    /**
     * Test refreshCache method when CacheData throws exception
     */
    @Test
    public void testRefreshCacheException() {
        // Arrange
        doThrow(new RuntimeException("Cache refresh failed")).when(cacheData).refreshCacheAll();

        // Act
        cacheDataTask.refreshCache();

        // Assert
        verify(logger).info("缓存定时任务执行开始");
        verify(cacheData).refreshCacheAll();
        verify(logger).error("缓存定时任务执行异常", any(RuntimeException.class));
        verify(logger).info("缓存定时任务执行结束");
    }
}