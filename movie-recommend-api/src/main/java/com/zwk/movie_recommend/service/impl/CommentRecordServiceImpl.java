package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.CommentRecordDao;
import com.zwk.movie_recommend.entity.CommentRecordEntity;
import com.zwk.movie_recommend.service.CommentRecordService;
import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 17:45
 * @ Description：
 */
@Service("commentRecordService")
public class CommentRecordServiceImpl extends ServiceImpl<CommentRecordDao, CommentRecordEntity> implements CommentRecordService {

    @Autowired
    private CommentRecordDao commentRecordDao;

    @Override
    public List<CommentRecordEntity> getReviewListByUserId(Long userId) {

        StringBuffer sql =new StringBuffer();
        sql.append(" and user_id=" + userId);
        List<CommentRecordEntity> commentRecordEntities = commentRecordDao.getList(sql.toString());
        return commentRecordEntities;
    }

    @Override
    public void setStar(CommentRecordEntity commentRecordEntity) {
        commentRecordEntity.setCommentId(commentRecordDao.getMaxId());
        commentRecordDao.insert(commentRecordEntity);
    }

    @Override
    public ResultView getReview(Long userid, Long movieid) {
        StringBuffer sql = new StringBuffer();
        sql.append(" and user_id =" + userid);
        sql.append(" and movie_id =" + movieid);
        List<CommentRecordEntity> commentRecordEntityList = commentRecordDao.getList(sql.toString());
        CommentRecordEntity commentRecordEntity =null;
        if(commentRecordEntityList != null && commentRecordEntityList.size()>=1 ) {
            commentRecordEntity = commentRecordEntityList.get(0);
        }
        return ResultView.ok(commentRecordEntity);
    }

    @Override
    public List<CommentRecordEntity> getTop10Comment() {
        StringBuffer sql = new StringBuffer();
        sql.append(" order by comment_date desc limit 0,10 ");
        return commentRecordDao.getList(sql.toString());
    }
}
