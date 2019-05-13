package com.zwk.movie_recommend.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;


/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-25 17:01
 * @ Description：
 */
@TableName("comment_record")
public class CommentRecordEntity implements Serializable {
    private static final long serialVersionUID = -3848324355307941910L;
    @TableId
    private Long commentId;

    private Long userId;

    private Long movieId;

    private String commentDescription;

    private Double commentStar;

    private Date commentDate;

    private Long commentThumbupNum;

    private String moviePic;

    ///图片地址
    public String getMoviePic() {
        return moviePic;
    }

    public void setMoviePic(String moviePic) {
        this.moviePic = moviePic;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getCommentDescription() {
        return commentDescription;
    }

    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    public Double getCommentStar() {
        return commentStar;
    }

    public void setCommentStar(Double commentStar) {
        this.commentStar = commentStar;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Long getCommentThumbupNum() {
        return commentThumbupNum;
    }

    public void setCommentThumbupNum(Long commentThumbupNum) {
        this.commentThumbupNum = commentThumbupNum;
    }
}
