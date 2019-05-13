<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path=request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans" class="ua-mac ua-webkit">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>爱看不看电影网</title>

    <link rel="SHORTCUT ICON" href="<%=basePath%>assets/img/knowU.ico"/>

    <!-- 星星评分CSS-->
    <link href="<%=basePath%>assets/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
    <!-- 整体DIV CSS-->
    <link href="<%=basePath%>assets/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/wholeframe.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>assets/css/MovieDescription.css" rel="stylesheet" type="text/css">
    <link href="<%=basePath%>assets/css/SuggestList.css" rel="stylesheet" type="text/css">
    <!-- JS-->
    <script src="<%=basePath%>assets/js/jquery.js"></script>
    <script src="<%=basePath%>assets/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>assets/js/star-rating.min.js" type="text/javascript"></script>
    <!-- 页面一开始加载star类和切换喜欢按钮样式-->
    <script type="text/javascript">
        function  load() {
            $("#allstar").rating({
                    showClear: false,
                    size: 'xs',
                    showCaption: false,
                    readonly: true,
                }
            );
            $("#Evaluation").rating({
                min: 0,
                max: 5,
                step: 0.5,
                size: 'sm',
            })
            <%--if("${sessionScope.booluserunlikedmovie}"==1)--%>
                <%--$("#liked").toggleClass('likedactive');--%>
            if("${sessionScope.flag}"==true)
                $("#liked").toggleClass('likedactive');
        }
    </script>
</head>

<body onload="load()">

<!-- 导航栏-->
<nav class="navbar navbar-default" role="navigation" style="background-color: black;margin-bottom: 0%">
    <a class="navbar-brand" href="<%=basePath%>/homepage" style="color: white;margin-bottom: 10px;">想看就看电影网 <img src="<%=basePath%>assets/img/search.png"></a>
    <div class="col-xs-4" style="margin-left: -15px;margin-top: 5px;">
        <input id="inp-query" class="form-control" style="margin-bottom: 8px;margin-top: 8px;border-radius: 5px;" name="search_text"  maxlength="60" placeholder="输入你想看的电影吧·····" value="">
    </div>
    <a class="navbar-brand" href="<%=basePath%>index" style="color: white;margin-top: 5px;">点我找你想看的</a>
    <a class="navbar-brand" href="<%=basePath%>index" style="color: white">选电影</a>
    <c:if test="${sessionScope.user == null}">
        <a class="dream" href="javascript:window.location.href='<%=basePath%>customer/register'" id="register"
           style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                style="color: white" class="glyphicon glyphicon-user"></span> 注册</a>
        <a class="dream" href="javascript:window.location.href='<%=basePath%>customer/login'"
           style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
    </c:if>
    <c:if test="${sessionScope.user != null}">

        <a class="dream" id="logout" href="javascript:window.location.href='<%=basePath%>customer/logout'"
           style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                style="color: white" class="glyphicon glyphicon-log-in"></span> 注销</a>
        <a class="dream" onclick='javascript:$.post("<%=basePath%>customer/profile",{"id":"${sessionScope.user.userId}"}, function (data) {
                if (data=="success") {
                location.href = "<%=basePath%>profile"
                } else {
                }
                })'
           style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}</a>
    </c:if>
</nav>
<br>
<br>

<div class="component-poster-detail">
    <!--bt-->
    <div class="container">

        <!--电影海报上面 电影名称和导演 -->
        <div class="row">
            <!--电影名称导演 -->
            <div class="col-md-9 col-sm-8">
                <h1>${sessionScope.moviedescription.movieName}</h1>
                <h2>Directed by ${sessionScope.moviedescription.movieDirector}</h2>
            </div>
        </div>

        <div class="row">
            <!--电影海报和其他信息/喜欢播放提交按钮 -->
            <div class="col-sm-4">
                <div class="row">

                    <!--最左侧电影图片和星星评分控件 -->
                    <div class="col-md-7 col-sm-12">
                        <div class="movieEntity-poster">

                            <!--电影图片 -->
                            <a><img src="${sessionScope.moviedescription.moviePic}" alt="" style="width: 100%"></a>

                            <%--<!--评分控件，如果用户登录且未评分显示 -->--%>
                            <%--<c:if test="${sessionScope.user != null&&sessionScope.userstar==null}">--%>
                                <%--<div id="evalutiondiv">--%>
                                    <%--<input id="Evaluation">--%>
                                <%--</div>--%>
                            <%--</c:if>--%>
                            <c:if test="${sessionScope.userstar==null}">
                                <div id="evalutiondiv">
                                    <input id="Evaluation">
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!--左侧电影信息 -->
                    <div class="col-md-5 col-sm-12 film-stats" style="">

                        <!--电影信息div -->
                        <div><b style="font-size: 11pt">语言:</b><span
                                style="font-size: 9pt"> 英语</span></div>
                        <div><b style="font-size: 11pt">类别:</b><span
                                style="font-size: 9pt"> ${sessionScope.moviedescription.movieTags}</span></div>
                        <div><b style="font-size: 11pt">上映日期:</b><span style="font-size: 9pt">
                                        <fmt:formatDate value="${sessionScope.moviedescription.movieShowyear}"
                                                        pattern="yyyy-MM-dd"/>
                                    </span></div>
                        <div><b style="font-size: 11pt">多少人看过:</b> <span
                                style="font-size: 9pt">${sessionScope.moviedescription.movieRateNum}</span></div>
                        <div><b style="font-size: 11pt">总评分:</b> <span
                                style="font-size: 9pt">${sessionScope.moviedescription.movieAverating}分</span></div>
                        <div><input id="allstar" value="${sessionScope.moviedescription.movieAverating}"></div>

                        <!--用户评分，如果用户登录且评分过则显示评分信息 -->
                        <c:if test="${sessionScope.user != null&&sessionScope.userstar!=null}">
                            <div><b style="font-size: 11pt">你的评分:</b> <span
                                    style="font-size: 9pt">${sessionScope.userstar.commentStar}分</span></div>
                            <div><b style="font-size: 11pt">日期:</b><span style="font-size: 9pt">
                                        <fmt:formatDate value="${sessionScope.userstar.commentDate}"
                                                        pattern="yyyy-MM-dd"/>
                                    </span></div>
                        </c:if>
                        <br>

                        <!--喜欢按钮，如果用户登录则显示 -->
                        <c:if test="${sessionScope.user != null&&sessionScope.flag==true}">
                        <a  class="btn btn-default btn-md" id="liked" onclick="likedclick()" style="background-color: #fa8280"><span
                                class="glyphicon glyphicon-heart"></span><span class="fm-opt-label"> 喜欢</span></a>
                        </c:if>
                        <c:if test="${sessionScope.user == null || sessionScope.flag==false}">
                            <a  class="btn btn-default btn-md" id="liked" onclick="likedclick()"><span
                                    class="glyphicon glyphicon-heart"></span><span class="fm-opt-label"> 喜欢</span></a>
                        </c:if>
                        <br><br>

                        <!--播放按钮 -->
                        <a class="btn btn-default btn-md"
                           href="http://so.iqiyi.com/so/q_${sessionScope.moviedescription.movieName}" id="play"
                           target="_Blank"><span class="glyphicon glyphicon-play-circle"></span><span
                                class="fm-opt-label"> 播放</span></a><br>
                        <br>

                        <%--<!--提交按钮，如果用户登录且未评分显示 -->--%>
                        <%--<c:if test="${sessionScope.user != null&&sessionScope.userstar==null}">--%>
                            <%--<button id="submitevalutionstar" class="btn btn-default btn-md"--%>
                                    <%--onclick='$.post("<%=basePath%>getstar",{userId:${sessionScope.user.userId},movieId:${sessionScope.moviedescription.movieId},time:getNowFormatDate(),star:$("#Evaluation").val(),commentDescription:$("#commentDescription").val()},function (data) {--%>
                                            <%--alert(data);window.location.href=window.location.href})'><span--%>
                                    <%--class="glyphicon glyphicon-ok-circle"></span><span class="fm-opt-label"> 提交</span>--%>
                            <%--</button>--%>

                        <%--</c:if>--%>
                        <c:if test="${sessionScope.userstar==null}">
                            <button id="submitevalutionstar" class="btn btn-default btn-md"
                                onclick='$.post("<%=basePath%>getstar",{userId:${sessionScope.user.userId},movieId:${sessionScope.moviedescription.movieId},time:getNowFormatDate(),star:$("#Evaluation").val(),commentDescription:$("#commentDescription").val()},function (data) {
                                    alert(data);window.location.href=window.location.href})'><span
                            class="glyphicon glyphicon-ok-circle"></span><span class="fm-opt-label"> 提交</span>
                         </c:if>
                        </button>
                    </div>
                    <%--评论输入框--%>
                    <div class="col-md-7 col-sm-12">
                        <div class="input-group">
                            <%--<div class="input-group-prepend">--%>
                                <%--<span class="input-group-text">With textarea</span>--%>
                            <%--</div>--%>
                            <c:if test="${sessionScope.user != null&&sessionScope.userstar!=null&&sessionScope.userstar.commentDescription!=null}">
                                 <textarea class="form-control" name="commentDescription" id="commentDescription" aria-label="With textarea" placeholder="${sessionScope.userstar.commentDescription}"></textarea>
                            </c:if>
                             <c:if test="${sessionScope.user == null||sessionScope.userstar==null||sessionScope.userstar.commentDescription==null}">
                                <textarea class="form-control" name="commentDescription" id="commentDescription" aria-label="With textarea" placeholder="欢迎评论·····"></textarea>
                             </c:if>

                        </div>
                    </div>
                </div>
            </div>

            <!--右侧电影信息等栏目 -->
            <div class="col-sm-8">

                <!-- 分享链接栏 -->
                <div id="atstbx2" style="float: right;margin-top: -7%"
                     class="at-share-tbx-element addthis-smartlayers addthis-animated at4-show">
                    <div class="at-share-btn-elements" style="float: right;margin-top: -10%">
                        <a id="wbshareBtn" href="javascript:void(0)" target="_blank" class="at-icon-wrapper at-share-btn at-svc-email" style=" border-radius: 0%;">
                            <img style="line-height: 32px; height: 32px; width: 32px;"
                                 src="https://www.vmovier.com/Public/Home/images/baidu-weibo-v2.png?20160109"/>
                        </a>
                        <a id="qzoneshareBtn" href="javascript:void(0)" target="_blank" class="at-icon-wrapper at-share-btn at-svc-bitly" style=" border-radius: 0%;">
                            <img style="line-height: 32px; height: 32px; width: 32px;"
                                 src="https://www.vmovier.com/Public/Home/images/baidu-qzone-v2.png?20160109"/>
                        </a>
                        <a id="qqshareBtn" target="_blank" class="at-icon-wrapper at-share-btn at-svc-bitly" style=" border-radius: 0%;">
                            <img style="line-height: 32px; height: 32px; width: 32px;"
                                 src="https://www.vmovier.com/Public/Home/images/baibu-qq-v2.png?20160109"/>
                        </a>
                    </div>
                </div>

                <!-- Nav tabs 信息切换栏-->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active" style="text-align: center"><a href="#film-info"
                                                                                         aria-controls="film info"
                                                                                         data-toggle="tab"
                                                                                         aria-expanded="true">简介</a>
                    </li>
                    <li role="presentation" class="" style="text-align: center"><a id="reviewsId"
                                                                                   href="#commentRecordEntities"
                                                                                   aria-controls="commentRecordEntities"
                                                                                   data-toggle="tab"
                                                                                   aria-expanded="false">相似电影</a></li>
                    <li role="presentation" class="" style="text-align: center"><a href="#resource"
                                                                                   aria-controls="resource"
                                                                                   data-toggle="tab"
                                                                                   aria-expanded="false">电影资源</a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <!--电影信息 -->
                    <div role="tabpanel" class="tab-pane fade active in" id="film-info">
                        <br>
                        <h2>${sessionScope.moviedescription.movieName}</h2>
                        Directed by ${sessionScope.moviedescription.movieDirector}<br><br>
                        <div><strong>演员表</strong></div>
                        <strong></strong>
                        ${sessionScope.moviedescription.movieLeadactors}<br>

                        <br>
                        <div><strong>故事简介</strong></div>
                        <p><span style="font-weight: 400;"> ${sessionScope.moviedescription.movieDescription}</span></p>
                    </div>

                    <!--推荐电影table -->
                    <div role="tabpanel" class="tab-pane fade" id="commentRecordEntities">

                        <br>

                        <div>
                            <table class="table table-condensed">
                                <thead>
                                <tr>
                                    <th style="font-size: 13pt">电影名</th>
                                    <th style="font-size: 13pt">类型</th>
                                    <th style="font-size: 13pt">导演</th>
                                    <th style="font-size: 13pt">评分</th>
                                </tr>
                                </thead>
                                <tbody id="movietable">
                                </tbody>
                            </table>
                        </div>

                    </div>

                    <!--电影资源-->
                    <div role="tabpanel" class="tab-pane fade" id="resource">
                        <br>
                        <div class="全网搜索 clear none" id="qlink" style="display: block;">
                            <fieldset class="qBox qwatch">
                                <legend>《<span class="keyword">${sessionScope.moviedescription.movieName}</span>》在线观看
                                </legend>
                                <a href="http://so.iqiyi.com/so/q_${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">奇艺搜索</a>
                                <a href="http://v.sogou.com/v?query=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">搜狗影视</a>
                                <a href="http://www.quankan.tv/index.php?s=vod-search-wd-${sessionScope.moviedescription.movieName}.html"
                                   target="_blank" rel="nofllow">全看网</a>
                                <a href="http://www.soku.com/search_video/q_${sessionScope.moviedescription.movieName}?f=1&kb=020200000000000__${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">优酷</a>
                                <a href="http://www.acfun.cn/search/?#query=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">AcFun</a>
                                <a href="http://search.bilibili.com/all?keyword=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">Bilibili</a></fieldset>
                            <fieldset class="qBox qdown">
                                <legend>《<span class="keyword">${sessionScope.moviedescription.movieName}</span>》资源下载&nbsp;
                                </legend>
                                <a href="http://www.atugu.com/infos/${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">搜磁力</a>
                                <a href="http://www.btbtt.me/search-index-keyword-${sessionScope.moviedescription.movieName}.htm"
                                   target="_blank" rel="nofllow">搜种子</a>
                                <a href="http://www.xilinjie.com/s?t=pan&amp;q=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">搜网盘</a>
                                <a href="https://www.ziyuanmao.com/#/result/${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">资源猫</a>
                                <a href="http://www.zimuku.cn/search?q=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">字幕库</a>
                                <a href="http://www.zimuzu.tv/search?keyword=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">字幕组</a></fieldset>
                            <fieldset class="qBox qdata">
                                <legend>《<span class="keyword">${sessionScope.moviedescription.movieName}</span>》资料介绍&nbsp;
                                </legend>
                                <a href="http://baike.baidu.com/search/word?word=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">百度百科</a>
                                <a href="http://www.baike.com/wiki/${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">互动百科</a>
                                <a href="https://zh.wikipedia.org/wiki/${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">维基百科</a>
                                <a href="https://en.wikipedia.org/wiki/${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">Wiki</a></fieldset>
                            <fieldset class="qBox qreview">
                                <legend>《<span class="keyword">${sessionScope.moviedescription.movieName}</span>》评分影评
                                </legend>
                                <a href="https://m.douban.com/search/?query=${sessionScope.moviedescription.movieName}&amp;type=movieEntity"
                                   target="_blank" rel="nofllow">豆瓣电影</a>
                                <a href="http://search.mtime.com/search/?q=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">时光网</a>
                                <a href="http://www.imdb.com/find?q=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">IMDB</a>
                                <a href="https://www.rottentomatoes.com/search/?search=${sessionScope.moviedescription.movieName}"
                                   target="_blank" rel="nofllow">烂番茄</a></fieldset>
                        </div>
                    </div>

                </div>

            </div>
        </div>
        <!-- /row -->
    </div> <!-- /container -->
</div>
<br>

<br>
<br>

<!--底部 -->
<div class="footer">
    <a href="/" target="_blank">客户端</a>
    <a href="/" target="_blank">关于我们</a>
    <a href="/" target="_blank">加入我们</a>
    <div class="tip">Copyright © 2011-2018 &nbsp;&nbsp; <p>声明：本站不提供视频观看，将跳转到第三方网站进行观看</p></a>
        &nbsp;
    </div>
</div>

<%--智能提示框--%>
<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 155px;" >
    <ul id="search-result">
    </ul>
</div>

<br>
</body>
<!-- 点击相似电影<li>-->
<script>
    $('#reviewsId').click(function (event) {
        event.preventDefault();
        $("#movietable").children().remove();
        $.post("<%=basePath%>getSimiMovies", {"id": "${sessionScope.moviedescription.movieId}"},function (data) {
            if (data.status == 200) {
                if(data.data.length!=0) {
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#recommodmovies").html();
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{movieAverating}/g,changeTwoDecimal_f(item.movieAverating));
                        headHtml = headHtml.replace(/{movieDirector}/g, item.movieDirector);
                        headHtml = headHtml.replace(/{movieTags}/g, item.movieTags);
                        headHtml = headHtml.replace(/{movieName}/g, item.movieName);
                        $("#movietable").append(headHtml);
                    })
                }else
                {alert("没有相似影片")}
            }
            else {
                alert("加载更多图片资源错误");
            }
        })
    })
</script>

<!-- 喜欢按钮事件-->
<script>
    function  likedclick() {
        var color=$("#liked").css("background-color");
        var boollike ;
        if(color=="rgb(230, 230, 230)")
            boollike=1;
        else
            boollike=0;
        $.post("<%=basePath%>likedmovie", {"movieId": "${sessionScope.moviedescription.movieId}","boollike":boollike,"userId":"${sessionScope.user.userId}"},function (data) {
            if(data=="success") {
                if (boollike == 1)
                    alert("收藏成功");
                else
                    alert("取消收藏");
            }
            else
                alert("按钮事件失效")
        })

        $("#liked").toggleClass('likedactive');
    }
</script>

<!-- 获取用户评论的当前时间 post用-->
<script>
    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
    }

</script>

<!-- 相似电影table模板-->
<script type="text/tmpl" id="recommodmovies">
    <tr>
    <td>
    <a value="{id}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>MovieDescription"
            } else {
            }
        })'>{movieName}</a></td>
    <td>{movieTags}</td>
    <td>{movieDirector}</td>
    <td>
    <span class="fm-rating">{movieAverating}</span>
        </td>
        </tr>
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
//            alert("加载更多图片资源错误");
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

<!-- 分享连接栏-->
<script>


    function qzoneShare(){
        var qzone_shareBtn = document.getElementById("qzoneshareBtn")
        qzone_url = document.URL, //获取当前页面地址，也可自定义例：wb_url = "http://liuyanzhao.com"
            qzone_title = "电影名称：${sessionScope.moviedescription.movieName}（来自爱看不看电影网）",
            qzone_pic = "",
            qzone_language = "zh_cn";
        qzone_shareBtn.setAttribute("href","http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+qzone_url+"&title="+qzone_title+"&pic="+qzone_pic+"&language="+qzone_language+"");
    }
    qzoneShare();

    function qqShare(){
        var qq_shareBtn = document.getElementById("qqshareBtn")
        qq_url = document.URL, //获取当前页面地址，也可自定义例：wb_url = "http://liuyanzhao.com"
//            qq_appkey = "3118689721",//你的app key
            qq_title = "电影名称：${sessionScope.moviedescription.movieName}（来自爱看不看电影网）",
//            wb_ralateUid = "5936412667",//微博id，获得你的用户名
            qq_pic = "",
            qq_language = "zh_cn";
        qq_shareBtn.setAttribute("href","http://connect.qq.com/widget/shareqq/index.html?url="+qq_url+"&title="+qq_title+"&pic="+qq_pic+"&language="+qq_language+"");
    }
    qqShare();

    function weiboShare(){
        var wb_shareBtn = document.getElementById("wbshareBtn")
        wb_url = document.URL, //获取当前页面地址，也可自定义例：wb_url = "http://liuyanzhao.com"
            wb_appkey = "3118689721",//你的app key
            wb_title = "电影：${sessionScope.moviedescription.movieName}（来自爱看不看电影网）",
            wb_pic = "",
            wb_language = "zh_cn";
        wb_shareBtn.setAttribute("href","http://service.weibo.com/share/share.php?url="+wb_url+"&appkey="+wb_appkey+"&title="+wb_title+"&pic="+wb_pic+"&language="+wb_language+"");
    }
    weiboShare();
</script>



<!-- Go to www.addthis.com/dashboard to customize your tools -->
<script type="text/javascript"
        src="//s7.addthis.com/js/300/addthis_widget.js#pubid=kinointernational"></script>


</html>