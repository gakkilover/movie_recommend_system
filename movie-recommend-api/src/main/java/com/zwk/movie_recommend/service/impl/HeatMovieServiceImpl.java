package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.movie_recommend.dao.HeatMovieDao;
import com.zwk.movie_recommend.entity.HeatMovieEntity;
import com.zwk.movie_recommend.entity.MovieEntity;
import com.zwk.movie_recommend.service.HeatMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-23 21:56
 * @description:
 **/
@Service("heatMovieService")
public class HeatMovieServiceImpl extends ServiceImpl<HeatMovieDao, HeatMovieEntity> implements HeatMovieService {

    @Autowired
    private HeatMovieDao heatMovieDao;

    @Override
    public void insertOrUpdateHeat(HeatMovieEntity heatMovieEntity) {
        HeatMovieEntity orgionHeat = heatMovieDao.getOne(heatMovieEntity.getMovieId());
        if(orgionHeat != null){
            orgionHeat.setHeatNumber(heatMovieEntity.getHeatNumber());
            orgionHeat.setUpdateDate(new Date());
            heatMovieDao.updateById(orgionHeat);
        } else{
            heatMovieEntity.setHeatId(heatMovieDao.getMaxId());
            heatMovieEntity.setUpdateDate(new Date());
            heatMovieDao.insert(heatMovieEntity);
        }
    }

    @Override
    public List<MovieEntity> getTop10List() {
//        StringBuffer sql = new StringBuffer();
//        sql.append(" order by update_date desc limit 0,10 ");
//        return heatMovieDao.getList(sql.toString());
        return  heatMovieDao.getTop10List();
    }
}
