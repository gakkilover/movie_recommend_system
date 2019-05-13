<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path=request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh" data-theme="light">

<head>
    <meta charset="utf-8"/>
    <title data-react-helmet="true">爱看不看电影网</title>
    <link rel="SHORTCUT ICON" href="<%=basePath%>assets/img/knowU.ico"/>
    <link data-react-helmet="true" rel="prefetch" href="<%=basePath%>assets/img/user_cover_image.jpg"/>
    <script src="<%=basePath%>assets/js/jquery.js"></script>
    <script src="<%=basePath%>assets/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>assets/js/star-rating.min.js" type="text/javascript"></script>
    <link href="<%=basePath%>assets/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
    <link href="<%=basePath%>assets/css/douban.main.css" rel="stylesheet"/>
    <link href="<%=basePath%>assets/css/bootstrap.css" rel="stylesheet">
    <link href="<%=basePath%>assets/css/SuggestList.css" rel="stylesheet" type="text/css">
    <style>
        .component-poster-detail .nav-tabs > li {
            width: 50% !important;
        }
    </style>
    <%--star rating 类--%>
    <script type="text/javascript">
        window.onload = function () {
            $("input[name='allstar']").rating({
                        showClear: false,
                        size: 'xs',
                        showCaption: false,
                        readonly: true,
                    }
            );
        }
    </script>
</head>

<body class="Entry-body">
<div id="root">

    <div data-reactid="5">
        <!-- 导航栏-->
        <nav class="navbar navbar-default" role="navigation" style="background-color: black;margin-bottom: 0%">
            <a class="navbar-brand" href="<%=basePath%>homepage" style="color: white">电影推荐网站</a>

            <div class="col-xs-4">
                <input id="inp-query" class="form-control"
                       style="margin-bottom: 8px;margin-top: 8px;border-radius: 5px;" name="search_text" maxlength="60"
                       placeholder="搜索电影" value="">
            </div>
            <a class="navbar-brand" href="<%=basePath%>index" style="color: white">选电影</a>
            <!-- 判断用户是否登录-->
            <c:if test="${sessionScope.user == null}">
                <a class="dream" href="javascript:window.location.href='<%=basePath%>customer/register'" id="register"
                   style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                        style="color: white" class="glyphicon glyphicon-user"></span> 注册</a>
                <a class="dream" href="javascript:window.location.href='<%=basePath%>page/login'"
                   style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                        style="color: white" class="glyphicon glyphicon-log-in"></span> 登录</a>
            </c:if>
            <c:if test="${sessionScope.user != null}">

                <a class="dream" id="logout" href="javascript:window.location.href='<%=basePath%>customer/logout'"
                   style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                        style="color: white" class="glyphicon glyphicon-log-in"></span> 注销</a>
                <a class="dream" href="javascript:"
                   style="float: right;color: white;font-size: 13pt;margin-top: 10px;margin-right: 10px"><span
                        style="color: white" class="glyphicon glyphicon-user"></span> ${sessionScope.user.userName}</a>
            </c:if>
        </nav>
    </div>

    <main role="main" class="App-main" data-reactid="48">
        <div data-reactid="49">

            <div id="ProfileHeader" class="ProfileHeader" data-reactid="61">
                <div class="Card" data-reactid="62">
                    <div class="ProfileHeader-userCover" data-reactid="63">
                        <div class="UserCoverEditor" data-reactid="64">
                            <!-- 背景图片 -->
                            <div data-reactid="65">
                                <div class="UserCover" data-reactid="71">
                                    <!-- 背景图片 -->
                                    <div class="VagueImage UserCover-image" data-src="<%=basePath%>assets/img/background/a8.jpg"
                                         data-reactid="72">
                                        <img src="<%=basePath%>assets/img/background/a8.jpg">
                                        <div class="VagueImage-mask" data-reactid="73"></div>
                                    </div>
                                    <%--<div class="VagueImage UserCover-image" data-src="<%=basePath%>assets/img/user_cover_image.jpg"--%>
                                         <%--data-reactid="72">--%>
                                        <%--<img src="<%=basePath%>assets/img/user_cover_image.jpg">--%>
                                        <%--<div class="VagueImage-mask" data-reactid="73"></div>--%>
                                    <%--</div>--%>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="ProfileHeader-wrapper" data-reactid="75">

                        <!-- 背景图片一下的用户信息部分-->
                        <div class="ProfileHeader-main" data-reactid="76" style="margin-bottom: 0px;">

                            <!-- 用户头像 -->
                            <div class="UserAvatarEditor ProfileHeader-avatar" style="top:-57px;margin-left: 20px;"
                                 data-reactid="77">
                                <div class="UserAvatar" data-reactid="78">
                                    <img class="Avatar Avatar--large UserAvatar-inner" width="160" height="160"
                                         src="<%=basePath%>assets/img/icon/a95.jpg" srcset="<%=basePath%>assets/img/icon/a95.jpg 2x"
                                         data-reactid="79"/>
                                </div>
                            </div>

                            <div class="ProfileHeader-content" data-reactid="87">

                                <!-- 用户名称 -->
                                <div class="ProfileHeader-contentHead" data-reactid="88">
                                    <h1 class="ProfileHeader-title" data-reactid="89">
                                        <span class="ProfileHeader-name"
                                              data-reactid="90">${sessionScope.user.userName}</span>
                                    </h1>
                                </div>

                                <!-- 头像下的留白空间 -->
                                <div style="overflow:hidden;transition:height 300ms ease-out;"
                                     class="ProfileHeader-contentBody" data-reactid="93">
                                    <div data-reactid="94">
                                        <div class="ProfileHeader-info" data-reactid="95">
                                            <div class="ProfileHeader-infoItem" data-reactid="96">
                                                <div class="ProfileHeader-iconWrapper" data-reactid="97">
                                                </div>
                                                <div class="ProfileHeader-divider" data-reactid="102"></div>
                                                <div class="ProfileHeader-divider" data-reactid="104"></div>
                                                <div class="ProfileHeader-iconWrapper" data-reactid="105">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!--编辑个人资料按钮 -->
                                <div class="ProfileHeader-contentFooter" data-reactid="109">

                                    <div class="ProfileButtonGroup ProfileHeader-buttons" data-reactid="115"
                                         style="bottom: 30px;">
                                        <a href="#" class="Button Button--blue" data-toggle="modal"
                                           data-target="#userEditDialog"
                                           onclick="editUser(${sessionScope.user.userId})">
                                            <!-- react-text: 117 -->编辑
                                            <!-- /react-text -->
                                            <!-- react-text: 118 -->个人
                                            <!-- /react-text -->
                                            <!-- react-text: 119 -->资料
                                            <!-- /react-text -->
                                        </a></div>
                                </div>

                            </div>
                        </div>

                    </div>

                </div>
            </div>

            <div class="Profile-main" data-reactid="120">
                <div class="Profile-mainColumn" data-reactid="121">
                    <div class="Card ProfileMain" id="ProfileMain" data-reactid="122">

                        <div class="ProfileMain-header" data-reactid="123">

                            <!-- 滑动标签<li> -->
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation" class="active" style="text-align: center"><a href="#film-info"
                                                                                                     aria-controls="film info"
                                                                                                     data-toggle="tab"
                                                                                                     aria-expanded="true">喜欢</a>
                                </li>
                                <li role="presentation" class="" style="text-align: center"><a id="reviewsId"
                                                                                               href="#commentRecordEntities"
                                                                                               aria-controls="commentRecordEntities"
                                                                                               data-toggle="tab"
                                                                                               aria-expanded="false">已评价</a>
                                </li>
                            </ul>

                            <!-- 喜欢的电影<li> -->
                            <div class="tab-content">
                                <div class="tab-pane fade active in" id="film-info" data-zop-feedlistfather="1"
                                     data-reactid="158">
                                    <div class="List-header" data-reactid="159">
                                        <h4 class="List-headerText" data-reactid="160"><span data-reactid="161">
              <!-- react-text: 162 -->我喜欢的电影
                          </span></h4>
                                        <div class="List-headerOptions" data-reactid="164"></div>
                                    </div>
                                    <div class="" data-reactid="165">

                                        <c:if test="${sessionScope.movies != null}">
                                            <c:forEach var="item" items="${sessionScope.movies}">

                                                <div class="List-item" data-reactid="166">
                                                    <p class="ul first"></p>
                                                    <table width="100%" class="">
                                                        <tr class="item">
                                                            <td width="100" valign="top">
                                                                <a class="nbg" value="${item.movieId}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
                                                  if (data=="success") {
                                                      location.href = "<%=basePath%>MovieDescription"
                                                  } else {
                                                  }
                                              })' title="${item.movieName}">
                                                                    <img src="${item.moviePic}" width="75"
                                                                         alt="${item.movieName}" class=""/>
                                                                </a>
                                                            </td>
                                                            <td valign="top">
                                                                <div class="pl2">

                                                                    <a value="${item.movieId}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
                                                  if (data=="success") {
                                                      location.href = "<%=basePath%>MovieDescription"
                                                  } else {
                                                  }
                                              })' class="">
                                                                            ${item.movieName}
                                                                    </a>
                                                                    <p class="pl"><fmt:formatDate type="date"
                                                                                                  value="${item.movieShowyear}"
                                                                                                  pattern="yyyy-MM-dd"/>上映</p>
                                                                    <p class="pl">导演：${item.movieDirector}</p>
                                                                    <div class="star clearfix">
                                                                        <span class="allstar40"></span>
                                                                        <span class="rating_nums">${item.movieAverating}</span>
                                                                        <span class="pl">(${item.movieRateNum}人评价)</span>

                                                                    </div>


                                                                </div>

                                                            </td>
                                                        </tr>
                                                    </table>
                                                    <div id="collect_form_11584016"></div>

                                                </div>
                                            </c:forEach>
                                        </c:if>
                                    </div>

                                </div>

                                <!-- 评价过的电影<li> -->
                                <div class="tab-pane fade" id="commentRecordEntities" data-zop-feedlistfather="1" data-reactid="158">
                                    <div class="List-header" data-reactid="159">
                                        <h4 class="List-headerText" data-reactid="160"><span data-reactid="161">
              <!-- react-text: 162 -->我评价过的电影
                          </span></h4>
                                        <div class="List-headerOptions" data-reactid="164"></div>
                                    </div>

                                    <div class="" data-reactid="165">
                                        <!-- 评价过的电影 -->

                                        <c:if test="${sessionScope.commentRecordEntities != null}">
                                            <c:forEach var="item" items="${sessionScope.commentRecordEntities}">

                                                <div class="List-item" data-reactid="166">
                                                    <p class="ul first"></p>
                                                    <table width="100%" class="">
                                                        <tr class="item">
                                                            <td width="100" valign="top">
                                                                <a class="nbg" value="${item.movieId}" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
                                                  if (data=="success") {
                                                      location.href = "<%=basePath%>MovieDescription"
                                                  } else {
                                                  }
                                              })'>
                                                                    <img src="${item.moviePic}" width="75" class=""/>
                                                                </a>
                                                            </td>
                                                            <td valign="top">
                                                                <div class="pl2">
                                                                    <div><input name="allstar" value="${item.commentStar}">
                                                                    </div>
                                                                    <div><b style="font-size: 11pt">你的评分:</b> <span
                                                                            style="font-size: 9pt">${item.commentStar}分</span>
                                                                    </div>
                                          <span property="v:dtreviewed" content="2018-03-19" class="main-meta">
                                              <fmt:formatDate type="date" value="${item.commentDate}"
                                                              pattern="yyyy-MM-dd"/>
                                          </span>
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                            </c:forEach>
                                        </c:if>

                                    </div>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- 右侧模块 -->
                <div class="Profile-sideColumn" data-za-module="RightSideBar" data-reactid="294">
                    <div class="Card" data-reactid="295">
                        <div class="Card-header Profile-sideColumnTitle" data-reactid="296">
                            <div class="Card-headerText" data-reactid="297">推荐电影</div>
                        </div>
                    </div>
                    <!-- 右侧电影推荐列表 -->
                    <div class="Profile-lightList" data-reactid="329">

                        <!-- 右侧电影推荐列表 -->
                        <c:if test="${sessionScope.TopDefaultMovie != null}">
                            <c:forEach var="item" items="${sessionScope.TopDefaultMovie}">
                                <a class="Profile-lightItem" onclick='javascript:$.post("<%=basePath%>Customer/Description",{id:$(this).attr("value")}, function (data) {
            if (data=="success") {
                location.href = "<%=basePath%>MovieDescription"
            } else {
            }
        })' value="${item.movieId}"><span class="Profile-lightItemName" data-reactid="331">${item.movieName}</span><span
                                        class="Profile-lightItemValue" data-reactid="332">${item.movieAverating}分</span></a>
                            </c:forEach>
                        </c:if>

                    </div>

                </div>

            </div>
        </div>
    </main>

</div>

<!-- 用户编辑资料框 -->
<div class="modal fade" id="userEditDialog" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" style="margin-top: 10%;">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">修改用户信息</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" id="edit_user_form">
                    <%--<div class="form-group">--%>
                        <%--<label for="edit_password" class="col-sm-2 control-label">用户密码</label>--%>
                        <%--<div class="col-sm-10">--%>
                            <%--<input type="text" class="form-control" id="edit_password" placeholder="" name="userPassword">--%>
                        <%--</div>--%>
                    <%--</div>--%>
                    <div class="form-group">
                        <label for="edit_name" class="col-sm-2 control-label">用户昵称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="edit_name" placeholder="${sessionScope.userInfo.userName}" name="edit_name" value="${sessionScope.userInfo.userName}">
                        </div>
                    </div>
                        <div class="form-group">
                            <label for="edit_sex" class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="edit_sex" placeholder="${sessionScope.userInfo.userSex}" name="edit_sex" value="${sessionScope.userInfo.userSex}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit_age" class="col-sm-2 control-label">年龄</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="edit_age" placeholder="${sessionScope.userInfo.userAge}" name="edit_age" value="${sessionScope.userInfo.userAge}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit_phone" class="col-sm-2 control-label">手机号</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="edit_phone" placeholder="${sessionScope.userInfo.userPhone}" name="edit_phone" value="${sessionScope.userInfo.userPhone}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="edit_email" class="col-sm-2 control-label">用户邮箱</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="edit_email" placeholder="${sessionScope.userInfo.userEmail}" name="edit_email" value="${sessionScope.userInfo.userEmail}">
                            </div>
                        </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="UPDATE.updateUser()">保存修改</button>
            </div>
        </div>
    </div>
</div>
<%--<script>--%>
    <%--$('#userEditDialog').modal().css({--%>
        <%--'margin-top': function () {--%>
            <%--return ($(this).height() * 0.2);--%>
        <%--}--%>
    <%--});--%>
<%--</script>--%>
<%--智能提示框--%>
<div class="suggest" id="search-suggest" style="display: none; top:43px;left: 155px;">
    <ul id="search-result">
    </ul>
</div>

</body>

<%--搜索栏--%>
<script>

    $("#inp-query").bind("keyup", function () {
        var width = document.getElementById("inp-query").offsetWidth + "px";
        $("#search-suggest").show().css({
            width: width
        });

        //在搜索框输入数据，提示相关搜索信息
        var searchText = $("#inp-query").val();

        $("#search-result").children().remove();
        $.post("<%=basePath%>search", {"search_text": searchText}, function (data) {
            if (data.status == 200) {
                if (data.data.length != 0) {
                    $.each(data.data, function (i, item) {
                        var headHtml = $("#movieEntity-tmpl").html();
                        var formatDate = item.movieShowyear;
                        headHtml = headHtml.replace(/{id}/g, item.movieId);
                        headHtml = headHtml.replace(/{cover}/g, item.moviePic);
                        headHtml = headHtml.replace(/{movieName}/g, item.movieName);
                        headHtml = headHtml.replace(/{movieShowyear}/g, dateFormat(formatDate, 'yyyy-MM-dd'));
                        headHtml = headHtml.replace(/{movieDirector}/g, item.movieDirector);
                        headHtml = headHtml.replace(/{movieAverating}/s, item.movieAverating);
                        $("#search-result").append(headHtml);
                    })
                }
                else {
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
<script type="text/tmpl" id="movieEntity-tmpl">
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
            'M+': date.getMonth() + 1, //month
            'd+': date.getDate(), //day
        };
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));

        for (var k in o)
            if (new RegExp('(' + k + ')').test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ('00' + o[k]).substr(('' + o[k]).length));
        return format;
    }
</script>

<script type="text/javascript">

    var status = 1;
    function editUser(id) {
        $.ajax({
            type: "get",
            url: "<%=basePath%>editUser",
            data: {"userId": id},
            success: function (data) {   // Movie的JSON字符串传过来就行
                 $("#edit_name").val(data.userName);
                 $("#edit_sex").val(data.userSex);
                 $("#edit_age").val(data.userAge);
                $("#edit_email").val(data.userEmail);
                $("#edit_phone").val(data.userPhone);
            }
        });
    }

    <%--更改密码--%>
    var UPDATE = {
        checkInput: function () {

            if ($("#edit_password").val()) {
                if ($("#edit_password").val().length < 6 || $("#edit_password").val().length > 12) {
                    alert("密码必须在6-12之间！");
                    return false;
                }

            }

            return true;
        },
        updateUs: function () {
            $.post("<%=basePath%>updateUser", {
                "userId": "${sessionScope.user.userId}",
                //"password": $("#edit_password").val(),
                "userName": $("#edit_name").val(),
                "userSex": $("#edit_sex").val(),
                "userAge": $("#edit_age").val(),
                "userPhone": $("#edit_phone").val(),
                "userEmail": $("#edit_email").val()
            }, function (data) {
                if(data.status = 200){
                    status = 0;
                    window.location.reload();
                }
            });
        },
        updateUser: function () {
            if (this.checkInput()) {
                this.updateUs();
                if(status = 1){
                    alert("用户信息更新完成");
                }else{
                    alert("更新失败，用户名可能已存在");
                }
                window.location.reload();
            }
        }
    };
</script>


</html>