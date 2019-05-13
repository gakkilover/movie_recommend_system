<%@ page isELIgnored="false"%>
<%--<%@ page import="com.dream.po.MovieEntity" %>--%>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path=request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans" class="ua-mac ua-webkit">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>
        爱看不看电影网
    </title>
    <script src="<%=basePath%>assets/js/jquery.js"></script>
    <script src="<%=basePath%>assets/js/bootstrap.min.js"></script>
    <link rel="SHORTCUT ICON" href="<%=basePath%>assets/img/knowU.ico"/>
    <!-- 电影推荐模块CSS-->
    <link href="<%=basePath%>assets/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/SuggestList.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>assets/css/wholeframe.css" rel="stylesheet" type="text/css">
    <!-- 左右模块位置排序和推荐CSS-->
    <%--<link href="https://img3.doubanio.com/f/movieEntity/8864d3756094f5272d3c93e30ee2e324665855b0/css/movieEntity/base/init.css" rel="stylesheet">--%>
    <link href="<%=basePath%>assets/css/init.css" rel="stylesheet">

    <!-- 电影选择模块CSS（类型/排序/展示）-->
    <%--<link rel="stylesheet" href="https://img3.doubanio.com/f/movieEntity/fc5a7b9631f6e089a6a047e0e701207243e3fbdf/css/movieEntity/project/gaia/__init__.css" />--%>
    <link rel="stylesheet" href="<%=basePath%>assets/css/__init__.css" />
<!-- 电影推荐模块CSS-->
    <link rel="stylesheet" href="https://img3.doubanio.com/misc/mixed_static/554ab01e9256e005.css">
    <!-- 鼠标悬浮在<A>时背景和导航栏同步-->
    <style type="text/css">
       a.dream:hover
       {
            background-color: black;
        }
       .clash-card {
           background: white;
           width: 370px;
           display: inline-block;
           margin-left: -80px;
           margin-top: 0px ;
           border-radius: 19px;
           position: relative;
           text-align: center;
           box-shadow: -1px 15px 30px -12px black;
           z-index: 9999;
       }
       .clash-card__unit-name {
           font-size: 26px;
           color: black;
           font-weight: 900;
           margin-bottom: 5px;
       }
       .clash-card__unit-movieDescription {
           padding: 20px;
           margin-bottom: 10px;
       }
       .clash-card__unit-stats--giant {
           background: #428bca;
       }
       .clash-card__unit-stats--giant .one-third {
           border-right: 1px solid #de7b09;
       }
       .clash-card__unit-stats {
           color: white;
           font-weight: 400;
           border-bottom-left-radius: 14px;
           border-bottom-right-radius: 14px;
       }
       .clash-card__unit-stats .one-third {
           width: 33%;
           float: left;
           padding: 20px 15px;
       }
       .clash-card__unit-stats sup {
           position: absolute;
           bottom: 4px;
           font-size: 45%;
           margin-left: 2px;
       }
       .clash-card__unit-stats .stat {
           position: relative;
           font-size: 17px;
           margin-bottom: 10px;
       }
       .clash-card__unit-stats .stat-value {
           text-transform: uppercase;
           font-weight: 400;
           font-size: 17px;
       }
       .clash-card__unit-stats .no-border {
           border-right: none;
       }

    </style>
</head>

<body>

<!-- 导航栏-->
<nav class="navbar navbar-default" role="navigation" style="background-color: black;margin-bottom: 0%">
    <a class="navbar-brand" href="<%=basePath%>/homepage" style="color: white;margin-bottom: 10px;">想看就看电影网 <img src="<%=basePath%>assets/img/search.png"></a>
    <div class="col-xs-4" style="margin-left: -15px;margin-top: 5px;">
        <input id="inp-query" class="form-control" style="margin-bottom: 8px;margin-top: 8px;border-radius: 5px;" name="search_text"  maxlength="60" placeholder="输入你想看的电影吧·····" value="">
    </div>
    <a class="navbar-brand" href="<%=basePath%>index" style="color: white;margin-top: 5px;">点我找你想看的</a>
    <!-- 判断用户是否登录-->
            <c:if test="${sessionScope.user == null}">
            <a  class="dream" href="javascript:window.location.href='<%=basePath%>customer/register'" id="register" style="float: right;color: white;font-size: 13pt;margin-top: 20px;margin-right: 10px"><span style="color: white;" class="glyphicon glyphicon-user"></span> 注册</a>
            <a  class="dream" href="javascript:window.location.href='<%=basePath%>page/login'" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
            </c:if>
            <c:if test="${sessionScope.user != null}">

                <a class="dream" id="logout" href="javascript:window.location.href='<%=basePath%>customer/logout'" style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-in"></span>  注销</a>
                <a class="dream" onclick='javascript:$.post("<%=basePath%>customer/profile",{"id":"${sessionScope.user.userId}"}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>profile"
            } else {
            }
        })' style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}</a>
            </c:if>
</nav>
<br>


<div id="wrapper" class="col-md-12" style="margin-left: 5%">
    <div id="content">
        <h1>选电影</h1>
        <div class="grid-16-8 clearfix">

            <!-- 左侧电影展示模块-->

            <div  class="article">
                <div class="gaia">
                    <div class="detail-pop"></div>
                    <div class="fliter-wp">
                        <div class="filter">
                            <form action="get" class="gaia_frm" autocomplete="off">
                                <input type="hidden" name="type" value="movieEntity">

                                <!-- 电影类型标签-->

                                <div class="tags">
                                    <div id="tags-list" class="tag-list">
                                        <label  class="activate" style="font-size: 13pt" value="0">全部
                                            <input type="radio" name="tag" value="0">
                                        </label>
                                        <!-- 从数据库到seesion读入，默认第一个选中activate-->

                                        <c:forEach var="item"   items="${sessionScope.category}" varStatus="i">
                                                <label  style="font-size: 13pt" value="${i.count}">${item.tagName}
                                                    <input type="radio" name="tag" value="${i.count}">
                                                </label>
                                        </c:forEach>
                                    </div>
                                </div>



                                <!-- 电影时序等选择radio-->

                                <div class="tool" style="">
                                    <div class="sort">
                                        <label>
                                            <input  type="radio" name="sort" value="movie_rate_num" checked="checked"> 按热度排序
                                        </label>
                                        <label>
                                            <input type="radio" name="sort" value="movie_showyear"> 按时间排序
                                        </label>
                                        <label>
                                            <input type="radio" name="sort" value="movie_averating"> 按评价排序
                                        </label>
                                    </div>


                                </div>
                            </form>
                        </div>
                    </div>

                    <!-- 电影信息卡片展示模块-->

                    <div   class="list-wp">
                        <div id="list" class="list">

                            <!-- 初始化或刷新页面用C:FOREACH加载电影（类似于SC模板）-->

                            <c:if test="${sessionScope.movie != null}">
                                <c:forEach var="item"   items="${sessionScope.movie}">
                                    <a class="item"   target="_blank" name="imgitem" id="${item.movieId}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("id")}, function (data) {
                                    if (data == "success") {
                                        location.href = "<%=basePath%>MovieDescription"
                                    } else {
                                    }
                                    })'>
                                        <div class="cover-wp">
                                            <c:if test="${item.moviePic=='http://image.tmdb.org/t/p/w185'}">
                                              <img src="https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2512283982.jpg" alt="${item.movieName}" data-x="1500" data-y="2200" style="width: 100%"/>
                                            </c:if>
                                            <c:if test="${item.moviePic!='http://image.tmdb.org/t/p/w185'}">
                                                <img src="${item.moviePic}" alt="${item.movieName}" data-x="1500" data-y="2200" style="width: 100%"/>
                                            </c:if>
                                        </div>
                                        <p>${item.movieName}
                                            <strong>${item.movieAverating}</strong>
                                        </p>
                                    </a>
                                </c:forEach>
                            </c:if>
                        </div>

                        <!-- 加载更多<a>-->

                        <a class="more" id="loadmore" href="javascript:;">加载更多</a>
                    </div>
                </div>

            </div>

            <!-- 右侧推荐模块（基本未修改/后期可修改）-->

            <div  class="aside">
                    <h1 style="margin-top: -38px;margin-left: -100px;">电影推荐</h1>
                    <div id="doulist" style="width: 350px;margin-left: -100px">
                        <ul>
                            <li>
                                <span>3381推荐</span>
                                <div class="title"><a  target="_blank" href="https://www.douban.com/doulist/30327/">100部爱情电影 经典的回味</a></div>
                            </li>
                            <li>
                                <span>1159推荐</span>
                                <div class="title"><a  target="_blank" href="https://www.douban.com/doulist/31478/">很轻的电影和很重的人生</a></div>
                            </li>
                            <li>
                                <span>3381推荐</span>
                                <div class="title"><a   target="_blank" href="https://www.douban.com/doulist/200218/">得此两百部，足以度余生</a></div>
                            </li>

                        </ul>
                    </div>
                    <!-- 推荐模块默认五个-->
                    <div  style="margin-left: -30px;margin-top: -7px">
                        <div id="myCarousel" class="carousel slide"  data-ride="carousel" style=" margin-top: 35px ;width: 370px;margin-left: -80px">
                            <!-- 轮播（Carousel）指标 -->
                            <ol class="carousel-indicators" style="margin-bottom: -20px">
                                <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                                <li data-target="#myCarousel" data-slide-to="1"></li>
                                <li data-target="#myCarousel" data-slide-to="2"></li>
                                <li data-target="#myCarousel" data-slide-to="3"></li>
                                <li data-target="#myCarousel" data-slide-to="4"></li>
                            </ol>
                            <!-- 轮播（Carousel）项目 -->
                            <div class="carousel-inner">
                                <c:if test="${sessionScope.TopDefaultMovie != null}">
                                    <c:forEach var="item"   items="${sessionScope.TopDefaultMovie}" varStatus="i">
                                        <c:if test="${i.count==1&&i.count<6}">
                                            <div class="item active">
                                                <img src="${item.backpost}" alt="${item.movieId}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("alt")}, function (data) {
                                                if (data=="success") {
                                                    location.href = "<%=basePath%>MovieDescription"
                                                } else {
                                                }
                                            })' style="width:400px;height: 100%">
                                            </div>
                                        </c:if>
                                        <c:if test="${i.count!=1&&i.count<6}">
                                            <div class="item">
                                                <img src="${item.backpost}" alt="${item.movieId}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("alt")}, function (data) {
                                                if (data=="success") {
                                                    location.href = "<%=basePath%>MovieDescription"
                                                } else {
                                                }
                                            })' style="width: 500px;height: 100%">
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </div>

                        </div>

                        <div class="clash-card giant">
                            <div class="clash-card__unit-name">${sessionScope.TopDefaultMovie[0].movieName}</div>
                                            <div class="clash-card__unit-movieDescription">${sessionScope.TopDefaultMovie[0].movieDescription}</div>
                                            <div class="clash-card__unit-stats clash-card__unit-stats--giant clearfix">
                                                <div class="one-third">
                                                   <%--<div class="stat">2<sup>M</sup></div>--%>
                                                        <div class="stat">上映日期</div>
                                                        <div class="stat-value"><fmt:formatDate value="${sessionScope.TopDefaultMovie[0].movieShowyear}" pattern="yyyy-MM-dd"/></div>
                                                </div>

                                                <div class="one-third">
                                                    <div class="stat">多少人看</div>
                                                    <div class="stat-value">${sessionScope.TopDefaultMovie[0].movieRateNum}</div>
                                                </div>

                                                <div class="one-third no-border">
                                                    <div class="stat">电影总评</div>
                                                    <div class="stat-value">${sessionScope.TopDefaultMovie[0].movieAverating}</div>
                                                </div>

                                            </div>
                        </div> <!-- end clash-card giant-->
                    </div>
            </div>

            </div> <!-- end container -->
    </div>
</div>

<%--智能提示框--%>
<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 155px;margin-left: 42px;" >
    <ul id="search-result">
    </ul>
</div>
<!-- 点击加载更多事件，通过SC模板加载电影信息-->

<script>
    $(document).on("click",'#loadmore',function() {
        $.post("<%=basePath%>loadingmore",{molimit:$("#list").children("a").length,type:$("label[class='activate']").attr("value"),sort: $("input[name='sort']:checked").val()},
            function (data) {
            if (data.status == 200) {
                if(data.data.length!=0) {
                $.each(data.data, function (i, item) {

                        var headHtml = $("#subject-tmpl").html();
                        if (item.moviePic == "http://image.tmdb.org/t/p/w185"||item.moviePic==null)
                            headHtml = headHtml.replace(/{cover}/g, "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2512283982.jpg");
                        else
                            headHtml = headHtml.replace(/{cover}/g,item.moviePic);
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{rate}/g,changeTwoDecimal_f(item.movieAverating));
                        headHtml = headHtml.replace(/{cover_x}/g, "1500");
                        headHtml = headHtml.replace(/{cover_y}/g, "2200");
                        headHtml = headHtml.replace(/{title}/g, item.movieName);
                        $("#list").append(headHtml);
                })
                }else
                {alert("没有更多影片了")}
            }
            else {
                alert("加载更多图片资源错误");
            }
        })
        //headHtml = headHtml.replace(/{url}/g,"https://movieEntity.douban.com/subject/26882533/?tag=热门");
        //headHtml = headHtml.replace(/{tag}/g,"热门");
    })

</script>

<!-- 电影卡片模板-->

<script type="text/tmpl" id="subject-tmpl">

        <a class="item"  name="imgitem" target="_blank" id="{id}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("id")}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>MovieDescription"
            } else {
            }
        })'>

            <div class="cover-wp">
                <img src="{cover}" alt={title} data-x={cover_x} data-y={cover_y} style="width:100%"/>
            </div>
            <p>{title}
               <strong>{rate}</strong>
            </p>
        </a>
    </script>

<!-- 推荐影片卡片模板-->

<script type="text/tmpl" id="recommodmocard">
             <div class="clash-card__unit-name">{movieName}</div>
             <div class="clash-card__unit-movieDescription">{moviedescription}</div>
             <div class="clash-card__unit-stats clash-card__unit-stats--giant clearfix">
                    <div class="one-third">
                          <div class="stat">上映日期</div>
                          <div class="stat-value">{movieShowyear}</div>
                    </div>
                    <div class="one-third">
                          <div class="stat">多少人看</div>
                          <div class="stat-value">{movieRateNum}</div>
                    </div>
                    <div class="one-third no-border">
                          <div class="stat">电影总评</div>
                          <div class="stat-value">{movieAverating}</div>
                    </div>
             </div>
</script>

<!-- 强制保留一位小数点-->

<script>
function changeTwoDecimal_f(x)
{
　　var f_x = parseFloat(x);
　　if (isNaN(f_x))
　　{
　　　　return 0;
　　}
　　var f_x = Math.round(x*100)/100;
　　var s_x = f_x.toString();
　　var pos_decimal = s_x.indexOf('.');
　　if (pos_decimal < 0)
　　{
　　　　pos_decimal = s_x.length;
　　s_x += '.';
　　}
　　while (s_x.length <= pos_decimal + 1)
　　{
　　　　s_x += '0';
　　}
　　return s_x;
}
</script>

<!-- string cst时间转date-->

<script>
     function dateFormat(date, format) {
        date = new Date(date);
        var o = {
            'M+' : date.getMonth() + 1, //month
            'd+' : date.getDate(), //day
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));

    for (var k in o)
        if (new RegExp('(' + k + ')').test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
    return format;
    }
</script>

<%--搜索栏--%>
<script>

    $("#inp-query").bind("keyup",function () {
        var width = document.getElementById("inp-query").offsetWidth+"px";
        $("#search-suggest").show().css({
            width:width
        });
        //在搜索框输入数据，提示相关搜索信息
        var searchText=$("#inp-query").val();

        $("#search-result").children().remove();
        $.post("<%=basePath%>search",{"search_text":searchText},function (data) {
            if (data.status == 200) {
                if(data.data.length!=0) {
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#movieEntity-tmpl").html();
                        var formatDate = item.movieShowyear;
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{cover}/g, item.moviePic);
                        headHtml = headHtml.replace(/{movieName}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieShowyear}/g, dateFormat(formatDate,'yyyy-MM-dd'));
                        headHtml = headHtml.replace(/{movieDirector}/g, item.movieDirector);
                        headHtml = headHtml.replace(/{movieAverating}/s, item.movieAverating);
                        $("#search-result").append(headHtml);
                    })
                }
                else
                {
//                $("#search-result").html("查无此片");
                    alert("差不到此电影哦~")
                }
            }
            else {
           // alert("加载更多图片资源错误");
            }

        })
    });


</script>

<%--智能提示框模板--%>
<script type="text/tmpl"  id="movieEntity-tmpl">
 <li id="searchResult">
   <div>
      <a value="{id}" style="text-decoration:none" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>MovieDescription"
            } else {
            }
        })'>
         <div style="float:left">
            <img src="{cover}" style="width:80px;height:120px">
         </div>
         <div  style="padding:12px">
            <span>&nbsp;&nbsp;&nbsp;&nbsp;电影名称：{movieName}</span>
            <br>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;上映时间:{movieShowyear}</span>
            <br>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;导演：{movieDirector}</span>
             <br>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;评分：{movieAverating}</span>
         </div>
       </a>
   </div>
 </li>

</script>

<!-- 推荐轮播图片时影片信息改变-->

<script>
    $('#myCarousel').on('slide.bs.carousel', function (event) {
        $("div[class='clash-card giant']").children().remove();
        var $hoder = $('#myCarousel').find('.item'),
            $items = $(event.relatedTarget);
        //getIndex就是轮播到当前位置的索引
        var getIndex= $hoder.index($items);
        if(getIndex==0)
        {
            var formatDate = "${sessionScope.TopDefaultMovie[0].movieShowyear}";
            var headHtml = $("#recommodmocard").html();
            headHtml = headHtml.replace(/{movieName}/g, "${sessionScope.TopDefaultMovie[0].movieName}");
            headHtml = headHtml.replace(/{moviedescription}/g, "${sessionScope.TopDefaultMovie[0].movieDescription}");
            headHtml = headHtml.replace(/{movieShowyear}/g,dateFormat(formatDate,'yyyy-MM-dd'));
            headHtml = headHtml.replace(/{movieRateNum}/g, "${sessionScope.TopDefaultMovie[0].movieRateNum}");
            headHtml = headHtml.replace(/{movieAverating}/g, changeTwoDecimal_f("${sessionScope.TopDefaultMovie[0].movieAverating}"));
            $("div[class='clash-card giant']").append(headHtml);
        }
        if(getIndex==1)
        {
            var formatDate = "${sessionScope.TopDefaultMovie[1].movieShowyear}";
            var headHtml = $("#recommodmocard").html();
            headHtml = headHtml.replace(/{movieName}/g, "${sessionScope.TopDefaultMovie[1].movieName}");
            headHtml = headHtml.replace(/{moviedescription}/g, "${sessionScope.TopDefaultMovie[1].movieDescription}");
            headHtml = headHtml.replace(/{movieShowyear}/g, dateFormat(formatDate,'yyyy-MM-dd'));
            headHtml = headHtml.replace(/{movieRateNum}/g, "${sessionScope.TopDefaultMovie[1].movieRateNum}");
            headHtml = headHtml.replace(/{movieAverating}/g, changeTwoDecimal_f("${sessionScope.TopDefaultMovie[1].movieAverating}"));
            $("div[class='clash-card giant']").append(headHtml);
        }
        if(getIndex==2)
        {
            var formatDate = "${sessionScope.TopDefaultMovie[2].movieShowyear}";
            var headHtml = $("#recommodmocard").html();
            headHtml = headHtml.replace(/{movieName}/g, "${sessionScope.TopDefaultMovie[2].movieName}");
            headHtml = headHtml.replace(/{moviedescription}/g, "${sessionScope.TopDefaultMovie[2].movieDescription}");
            headHtml = headHtml.replace(/{movieShowyear}/g, dateFormat(formatDate,'yyyy-MM-dd'));
            headHtml = headHtml.replace(/{movieRateNum}/g, "${sessionScope.TopDefaultMovie[2].movieRateNum}");
            headHtml = headHtml.replace(/{movieAverating}/g, changeTwoDecimal_f("${sessionScope.TopDefaultMovie[2].movieAverating}"));
            $("div[class='clash-card giant']").append(headHtml);
        }
        if(getIndex==3)
        {
            var formatDate = "${sessionScope.TopDefaultMovie[3].movieShowyear}";
            var headHtml = $("#recommodmocard").html();
            headHtml = headHtml.replace(/{movieName}/g, "${sessionScope.TopDefaultMovie[3].movieName}");
            headHtml = headHtml.replace(/{moviedescription}/g, "${sessionScope.TopDefaultMovie[3].movieDescription}");
            headHtml = headHtml.replace(/{movieShowyear}/g, dateFormat(formatDate,'yyyy-MM-dd'));
            headHtml = headHtml.replace(/{movieRateNum}/g, "${sessionScope.TopDefaultMovie[3].movieRateNum}");
            headHtml = headHtml.replace(/{movieAverating}/g, changeTwoDecimal_f("${sessionScope.TopDefaultMovie[3].movieAverating}"));
            $("div[class='clash-card giant']").append(headHtml);
        }
        if(getIndex==4)
        {
            var formatDate = "${sessionScope.TopDefaultMovie[4].movieShowyear}";
            var headHtml = $("#recommodmocard").html();
            headHtml = headHtml.replace(/{movieName}/g, "${sessionScope.TopDefaultMovie[4].movieName}");
            headHtml = headHtml.replace(/{moviedescription}/g, "${sessionScope.TopDefaultMovie[4].movieDescription}");
            headHtml = headHtml.replace(/{movieShowyear}/g, dateFormat(formatDate,'yyyy-MM-dd'));
            headHtml = headHtml.replace(/{movieRateNum}/g, "${sessionScope.TopDefaultMovie[4].movieRateNum}");
            headHtml = headHtml.replace(/{movieAverating}/g, changeTwoDecimal_f("${sessionScope.TopDefaultMovie[4].movieAverating}"));
            $("div[class='clash-card giant']").append(headHtml);
        }
    });
</script>

<!-- 电影类型标签选择事件-->

<script>
    $("input[name='tag']").click(function () {
        //设置选中标签ACTIVATE之前的remove
        $("#tags-list label").attr("class","");
        var label= $(this).parent();
        label.attr("class","activate");
        //清空电影数据
        $("#list").children().remove();
        //如果type为0请求全部刷新页面
        //请求数据对应的电影类型
        $.post("<%=basePath%>typesortmovie", {
            molimit:$("#list").children("a").length,
            type: $(this).attr("value"),
            sort: $("input[name='sort']:checked").val()
        }, function (data) {
            if (data.status == 200) {
                if (data.data.length != 0) {
                    //返回movielist,用sc模板append
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#subject-tmpl").html();
                        if (item.moviePic == "http://image.tmdb.org/t/p/w185"||item.moviePic==null)
                            headHtml = headHtml.replace(/{cover}/g, "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2512283982.jpg");
                        else
                            headHtml = headHtml.replace(/{cover}/g, item.moviePic);
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{rate}/g, changeTwoDecimal_f(item.movieAverating));
                        headHtml = headHtml.replace(/{cover_x}/g, "1500");
                        headHtml = headHtml.replace(/{cover_y}/g, "2200");
                        headHtml = headHtml.replace(/{title}/g, item.movieName);
                        $("#list").append(headHtml);
                    })
                }
                else {
                    alert("没有该类型影片数据")
                }
            }
            else {
                alert("请求电影信息错误");
            }
        })
    })
</script>

<!-- 电影时序等选择radio事件-->
<script>
    $("input[name='sort']").click(function () {
        $("#list").children().remove()
        //请求数据对应的电影类型
        $.post("<%=basePath%>typesortmovie", {
            molimit:$("#list").children("a").length,
            sort: $(this).attr("value"),
            type: $("label[class='activate']").attr("value")
        }, function (data) {
            if (data.status == 200) {
                if (data.data.length != 0) {
                    //返回movielist,用sc模板append
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#subject-tmpl").html();
                        if (item.moviePic == "http://image.tmdb.org/t/p/w185"||item.moviePic==null)
                            headHtml = headHtml.replace(/{cover}/g, "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2512283982.jpg");
                        else
                            headHtml = headHtml.replace(/{cover}/g, item.moviePic);
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{rate}/g,changeTwoDecimal_f(item.movieAverating));
                        headHtml = headHtml.replace(/{cover_x}/g, "1500");
                        headHtml = headHtml.replace(/{cover_y}/g, "2200");
                        headHtml = headHtml.replace(/{title}/g, item.movieName);
                        $("#list").append(headHtml);
                    })
                }
                else {
                    alert("排序失败");
                }
            }
        })
    })
</script>

</body>
</html>
