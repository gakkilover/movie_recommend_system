package com.zwk.movie_recommend.controller;
import com.zwk.common.constant.Final;
import com.zwk.common.utils.ListUtils;
import com.zwk.movie_recommend.calculate.CalSemblance;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.common.JsonUtils;
import com.zwk.movie_recommend.entity.*;
import com.zwk.movie_recommend.redis.RedisService;
import com.zwk.movie_recommend.service.*;
import com.zwk.movie_recommend.utils.HttpContextUtils;
import com.zwk.movie_recommend.utils.IpUtils;
import com.zwk.movie_recommend.utils.LocalCacheUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Created by ZXL on 2018/3/1.
 */

@Controller
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RecommendRelationService alsService;

    @Autowired
    private CommentRecordService commentRecordService;

    @Autowired
    private CollectRecordService collectRecordService;

    @Autowired
    private RecommendRecordService recommendRecordService;

    @Autowired
    private RecommendRelationService recommendRelationService;

    @Autowired
    private MovieSimilarService movieSimilarService;

    @Autowired
    private MovieTagRelationService movieTagRelationService;

    @Autowired
    private CollectDetailService collectDetailService;

    @Autowired
    private SearchRecordService searchRecordService;

    @Autowired
    private HeatMovieService heatMovieService;

    @Autowired
    private RedisService redisService;

    //主页Home
    @RequestMapping("/homepage")
    public String showHomepage( HttpServletRequest request){
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");

        if(user!=null)
        {
            RecommendRecordEntity recommendRecord = recommendRecordService.getRectabByUserId(user.getUserId());
            List<MovieEntity> recommendMovieList = new ArrayList<>();
            //如果有推荐记录，最后一次推荐结果+热度电影
            if(recommendRecord != null){
                List<MovieEntity> recommendHis = recommendRelationService.selectAlsMoviesByUserId(user.getUserId());

                List<MovieEntity> heatList = heatMovieService.getTop10List();
                recommendMovieList = CalSemblance.getInstance().mergeRecommendList(recommendHis, heatList);
            } else{ //如果没有推荐记录
                List<UserTagEntity> userTagList = userTagService.getByUserId(user.getUserId());
                //如果注册时选择了电影标签，按各电影标签热度排名第一的电影
                if(userTagList != null && userTagList.size()>0){
                    recommendMovieList = movieService.getMovieByTagUser(user.getUserId());
                }else{ //如果注册时未选择电影标签，热度最高电影top10
                    recommendMovieList = heatMovieService.getTop10List();
                }
            }
//            List<MovieEntity> movies = new ArrayList<>();
//
//            // 从ALS表中查询推荐强度8以上的电影
//            List<MovieEntity> alsMovies = alsService.selectAlsMoviesByUserId(user.getUserId());
//            for (MovieEntity alsMovie : alsMovies) {
//                movies.add(alsMovie);
//            }
//
//            RecommendRecordEntity recommendRecordEntity = recommendRecordService.getRectabByUserId(user.getUserId());
//            if (recommendRecordEntity !=null && null != recommendRecordEntity.getMovieIds()) {
//                String movieids = recommendRecordEntity.getMovieIds();
//                String[] strmovieids = movieids.split(",");
//                int i = 0;
//                for (String strmovieid: strmovieids) {
//                    if(i==5)
//                        break;
//                    if(strmovieid != null &&StringUtils.isNotBlank(strmovieid)){
//                        Long movieid = Long.parseLong(strmovieid);
//                        MovieEntity movieEntity = movieService.getMovieByMovieid(movieid);
//                        if(movieEntity !=null)
//                            movies.add(movieEntity);
//                    }
//                    i++;
//                }
//            }
//            //不足五部从默认电影中凑齐五部
//            if(movies.size()<5)
//            {
//                ResultView TopDefaultMovie = movieService.selectTopDefaultMovie(5-movies.size());
//                List<MovieEntity> temp = (List<MovieEntity>)TopDefaultMovie.getData();
//                movies.addAll(temp);
//
//            }
            //将电影信息放在map中转Json再进入session给前端 map中存放movieid
            request.getSession().setAttribute("TopDefaultMovie",recommendMovieList);
            Map moviemap = new HashMap();
            for(int i = 0 ; i < recommendMovieList.size() ; i++) {
                moviemap.put(recommendMovieList.get(i).getMovieId().toString(), i);
            }
            request.getSession().setAttribute("TopDefaultMovieMap",JsonUtils.objectToJson(moviemap));
        }
        else //非用户
        {
            String userIp = IpUtils.getIpAddress(request); //获取用户的请求IP地址
            //新建游客账户，到时候会删除
            UserEntity visitUser = userService.getUserByIp(userIp);
            List<MovieEntity> recommendMovieList = new ArrayList<>();
            //如果有游客记录
            if(visitUser != null){
                List<MovieEntity>  searchList = searchRecordService.getSearchRecord(visitUser.getUserId());
                //如果有搜索记录，搜索电影标签+热度电影
                if(searchList != null && searchList.size()>0){
                    List<MovieEntity> tagList = movieService.getMovieByHeat();
                    //由于每个电影可能是多个标签最高评分，所以需要去重（先将list集合的内容放到另一个集合set中，以电影主键id为维度去重，最后将结果转为list）
                    tagList = tagList.stream().collect(
                            collectingAndThen(
                                    toCollection(() -> new TreeSet<>(comparingLong(MovieEntity::getMovieId))), ArrayList::new)
                    );
                    List<MovieEntity> heatList = heatMovieService.getTop10List();
                    recommendMovieList = CalSemblance.getInstance().mergeRecommendList(tagList,heatList);
                }else{//如果没有搜索记录，热度最高电影
                    recommendMovieList = heatMovieService.getTop10List();
                }
            }else {
                userService.generateUser(userIp);
                recommendMovieList = heatMovieService.getTop10List();
            }
            request.getSession().setAttribute("TopDefaultMovie",recommendMovieList);
            Map moviemap = new HashMap();
            for(int i = 0 ; i < recommendMovieList.size() ; i++) {
                moviemap.put(recommendMovieList.get(i).getMovieId().toString(), i);
            }
            request.getSession().setAttribute("TopDefaultMovieMap",JsonUtils.objectToJson(moviemap));
        }
        return "Home";
    }
    //选电影界面
    @RequestMapping("/index")
    public String showIndex( HttpServletRequest request){
        //获取所有分类标签
        ResultView resultViewAllCategory = tagService.getCategoryList();
        List<TagEntity> list1 = (List<TagEntity>) resultViewAllCategory.getData();
        //获取所有电影数据(缺少筛选，默认一次加载20个)
        ResultView resultViewAllMoive = movieService.getBycategory(0L, 0, "movie_rate_num");
        List<MovieEntity> list2 = (List<MovieEntity>) resultViewAllMoive.getData();
        //设置SEESION
        request.getSession().setAttribute("category",list1);
        request.getSession().setAttribute("movie",list2);
        return "index";
    }

    //电影详情传值
    @RequestMapping("/Customer/Description")
    @ResponseBody
    public String GoMoiveDescription(HttpServletRequest request) {
        request.getSession().removeAttribute("booluserunlikedmovie");
        //获取用户点击的movieid
        Long  movieid=Long.parseLong(request.getParameter("id"));
//        ResultView resultView = movieService.getByMovieid(movieid);
        MovieEntity movieDes = (MovieEntity) redisService.get(Final.REDIS_MOVIE + movieid);
        ResultView resultView = ResultView.ok(movieDes);
        //用户所点击的电影详情信息movie
        MovieEntity movieEntity = (MovieEntity) resultView.getData();
        UserEntity user=(UserEntity) request.getSession().getAttribute("user");
        String searchInfo = (String)request.getSession().getAttribute("searchInfo");
        //用户点击此电影之后根据此电影标签 更新推荐结果
        List<MovieEntity> updateList = new ArrayList<>();
        RecommendRecordEntity recommendRecord = new RecommendRecordEntity();
        String movieIds = "";
        List<MovieTagRelationEntity> movieTagList = movieTagRelationService.getByMovieId(movieid);
        for (MovieTagRelationEntity movieTag:movieTagList) {
            MovieEntity movie = new MovieEntity();
            movie = movieTagRelationService.getMovieByTagId(movieTag.getTagId()).get(0);
            movieIds  = movieIds + movie.getMovieId()+ ",";
            updateList.add(movie);
        }
        //String searchInfo = (String)HttpContextUtils.getValueInSession("searchInfo");
        //判断用户是否登录以及对这部电影的喜爱
        if(user!=null)
        {
            searchRecordService.insertSearchRecord(movieid, user.getUserId(), searchInfo);
            ResultView resultView2 = commentRecordService.getReview(user.getUserId(), movieid);
            CommentRecordEntity commentRecordEntity = (CommentRecordEntity) resultView2.getData();
            request.getSession().setAttribute("userstar", commentRecordEntity);
            request.getSession().setAttribute("descroption", commentRecordEntity);
            //判断登录用户是否喜欢该电影
            boolean flag = false;
            List<CollectDetailEntity> collectList = collectDetailService.getByUser(user.getUserId(), movieid);
            if(collectList != null && collectList.size()>0){
                flag = true;
            }
            request.getSession().setAttribute("flag", flag);
            int booluserlikedmovie=movieService.judgeIsLike(user.getUserId(),request.getParameter("id"));
            request.getSession().setAttribute("booluserunlikedmovie", booluserlikedmovie);
        }
        else
        {
            String userIp = IpUtils.getIpAddress(request); //获取用户的请求IP地址
            user = userService.getUserByIp(userIp);
            if(user != null){
                searchRecordService.insertSearchRecord(movieid, user.getUserId(),searchInfo);
            }
            CommentRecordEntity commentRecordEntity = null;
            request.getSession().setAttribute("userstar", commentRecordEntity);
        }
        recommendRecord.setMovieIds(movieIds);
        recommendRecord.setUserId(user.getUserId());
        recommendRecordService.insertRectab(recommendRecord);
        //将对用户推荐的电影更新到数据库，以便后续显示给用户
        recommendRelationService.insertRelation(user.getUserId(), movieIds);
        //设置session
        request.getSession().setAttribute("moviedescription", movieEntity);

        return "success";
    }

    //电影详情界面
    @RequestMapping("/MovieDescription")
    public String showMoiveDescription(HttpServletRequest request){
        return "MovieDescription";
    }

    //选电影界面加载更多按钮(通过类型标签，时序标签以及现有页面呈现的电影数目三个参数查询)
    @RequestMapping(value = "/loadingmore", method = RequestMethod.POST)
    @ResponseBody
    public ResultView showloadmore(HttpServletRequest request){
        Long categoryid= Long.parseLong(request.getParameter("type"));
        int limit=Integer.parseInt(request.getParameter("molimit"));
        String sort = request.getParameter("sort");
        ResultView resultViewAllMoive = movieService.getBycategory(categoryid, limit, sort);
        List<MovieEntity> list = (List<MovieEntity>) resultViewAllMoive.getData();
        ResultView resultView =ResultView.ok(list);
        return resultView;
    }

    //选择排序电影（类型和时序）
    @RequestMapping(value = "/typesortmovie", method = RequestMethod.POST)
    @ResponseBody
    public ResultView showtypesortmovie(HttpServletRequest request){
        Long type= Long.parseLong(request.getParameter("type"));
        int limit=Integer.parseInt(request.getParameter("molimit"));
        String sort=request.getParameter("sort");
        ResultView resultViewAllMoive = movieService.getBycategory(type, limit, sort) ;
        List<MovieEntity> list = (List<MovieEntity>) resultViewAllMoive.getData();
        ResultView resultView =ResultView.ok(list);
        return resultView;
    }

    //电影评星
    @RequestMapping(value = "/getstar", method = RequestMethod.POST)
    @ResponseBody
    public String getstar(HttpServletRequest request) throws ParseException {
        Long userid = Long.parseLong(request.getParameter("userId"));
        Long movieid = Long.parseLong(request.getParameter("movieId"));
        Double star = Double.parseDouble(request.getParameter("star"));
        String commentDecription = request.getParameter("commentDescription");
        String strDate = request.getParameter("time");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = format.parse(strDate);
        CommentRecordEntity commentRecordEntity = new CommentRecordEntity();
        commentRecordEntity.setUserId(userid);
        commentRecordEntity.setMovieId(movieid);
        commentRecordEntity.setCommentStar(star);
        commentRecordEntity.setCommentDate(time);
        commentRecordEntity.setCommentDescription(commentDecription);
        //写入数据库
        commentRecordService.setStar(commentRecordEntity);
        movieService.updateMovieRate(movieid, star);
        List<MovieEntity> recommendMovieList = new ArrayList<>();
        if(star>=3.5) {

            // 查询本地相似表

            List<CommentRecordEntity> commentRecordEntityList = commentRecordService.getReviewListByUserId(userid);
            List<MovieSimilarEntity> similartabList = movieSimilarService.getSimilarTabByMovieId(movieid);
            //List<MovieSimilarEntity> similartabList = (List<MovieSimilarEntity>) redisService.get(Final.REDIS_MOVIE_SIMILAR);
            CalSemblance calSemblance = CalSemblance.getInstance();
            List<MovieEntity> updateList = calSemblance.calSemblance(commentRecordEntityList, similartabList, movieid);
            MovieEntity movieEntity =null;
            RecommendRecordEntity historyRecommend = recommendRecordService.getRectabByUserId(userid);
            List<MovieEntity> historyList = new ArrayList<>();
            String moviedsHis = "";
            if(historyRecommend != null){
                moviedsHis = historyRecommend.getMovieIds();
            }
            String[] moviedsHisList = moviedsHis.split(",");
            for (String str: moviedsHisList) {
                if(str != null && StringUtils.isNotBlank(str)) {
//                    MovieEntity hisMovie = movieService.getMovieByMovieid(Long.parseLong(str));
                    MovieEntity hisMovie = (MovieEntity) redisService.get(Final.REDIS_MOVIE + str);
                    if (hisMovie != null) {
                        historyList.add(hisMovie);
                    }
                }
            }
            List<MovieEntity> resultList = CalSemblance.getInstance().mergeRecommendList(updateList, historyList);
            recommendMovieList = resultList;
            String movieds = CalSemblance.getInstance().getMovieIds(resultList);
            //String movieds = movieService.select5SimilarMovies(movieid);

            // 判断数据库是否有该userid
            RecommendRecordEntity recommendRecordEntity = recommendRecordService.getRectabByUserId(userid);
            RecommendRecordEntity rec = new RecommendRecordEntity();
            rec.setUserId(userid);
            rec.setMovieIds(movieds);
            // 没有则插入数据库
            if (null == recommendRecordEntity) {
                recommendRecordService.insertRectab(rec);
                recommendRelationService.insertRelation(userid, movieds);
            } else {
                recommendRecordService.updateRectab(rec);
                recommendRelationService.updateRelation(userid, movieds);
            }

        }
        commentRecordEntity =null;
        ResultView resultView = commentRecordService.getReview(userid, movieid);
        commentRecordEntity = (CommentRecordEntity) resultView.getData();
        //立即读取影评显示于前端
        boolean flag = false;
        List<CollectDetailEntity> collectList = collectDetailService.getByUser(userid, movieid);
        if(collectList != null && collectList.size()>0){
            flag =true;
        }
        request.getSession().setAttribute("userstar", commentRecordEntity);
        request.getSession().setAttribute("flag", flag);
        //用户评分之后就讲最新推荐结果展现給用户
        if(star>3.5) {
            request.getSession().setAttribute("TopDefaultMovie", recommendMovieList);
        }
        return "success";
    }

    //电影详情界面点击相似电影
    @RequestMapping(value = "/getSimiMovies", method = RequestMethod.POST)
    @ResponseBody
    public ResultView getSimiMovies(HttpServletRequest request) throws ParseException {
        Long id = Long.parseLong(request.getParameter("id"));
        ResultView resultView = movieService.select5SimilarMoviesById(id);
        List<MovieEntity> simiMovies = (List<MovieEntity>) resultView.getData();
        resultView =ResultView.ok(simiMovies);
        return resultView;
    }


    //电影详情界面用户喜欢电影（,id. 格式写入数据库，不存在则插入，存在则更新）
    @RequestMapping(value = "/likedmovie", method = RequestMethod.POST)
    @ResponseBody
    public String likedmovie(HttpServletRequest request) throws ParseException {
        String movieId = request.getParameter("movieId");
        Long userid = Long.parseLong(request.getParameter("userId"));
        int boollike = Integer.parseInt(request.getParameter("boollike"));

        CollectRecordEntity collectRecord = new CollectRecordEntity();
        collectRecord.setUserId(userid);
        collectRecord.setMovieIds("," + movieId);
        collectRecordService.insertCollectRecord(collectRecord);

        CollectDetailEntity collectDetail = new CollectDetailEntity();
        collectDetail.setMovieId(Long.parseLong(movieId));
        collectDetail.setUserId(userid);
        collectDetailService.insertCollectDetail(collectDetail);

        return "success";
    }


}
