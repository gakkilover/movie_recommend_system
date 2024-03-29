<%--
  Created by IntelliJ IDEA.
  User: wangqi
  Date: 2018/3/17
  Time: 下午9:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path=request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>
        电影推荐系统
    </title>
    <script src="<%=basePath%>assets/js/jquery.js"></script>
    <script src="<%=basePath%>assets/js/bootstrap.min.js"></script>
    <link rel="SHORTCUT ICON" href="<%=basePath%>assets/img/knowU.ico"/>
    <link href="<%=basePath%>assets/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/Homediscovery.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/SuggestList.css" rel="stylesheet" type="text/css">
</head>
<body>
<%--导航栏--%>
<nav class="navbar navbar-default" role="navigation" style="background-color: black;margin-bottom: 0%">
    <%--<a class="navbar-brand" href="<%=basePath%>/homepage" style="color: white">想看就看<img src="<%=basePath%>assets/img/title.gif">电影网</a>--%>
    <a class="navbar-brand" href="<%=basePath%>/homepage" style="color: white;margin-bottom: 10px;">想看就看电影网 <img src="<%=basePath%>assets/img/search.png"></a>
    <div class="col-xs-4" style="margin-left: -15px;margin-top: 5px;">
        <input id="inp-query" class="form-control" style="margin-bottom: 8px;margin-top: 8px;border-radius: 5px;" name="search_text"  maxlength="60" placeholder="输入你想看的电影吧·····" value="">
    </div>
    <a class="navbar-brand" href="<%=basePath%>index" style="color: white;margin-top: 5px;">点我找你想看的</a>
    <!-- 判断用户是否登录-->
    <c:if test="${sessionScope.user == null}">
        <a  class="dream" href="javascript:window.location.href='<%=basePath%>customer/register'" id="register" style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-user"></span> 注册</a>
        <a  class="dream" href="javascript:window.location.href='<%=basePath%>page/login'" style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
    </c:if>
    <c:if test="${sessionScope.user != null}">

        <a class="dream" id="logout" href="javascript:window.location.href='<%=basePath%>customer/logout'" style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-log-in"></span>  注销</a>
        <a class="dream" onclick='javascript:$.post("<%=basePath%>customer/profile",{"id":"${sessionScope.user.userId}"}, function (data) {
                if (data=="success") {
                location.href = "<%=basePath%>profile"
                } else {
                }
                })' style=" text-decoration:none;float: right;color: white;font-size: 13pt;margin-top: 12px;margin-right: 10px"><span style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}</a>
    </c:if>
</nav>

<%--智能提示框--%>
<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 155px;margin-left: 42px;" >
    <ul id="search-result">
    </ul>
</div>

<div class="fm-discovery" id="wholediv" style="background-image: url('${sessionScope.TopDefaultMovie[0].backpost}')">

    <!-- 左侧电影信息卡片-->
    <div class="x-kankan">
        <!-- 左侧电影信息卡片-->
        <div id="x-kankan-detail" class="x-kankan-detail">
            <p class="x-kankan-title">
                <a name="movienametag" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>MovieDescription"
            } else {
            }
        })' class="q" data-toggle="tooltip" value="${sessionScope.TopDefaultMovie[0].movieId}" data-placement="top" data-original-title="点击查看${sessionScope.TopDefaultMovie[0].movieName}的详细资料">
                    ${sessionScope.TopDefaultMovie[0].movieName}
                </a>
                <span class="revision-score">
                <span class="fm-rating">
        <a class="fm-green" value="${sessionScope.TopDefaultMovie[0].movieId}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>MovieDescription"
            } else {
            }
        })' name="movieaverating"  rel="nofollow">Score: ${sessionScope.TopDefaultMovie[0].movieAverating} </a></span></span>
            </p>
            <p  name="moviedescription" class="x-kankan-desc">${sessionScope.TopDefaultMovie[0].movieDescription}
            </p>
            <p name="moviedirector" class="muted x-kankan-starring" style="margin-top:5px;">Directed by ${sessionScope.TopDefaultMovie[0].movieDirector}</p>
            <p name="movietype" class="muted">Type:${sessionScope.TopDefaultMovie[0].movieTags}</p>
        </div>
    </div>

    <!-- 右侧按钮-->
    <div class="x-usermovie-controls x-kankan-buttons">
        <!-- 右侧按钮-->
        <div class="btn-group fm-discovery-actions">
            <!-- 搜索影片资源跳转详情页-->
            <a name="moviedesc" data-placement="top" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>MovieDescription"
            } else {
            }
        })'class="btn-default revision-btn-left "value="${sessionScope.TopDefaultMovie[0].movieId}" title="" data-toggle="tooltip" data-movieEntity="the-other-guys" data-cat="watched" data-class="btn-success" data-original-title="搜索资源">
                <span class="glyphicon glyphicon-search"></span>
            </a>
            <!-- 搜索播放同播放按钮-->
            <a name="moviehref" target="_blank" href="http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[0].movieName}" data-placement="top" class="btn-default revision-btn-left " title="" data-toggle="tooltip" data-movieEntity="the-other-guys" data-cat="liked" data-class="btn-danger" data-original-title="观看电影">
                <span class="glyphicon glyphicon-film"></span>
            </a>
        </div>
        <!-- 右侧按钮-->
        <div class="btn-group x-kankan-navigator">
            <!-- 上一部电影-->
            <a class="revision-btn-history" id="pre">
                <span>&nbsp;</span><span class="glyphicon glyphicon-chevron-left"></span>
            </a>
            <!-- 下一部电影-->

            <a  class="btn-default revision-btn-next" id="next">
                <span>换一个看看&nbsp;</span><span class="glyphicon glyphicon-chevron-right"></span>
            </a>
        </div>
    </div>

    <!-- 播放按钮-->
    <div class="xx-play-button">
        <a name="moviehref" href="http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[0].movieName}" target="_blank" class="q" data-title="全网资源搜索" style="display: none;">
            <img src="<%=basePath%>assets/img/Homeimg/kankan_play.7b61b6e9285d.png" alt="播放按钮">
        </a>
    </div>

</div>

<!--页面按钮hover提示 -->
<script>
    $(function(){
        if(!('ontouchstart' in window)) {
            $('[data-toggle="tooltip"]').tooltip();
        }
        if($('.top_messages').length > 0){
            setTimeout(function () {
                $('.top_messages').fadeOut();
            }, 5000);
        }
        $('.fm-lazy-img').each(function(i,e){
            $(e).attr('src', $(e).attr('data-src'));
        });
    });
</script>

<!--播放前进后退按钮事件 -->
<script>
    //播放按钮
    window.setTimeout(function(){
        $('.xx-play-button a').fadeIn(500, function(){
            window.setTimeout(function(){
                if(! $('.xx-play-button a').attr('data-hover')){
                    $('.xx-play-button a').hide();
                }
            }, 10*1000);
        });
        $('#fm_cache').html('<img src="http://7xksqe.com1.z0.glb.clouddn.com/media/backdrops/nC/nCK3Api5TteYOhbc7JTrbcD9OlO.jpg-discovery720" style="display:none;">');

    }, 500);
    $('.xx-play-button').mouseenter(function(){
        $(this).children('a').show();
        $(this).children('a').attr('data-hover', 'true');
    }).mouseleave(function(){
        $(this).children('a').hide();
    });
<!--回退上一部电影按钮 -->
    $('#pre').click(function(){

        var m=JSON.parse('${sessionScope.TopDefaultMovieMap}');
        var movieId=$("a[name='movienametag']").attr("value");
        if(m[movieId]==0)
        {
            var url="${sessionScope.TopDefaultMovie[4].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[4].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[4].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[4].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[4].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[4].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[4].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[4].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[4].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[4].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[4].movieTags}");

        }
        if(m[movieId]==1)
        {
            var url="${sessionScope.TopDefaultMovie[0].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[0].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[0].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[0].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[0].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[0].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[0].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[0].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[0].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[0].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[0].movieTags}");
        }
        if(m[movieId]==2)
        {
            var url="${sessionScope.TopDefaultMovie[1].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[1].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[1].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[1].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[1].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[1].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[1].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[1].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[1].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[1].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[1].movieTags}");
        }
        if(m[movieId]==3)
        {
            var url="${sessionScope.TopDefaultMovie[2].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[2].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[2].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[2].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[2].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[2].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[2].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[2].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[2].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[2].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[2].movieTags}");
        }
        if(m[movieId]==4)
        {
            var url="${sessionScope.TopDefaultMovie[3].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[3].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[3].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[3].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[3].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[3].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[3].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[3].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[3].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[3].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[3].movieTags}");
        }

    });
    <!--下一部电影按钮 -->
    $('#next').click(function(){
        var m=JSON.parse('${sessionScope.TopDefaultMovieMap}');
        var movieId=$("a[name='movienametag']").attr("value");
        if(m[movieId]==0)
        {
            var url="${sessionScope.TopDefaultMovie[1].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[1].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[1].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[1].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[1].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[1].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[1].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[1].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[1].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[1].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[1].movieTags}");

        }
        if(m[movieId]==1)
        {
            var url="${sessionScope.TopDefaultMovie[2].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[2].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[2].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[2].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[2].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[2].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[2].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[2].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[2].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[2].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[2].movieTags}");
        }
        if(m[movieId]==2)
        {
            var url="${sessionScope.TopDefaultMovie[3].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[3].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[3].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[3].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[3].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[3].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[3].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[3].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[3].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[3].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[3].movieTags}");
        }
        if(m[movieId]==3)
        {
            var url="${sessionScope.TopDefaultMovie[4].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[4].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[4].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[4].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[4].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[4].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[4].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[4].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[4].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[4].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[4].movieTags}");
        }
        if(m[movieId]==4)
        {
            var url="${sessionScope.TopDefaultMovie[0].backpost}";
            $("#wholediv").css('background-image',"url("+url+")" );
            $("a[name=\"moviehref\"]").attr("href","http://so.iqiyi.com/so/q_${sessionScope.TopDefaultMovie[0].movieName}");
            $("a[name=\"moviedesc\"]").attr("value","${sessionScope.TopDefaultMovie[0].movieId}");
            $("a[name='movienametag']").attr("value","${sessionScope.TopDefaultMovie[0].movieId}");
            $("a[name='movienametag']").attr("data-original-title","点击查看${sessionScope.TopDefaultMovie[0].movieName}的详细资料");
            $("a[name='movienametag']").text("${sessionScope.TopDefaultMovie[0].movieName}");
            $("a[name='movieaverating']").attr("value","${sessionScope.TopDefaultMovie[0].movieId}");
            $("a[name='movieaverating']").text("Score:${sessionScope.TopDefaultMovie[0].movieAverating}");
            $("p[name='moviedescription']").text("${sessionScope.TopDefaultMovie[0].movieDescription}");
            $("p[name='moviedirector']").text("Directed by ${sessionScope.TopDefaultMovie[0].movieDirector}");
            $("p[name='movietype']").text("Type:${sessionScope.TopDefaultMovie[0].movieTags}");
        }

    });
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
        $.post("<%=basePath%>/search",{"search_text":searchText},function (data) {
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
                    alert("查不到此电影哦~")
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
</body>
</html>
