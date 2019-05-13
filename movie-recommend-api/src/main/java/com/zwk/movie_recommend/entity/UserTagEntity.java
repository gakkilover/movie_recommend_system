package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-17 14:31
 * @ Description：
 */
@TableName("user_tag")
public class UserTagEntity implements Serializable {

    private static final long serialVersionUID = 7947813537925783404L;

    @TableId
    private Long userTagId;

    private Long userId;

    private String tagId;

    private Date recordDate;

    public Long getUserTagId() {
        return userTagId;
    }

    public void setUserTagId(Long userTagId) {
        this.userTagId = userTagId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTagIds() {
        return tagId;
    }

    public void setTagIds(String tagIds) {
        this.tagId = tagIds;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
