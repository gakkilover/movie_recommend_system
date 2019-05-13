package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.RecommendRelationDao;
import com.zwk.movie_recommend.dao.MovieDao;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.entity.RecommendRelationEntity;
import com.zwk.movie_recommend.service.RecommendRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-09 11:10
 * @ Description：
 */
@Service("alstabService")
public class RecommendRelationServiceImpl extends ServiceImpl<RecommendRelationDao, RecommendRelationEntity> implements RecommendRelationService {

    @Autowired
    private MovieDao movieDao;

    @Autowired
    private RecommendRelationDao recommendRelationDao;

    @Override
    public List<MovieEntity> selectAlsMoviesByUserId(Long userid) {
        StringBuffer sql = new StringBuffer();
        sql.append(" and rr.user_id= "+userid);
        List<MovieEntity> alsMovieIds = recommendRelationDao.getList(sql.toString());
//        List<MovieEntity> alsMovieList = new ArrayList<MovieEntity>();
//        MovieEntity movieEntity = null;
//        for (RecommendRelationEntity recommendRelationEntity : alsMovieIds) {
//            movieEntity = movieDao.getByMovieId(recommendRelationEntity.getMovieId());
//            if (movieEntity != null) {
//                alsMovieList.add(movieEntity);
//            }
//        }

        return alsMovieIds;
    }

    @Override
    public void insertRelation(Long userId, String movieds) {
        String[] movieIds = movieds.split(",");
        insertByStr(movieds,userId);
    }

    @Override
    public void updateRelation(Long userId, String movieds) {
        recommendRelationDao.deleteRelation(userId);
        insertByStr(movieds,userId);
    }

    void insertByStr(String movieids, Long userId){
        String[] movieIds = movieids.split(",");
        for (String movidId :movieIds) {
            if(movidId != null && StringUtils.isNotBlank(movidId)){
                RecommendRelationEntity recommendRelation = new RecommendRelationEntity();
                recommendRelation.setRecommendRelationId(recommendRelationDao.getMaxId());
                recommendRelation.setUserId(userId);
                recommendRelation.setRecommendScore(5d);
                recommendRelation.setMovieId(Long.parseLong(movidId));
                recommendRelationDao.insert(recommendRelation);
            }
        }
    }
}
