package com.zwk.movie_recommend.entity.redisData;

import com.zwk.common.utils.CacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-13 16:03
 * @ Description：
 */
public class MovieCache extends BaseCache{

    private Logger logger = LoggerFactory.getLogger(MovieCache.class);

    private Long movieId;
    private String movieName;
    private Date movieShowyear;
    private String nation;
    private String movieDirector;
    private String movieLeadactors;
    private String screen;
    private String moviePic;
    private Double movieAverating;
    private Long movieRateNum;
    private String movieDescription;
    private String movieTags;
    private String backpost;
    private double recommendPriority;

    public MovieCache(){
        setCacheName(TABLE.MOVIE.name());
    }

    //查询所有电影数据
    private final String sql = "select movie_id, movie_name, movie_showyear, nation, movie_director," +
            " movie_leadactors, screen, movie_pic, movie_averating, movie_rateNum, movie_description," +
            " movie_tags, backpost from movie where 1=1 ";

    @Override
    public void initCache() {
        logger.info("开始同步电影movie数据到缓存");
        List<Map<String, Object>> list = getJdbcTemplate().queryForList(sql);
        Map<String,Object> map = new HashMap<>();
        for (Map<String, Object> specMap : list) {
            MovieCache movieCache = mapToBean(specMap);
            map.put(PRE_ID + movieCache.getMovieId(), movieCache);
            map.put(PRE_ID + movieCache.getMovieName(), movieCache);
        }
        setCache(map);
        logger.info("结束同步电影movie数据到缓存");
    }

    public MovieCache getByMovieId(Long movieId){
        MovieCache movieCache = (MovieCache) getBaseCacheFroRedis(PRE_ID + movieId, MovieCache.class);
        movieCache = (movieCache == null ? qryMovie(movieId) : movieCache);
        return movieCache == null ? new MovieCache(): movieCache;
    }

    public MovieCache getByMovieName(String movieName){
        MovieCache movieCache = (MovieCache) getBaseCacheFroRedis(PRE_ID + movieName, MovieCache.class);
        return movieCache;
    }

    private MovieCache qryMovie(Long movieId){
        StringBuffer sql = new StringBuffer();
        sql.append("  ");
        if(movieId != null){
            sql.append(" AND MOVIE_ID= " + movieId);
        }
        Map<String, Object> specMap = null;
        try {
            specMap = getJdbcTemplate().queryForMap(sql.toString());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
        MovieCache movieCache = mapToBean(specMap);
        getCache().put(PRE_ID + movieCache.getMovieId(), movieCache);
        return movieCache;
    }

    private MovieCache mapToBean(Map<String,Object> movieMap){
        MovieCache movieCache = new MovieCache();
        movieCache.setMovieId(Long.valueOf(String.valueOf(movieMap.get("MOVIE_ID"))));
        movieCache.setMovieName(String.valueOf(movieMap.get("MOVIE_NAME")));
        movieCache.setMovieAverating(Double.parseDouble(String.valueOf(movieMap.get("MOVIE_AVERATING"))));
        movieCache.setBackpost(String.valueOf(movieMap.get("BACKPOST")));
        movieCache.setMovieDirector(String.valueOf(movieMap.get("MOVIE_DIRECTOR")));
        movieCache.setMovieLeadactors(String.valueOf(movieMap.get("MOVIE_LEADACTORS")));
        movieCache.setMovieRateNum(Long.valueOf(String.valueOf(movieMap.get("MOVIE_RATENUM"))));
        movieCache.setMovieShowyear(new Date(String.valueOf(movieMap.get("MOVIE_SHOWYEAR"))));
        movieCache.setMovieTags(String.valueOf(movieMap.get("MOVIE_TAGS")));
        movieCache.setNation(String.valueOf(movieMap.get("NATION")));
        movieCache.setScreen(String.valueOf(movieMap.get("SCREEN")));
        movieCache.setMoviePic(String.valueOf(movieMap.get("MOVIE_PIC")));
        movieCache.setMovieDescription(String.valueOf(movieMap.get("MOVIE_DESCRIPTION")));
        return movieCache;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public Date getMovieShowyear() {
        return movieShowyear;
    }

    public void setMovieShowyear(Date movieShowyear) {
        this.movieShowyear = movieShowyear;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String getMovieLeadactors() {
        return movieLeadactors;
    }

    public void setMovieLeadactors(String movieLeadactors) {
        this.movieLeadactors = movieLeadactors;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getMoviePic() {
        return moviePic;
    }

    public void setMoviePic(String moviePic) {
        this.moviePic = moviePic;
    }

    public Double getMovieAverating() {
        return movieAverating;
    }

    public void setMovieAverating(Double movieAverating) {
        this.movieAverating = movieAverating;
    }

    public Long getMovieRateNum() {
        return movieRateNum;
    }

    public void setMovieRateNum(Long movieRateNum) {
        this.movieRateNum = movieRateNum;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public String getMovieTags() {
        return movieTags;
    }

    public void setMovieTags(String movieTags) {
        this.movieTags = movieTags;
    }

    public String getBackpost() {
        return backpost;
    }

    public void setBackpost(String backpost) {
        this.backpost = backpost;
    }

    public double getRecommendPriority() {
        return recommendPriority;
    }

    public void setRecommendPriority(double recommendPriority) {
        this.recommendPriority = recommendPriority;
    }
}
