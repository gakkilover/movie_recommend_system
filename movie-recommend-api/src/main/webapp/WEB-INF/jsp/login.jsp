<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path=request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>Login</title>
    <link rel="SHORTCUT ICON" href="<%=basePath%>assets/img/knowU.ico"/>
    <!-- CSS -->
    <link href="<%=basePath%>assets/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="<%=basePath%>assets/css/regandlogcommon.css">
    <script src="<%=basePath%>assets/js/gVerify.js"></script>
    <script src="<%=basePath%>assets/js/jquery.js"></script>
    <script src="<%=basePath%>assets/js/bootstrap.min.js"></script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <![endif]-->
</head>

<body>

<div class="page-container" style="margin-top: 10%;">
    <h1 style="color: white">登录</h1>
    <form id="logForm_mod">
        <span style="color: white" class="glyphicon glyphicon-user"></span>
        <input type="text" name="userName" id="loginUsername" placeholder="用户名" required="required">
        <span style="color: white" class="glyphicon glyphicon-asterisk"></span>
        <input type="password" name="userPassword" id="loginPassword" placeholder="密　码" required="required">

        <div id="v_container" style="width: 265px;height: 50px; margin-left: 10%;margin-top: 5%;"></div>
        <input type="text" id="code_input" value="" placeholder="请输入验证码" style="width: 265px;background-color: #00FF00; margin-left: 5%;"/></br>
        <%--<button id="my_button">验证</button>--%>

        <button type="button" id="login" onclick="LOGIN.login()" style="width: 25%;">登录</button>
        <button type="button" id="register" style="background-color: #00b4ef;width: 25%;margin-left: 5%">注册</button>
    </form>
</div>

<!-- 登录按钮事件-->

<script type="text/javascript">
    var LOGIN = {
        checkInput: function () {

            if (!$("#loginUsername").val()) {
                alert("请输入登录名！");
                return false;
            }
            if ($("#loginUsername").val() && !$("#loginPassword").val()) {
                alert("请输入密码！");
                return false;
            }
            return true;
        },
        doLogin: function () {
            $.post("<%=basePath%>customer/login", $("#logForm_mod").serialize(), function (data) {
                if (data.status == 200) {
                    alert("登录成功！");
                    location.href = "<%=basePath%>homepage"
                } else {
                    alert("登录失败，原因是：" + data.msg);
                }
            });
        },
        login: function () {
            var res = verifyCode.validate(document.getElementById("code_input").value);
            if (this.checkInput()) {
                if(res){
                    this.doLogin();
                }else{
                    alert("验证码错误");
                }

            }
        }

    };
</script>

<!-- 注册按钮事件-->

<script type="text/javascript">
    $('#register').click(function () {

        location.href="<%=basePath%>customer/register"
    })
</script>

<!-- 背景轮播js -->

<script type="text/javascript">
    var curIndex = 0;
    //时间间隔(单位毫秒)，每秒钟显示一张，数组共有5张图片放在img文件夹下
    var timeInterval = 2000;
    //定义一个存放照片位置的数组，可以放任意个，在这里放3个
    var arr = new Array();
    arr[0] = "<%=basePath%>assets/img/loginimg/be.png";
    arr[1] = "<%=basePath%>assets/img/loginimg/li.jpg";
    arr[2] = "<%=basePath%>assets/img/loginimg/lo.jpg";
    arr[3] = "<%=basePath%>assets/img/loginimg/mu.jpg";
    arr[4] = "<%=basePath%>assets/img/loginimg/de.jpg";
    arr[5] = "<%=basePath%>assets/img/loginimg/a919.jpg";
    arr[6] = "<%=basePath%>assets/img/loginimg/a918.jpg";
    arr[7] = "<%=basePath%>assets/img/loginimg/a911.jpg";
    arr[8] = "<%=basePath%>assets/img/loginimg/a912.jpg";
    arr[9] = "<%=basePath%>assets/img/loginimg/a915.jpg";
    setInterval(changeImg, timeInterval);
    function changeImg() {
        if (curIndex == arr.length - 1) {
            curIndex = 0;
        } else {
            curIndex += 1;
        }
        //设置body的背景图片

        document.body.style.backgroundImage = "URL(" + arr[curIndex] + ")";  //显示对应的图片
    }
</script>
<script>
    var verifyCode = new GVerify("v_container");
    document.getElementById("my_button").onclick = function(){
        var res = verifyCode.validate(document.getElementById("code_input").value);
        if(res){
            alert("验证正确");
        }else{
            alert("验证码错误");
        }
    }
</script>
</body>
</html>

