package com.zwk.common.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-03 15:33
 * @ Description：常量数据
 */
public final class Final {
    /**
     * 空字串
     */
    public static final String EMPTY = "";

    /**
     * 数据库已存在记录
     */
    public final static Integer EXIST_ERROE_CODE=1001;

    /**
     * 空指针
     */
    public final static Integer NULL_ERROE_CODE=1002;

    /**
     * 文件读取异常
     */
    public final static String IO_ERROE_CODE="1003";

    /**
     * 类型转换异常
     */
    public final static String CAST_ERROE_CODE="1004";

    /**
     * JSON异常
     */
    public final static String JSON_ERROE_CODE="1005";

    /**
     * 类别默认状态
     */
    public final static String GENRE_ERROE_CODE="1000";

    /**
     * MD5加密失败
     */
    public final static String MD5_ERROE_CODE="2000";

    /**
     * MD5加密默认密码
     */
    public final static String DEFAULT_PASSWORD="zwk@123456";

    /**
     * 成功
     */
    public final static String SUCCESS="SUCCESS";

    /**
     * 失败
     */
    public final static String FAILURE="FAILED";

    /**
     * 成功状态
     */
    public final static Integer SUCCESS_STATUS = 200;

    /**
     * 失败状态
     */
    public final static Integer FAILURE_STATUS = 400;

    /**
     * 最小相似度
     */
    public static final Double MIN_SIMILAR = 0.6;

    /**
     * 电影评分低于1.0属于低评分
     */
    public static final Double LOW_AVERATING = 1.0;

    /**
     * 电影评分高于4.0属于低评分
     */
    public static final Double HIGH_AVERATING = 4.0;

    /**
     * Md5加密盐值
     */
    public static final String MD5_SALT = "123@abc";

    /**
     * 用户状态 ，1000有效用户  1001游客
     */
    public static final String USER_STATUS_EFF = "1000";

    /**
     * 用户状态 ，1000有效用户  1001游客
     */
    public static final String USER_STATUS_EXP = "1001";

    /**
     * redis缓存电影表前缀
     */
    public static final String REDIS_MOVIE = "MOVIE_";

    /**
     * redis缓存电影标签关系表前缀
     */
    public static final String REDIS_MOVIE_TAG_RELATION = "MOVIE_TAG_RELATION_";

    /**
     * redis缓存电影相似度表前缀
     */
    public static final String REDIS_MOVIE_SIMILAR = "MOVIE_SIMILAR_";

    public static final List<String> EXCLUDE_PATH_PATTERNS = new ArrayList<>();
}
