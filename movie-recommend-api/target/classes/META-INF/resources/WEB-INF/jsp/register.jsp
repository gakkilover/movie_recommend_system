<%@ page isELIgnored="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%request.setCharacterEncoding("UTF-8");%>
<%
    String path=request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<head>

    <meta charset="utf-8">
    <title>register</title>
    <link rel="SHORTCUT ICON" href="<%=basePath%>assets/img/knowU.ico"/>
    <!-- CSS -->
    <%--<link href="<%=basePath%>assets/css/bootstrap.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=basePath%>assets/css/regandlogcommon.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/register.css">
    <script src="<%=basePath%>assets/js/jquery.js"></script>
    <script src="<%=basePath%>assets/js/jquery.min.js"></script>
    <script src="<%=basePath%>assets/js/register.js"></script>
    <script src="<%=basePath%>assets/js/bootstrap.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <![endif]-->

</head>

<body>
<div class="page-container">
    <h1 style="color: white">请填写注册信息</h1>
    <form id="regForm_mod">
        <%--用户名--%>
        <div id="d1">

            <span  style="color: white" class="glyphicon glyphicon-user"></span>
            <input type="text" name="userName"  id="regName" placeholder="用 户 名" required="required" />
            <span style="color: red"  class="usernameerror"></span>

        </div>


        <%--邮箱--%>
        <div  id="d2">
            <span style="color: white" class="glyphicon glyphicon-envelope"></span>
            <input type="email" name="userEmail"  id="email" placeholder="电子邮箱" required="required">
            <span  style="color: red" class="emailerror"></span>
        </div>

        <%--密码--%>
        <div id="d3">
            <span style="color: white" class="glyphicon glyphicon-asterisk"></span>
            <input type="password" name="userPassword" id="pwd" placeholder="密　码" required="required">
            <span   style="color: red" class="pwderror"></span>

        </div>

            <%--确认密码--%>
        <div id="d4">
            <span style="color: white" class="glyphicon glyphicon-asterisk"></span>
            <input type="password" id="pwdRepeat" placeholder="确 认 密 码" required="required">
            <span   style="color: red" class="pwdRerror"></span>

        </div>
            <div id="d4">
                <input type="text" id="userPhone" name="userPhone" placeholder="请输入手机号码" required="required" style="margin-left: 15px;">

            </div>
            <%--<label class="block clearfix"> <span--%>
                    <%--class="block input-icon input-icon-right"> <input id="userPhone"--%>
                                                                      <%--name="userPhone" type="text" class="form-control" placeholder="请输入手机号码"}>--%>
												<%--</span>--%>
            <%--</label>--%>
        <div class="clearfix">
		    <span class="block input-icon input-icon-right">
			<input id="phoneCode" name="phoneCode" type="text" class="required" style="width: 150px;margin-left: 10px;" placeholder="请输入验证码"/>
            <%--<button type="button" class="btn btn-primary" style="width: 100px;margin-top: 0px;margin-left: 10px;" onclick='javascript:$.post("<%=basePath%>sendCode",{userPhone:$("#userPhone").val()}, function (data) {--%>
                    <%--if ( data.status == 200) {--%>
                        <%--alert("发送成功");--%>
                    <%--} else {--%>
                        <%--alert("发送失败")--%>
                    <%--}--%>
                    <%--})'>发送验证码</button>--%>
             <button type="button" class="btn btn-primary" style="width: 100px;margin-top: 0px;margin-left: 10px;" id="sendCode" name="sendCode">发送验证码</button>
			<%--<button type="button"--%>
                 <%--class="width-30 pull-right btn btn-sm btn-primary" onclick="sendCode()">--%>
				<%--<i class="icon-key"></i> 发送验证码--%>
				<%--</button>--%>
			</span>
            </div>

        <button  class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal #identifier" type="button" style="background-color: #00b4ef;margin-left: 25px;width: 270px" onclick="REGISTER.reg()">注册</button>
            <%--邮箱提示信息--%>

         <ul class="bRadius2 mail">
                <li data-mail="@qq.com" class="item item1" type="none">@qq.com</li>
                <li data-mail="@sina.com" class="item item2" type="none">@sina.com</li>
                <li data-mail="@126.com" class="item item3" type="none">@126.com</li>
                <li data-mail="@163.com" class="item item4" type="none">@163.com</li>
                <li data-mail="@gmail.com" class="item item5" type="none">@gmail.com</li>
         </ul>
    </form>





    <%--//错误提示信息--%>
    <div id="mz_Float">
        <div id="tip"style="color: red" class="bRadius2"></div>
    </div>





    <!-- 电影模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 810px">
            <div class="modal-content"  style="height:620px;width: 810px">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="width: 30px">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">请选择您喜欢的电影</h4>
                </div>
                <div class="modal-body">
                    <table id="tab" border="2px">
                        <tr>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[0].movieName}</h6>
                                <img  alt="0" name="movieId"  class="img" movieId="${sessionScope.TopRegDefaultMovie[0].movieId}" src="${sessionScope.TopRegDefaultMovie[0].moviePic}">

                            </td>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[1].movieName}</h6>
                                <img alt="1"  name="movieId" class="img" movieId="${sessionScope.TopRegDefaultMovie[1].movieId}" src="${sessionScope.TopRegDefaultMovie[1].moviePic}">
                            </td>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[2].movieName}</h6>
                                <img alt="2" name="movieId"  class="img" movieId="${sessionScope.TopRegDefaultMovie[2].movieId}" src="${sessionScope.TopRegDefaultMovie[2].moviePic}">
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[3].movieName}</h6>
                                <img  alt="3" name="movieId" class="img" movieId="${sessionScope.TopRegDefaultMovie[3].movieId}" src="${sessionScope.TopRegDefaultMovie[3].moviePic}">

                            </td>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[4].movieName}</h6>
                                <img  alt="4" name="movieId" class="img" movieId="${sessionScope.TopRegDefaultMovie[4].movieId}" src="${sessionScope.TopRegDefaultMovie[4].moviePic}">

                            </td>

                        </tr>
                        <tr>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[5].movieName}</h6>
                                <img  alt="5" name="movieId" class="img" movieId="${sessionScope.TopRegDefaultMovie[5].movieId}" src="${sessionScope.TopRegDefaultMovie[5].moviePic}">

                            </td>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[6].movieName}</h6>
                                <img alt="6" name="movieId" class="img" movieId="${sessionScope.TopRegDefaultMovie[6].movieId}" src="${sessionScope.TopRegDefaultMovie[6].moviePic}">
                            </td>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[7].movieName}</h6>
                                <img  alt="7" name="movieId" class="img" movieId="${sessionScope.TopRegDefaultMovie[7].movieId}" src="${sessionScope.TopRegDefaultMovie[7].moviePic}">
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[8].movieName}</h6>
                                <img  alt="8" class="img"  name="movieId" movieId="${sessionScope.TopRegDefaultMovie[8].movieId}" src="${sessionScope.TopRegDefaultMovie[8].moviePic}">

                            </td>
                            <td>
                                <h6>${sessionScope.TopRegDefaultMovie[9].movieName}</h6>
                                <img  alt="9" class="img"  name="movieId" movieId="${sessionScope.TopRegDefaultMovie[9].movieId}" src="${sessionScope.TopRegDefaultMovie[9].moviePic}">

                            </td>
                        </tr>
                    </table>


                </div>
                <div class="modal-footer" style="position: relative;top:40px;text-align: center;">

                    <button type="button" class="btn btn-default" data-dismiss="modal" style="width: 55px;height: 30px;">关闭</button>
                    <button type="button" class="btn btn-primary" style="width: 55px;height: 30px" onclick="movieSubmit()">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <!-- 电影标签模态框（Modal） -->
    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog" style="width: 810px">
            <div class="modal-content"  style="height:620px;width: 810px">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="width: 30px">&times;</button>
                    <h4 class="modal-title" id="myModalLabel1">请选择您喜欢的电影类型</h4>
                </div>
                <div class="modal-body">
                    <table style="" class="table">
                    <tr>
                    <c:forEach var="item"  items="${sessionScope.tagList}" varStatus="status">
                        <c:if test="${status.index %5 ==0}">
                            </tr>
                             <tr>
                        </c:if>
                        <td style="">

                        <span class="input-group-addon">
                             <input type="checkbox" aria-label="..." style="width: 20px;height:20px;margin: 5px 0 0;border-radius: 5px;" value="${item.tagId}">
                             <h6>${item.tagName}</h6>
                         </span>
                        </td>
                    </c:forEach>
                    </tr>
                    </table>
                </div>
                <div class="modal-footer" style="position: relative;top:40px;text-align: center;">

                    <button type="button" class="btn btn-default" data-dismiss="modal" style="width: 55px;height: 30px;">关闭</button>
                    <button type="button" class="btn btn-primary" style="width: 55px;height: 30px" onclick="movieSubmit()">提交</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>

<script>
    $('.customCheck1').prop('indeterminate', true),
    $("#sendCode").click(function () {
        $.post("<%=basePath%>sendCode", {
            userPhone:$("#userPhone").val()
        }, function (data) {
            if (data.status == 200) {
                alert("发送成功");
            } else {
                alert("发送失败")
            }
        })
    })
</script>

<%--选择喜欢的电影--%>
<script type="text/javascript">

    function sendCode() {
        var userTel=document.getElementById("userTel").value;
        // var nickName=document.getElementById("nickName").value;
        // var password=document.getElementById("password").value;
        window.location="<%=basePath %>sendCode?userPhone="+userTel;
    }

        $(".img").click(function () {

            $(this).toggleClass("imgSelected");
           // alert($(this).css('border-color'));
    })

    function movieSubmit() {
        var valu="";
        $("input[type=checkbox]").each(function(){
            if($(this).is(":checked")){
                valu+=$(this).val()+",";
            }
        })
        var imgs=$(".imgSelected");
        console.log(imgs);
        var ids = "";
        for(var i =0;i< imgs.size();i++){
            var temp=","+$(imgs[i]).attr("movieId");
            ids+=temp;
        }
        if(valu!="") {
            $.post("<%=basePath %>likedTags", {'tagIds': valu}, function (data) {
                if(data == "SUCCESS") {
                    alert("提交成功");
                    $('#myModal').modal('hide');
                    location.href = "<%=basePath %>page/login";
                }
                else
                    alert("请至少选择一个标签");
            })
        }
        else
            alert("请至少选择一个标签");

    }

</script>

<!-- 注册事件-->

<script type="text/javascript">

    //用户名获得焦点
    $("#regName").focus(function () {
        $("#regName").removeClass("errorC");
        $("#regName").removeClass("checkedN");
        $(".usernameerror").show();
        $(".usernameerror").text("  ");


    });
    //邮箱获得焦点
    $("#email").focus(function () {
        $("#email").removeClass("errorC");
        $("#email").removeClass("checkedN");
        $(".emailerror").show();
        $(".emailerror").text("  ");
    });
    //密码获得焦点
    $("#pwd").focus(function () {
        $("#pwd").removeClass("errorC");
        $("#pwd").removeClass("checkedN");
        $(".pwderror").show();
        $(".pwderror").text("  ");
    });
    //确认密码获得焦点
    $("#pwdRepeat").focus(function () {
        $("#pwdRepeat").removeClass("errorC");
        $("#pwdRepeat").removeClass("checkedN");
        $(".pwdRerror").show();
        $(".pwdRerror").text("  ");
    });

    //用户名失去焦点
    $("#regName").blur(function () {
        if ($("#regName").val() == "") {
            $("#regName").addClass("errorC");
            $(".usernameerror").html("<span>▲用户名不能为空</span>");
            $(".usernameerror").show();


        }
        else if($("#regName").val().length>10 || $("#regName").val().length<4){
            $(".usernameerror").show();
            $(".usernameerror").html("<span>▲请输入4-10位长度用户名</span>").show();
            $("#regName").addClass("errorC");
        }
        else{
            $("#regName").addClass("checkedN");
            $(".usernameerror").show();
            $(".usernameerror").text("");
        }
        //判断用户名是否被占用
        var surl = "";

        var userName = encodeURI(encodeURI($("#regName").val()));
        console.log(userName);
        $.ajax({url:surl + "<%=basePath %>customer/check/"+userName+"/1?r=" + Math.random(),
            success : function(data) {
                if (data.data) {
                } else {
                    $(".usernameerror").show();
                    $(".usernameerror").html("<span>▲用户已被注册，请重新输入</span>");
                    $("#regName").addClass("errorC");
//                    style = 'position: relative ;top:-20px;left: 200px;'
                }
            }
        });
    });


    //密码失去焦点
    $("#pwd").blur(function () {
        var reg1=/^.*[\d]+.*$/;
        var reg2=/^.*[A-Za-z]+.*$/;
        var reg3=/^.*[_@#%&^+-/*\/\\]+.*$/;//验证密码

        if ($("#pwd").val() == "") {
            $("#pwd").addClass("errorC");
            $(".pwderror").show();
            $(".pwderror").html("▲密码不能为空");
        }
        else if ($("#pwd").val().length>16 || $("#pwd").val().length<8){
            $("#pwd").addClass("errorC");
            $(".pwderror").show();
            $(".pwderror").html("<span style = 'position: relative ;left: 28px;'>◀密码长度为8-16个字符</span>");
        }
        else if (!(reg1.test($("#pwd").val()) ||  reg2.test($("#pwd").val())|| reg3.test($("#pwd").val()) )){
            $("#pwd").addClass("errorC");
        }
        else{
            //输入正确
            $("#pwd").addClass("checkedN");
            $(".pwderror").show();
            $(".pwderror").text("");
        }

    })
    //确认密码失去焦点
    $("#pwdRepeat").blur(function () {
        if ($("#pwd").val() != $("#pwdRepeat").val() || $("#pwdRepeat").val() =="") {
            $("#pwdRepeat").addClass("errorC");
            $(".pwdRerror").show();
            $(".pwdRerror").html("▲密码不一致");
        }
        else{
            $("#pwdRepeat").addClass("checkedN");
            $(".pwdRerror").show();
            $(".pwdRerror").text("");
        }

    })

    //邮箱栏键盘操作
    $("#email").keyup(function () {//键盘监听keyup,keydown,keypress
//        alert("键盘操作");
        var emailVal = $("#email").val();
        var emailValN = emailVal.replace(/\s/g,'');//去空
        emailValN = emailValN.replace(/[\u4e00-\u9fa5]/g,'');//屏蔽中文
        if(emailValN!=emailVal){
            $("#email").val(emailValN);//
        }

        var mailVal = emailValN.split("@");
        var mailHtml = mailVal[0];
        if(mailHtml.length>15)
        {
            mailHtml=mailHtml.slice(0,15)+"...";//字数超加省略
        }
        for(var i=1;i<6;i++)
        {
            var M = $(".item"+i).attr("data-mail");
            $(".item"+i).html(mailHtml+M);
        }
    });

    //邮箱提示
    $(".item").click(function () {
        var a = $("#email").val();
        var b = $(this).attr("data-mail");
        $("#email").val(a+b);
        $("#email").trigger("focus");


    });

    $("#email").click(function () {


        $(".mail").show();
        return false;
    });

    $(document).click(function(){
        $(".mail").hide();

    });
    //邮箱失去焦点
    $("#email").blur(function () {
//        $(".mail").hide();
        reg=/^\w+[@]\w+((.com)|(.net)|(.cn)|(.org)|(.gmail))$$/;
        if ($("#email").val() == "") {
            $("#email").addClass("errorC");
            $(".emailerror").show();
            $(".emailerror").html("▲邮箱不能为空");

        }
        else if(!reg.test($("#email").val())){
            $("#email").addClass("errorC");
            $(".emailerror").show();
            $(".emailerror").html("▲邮箱格式错误");
        }

        else {
            $(".emailerror").show();
            $(".emailerror").text("");
            $("#email").addClass("checkedN");
        }

        //判断邮箱是否被占用
        var surl = "";
        $.ajax({url:surl + "<%=basePath %>/customer/check/"+escape($("#email").val())+"/3?r=" + Math.random(),
            success : function(data) {
                if (data.data) {
                } else {
                    $(".emailerror").show();
                    $(".emailerror").html("<span>▲邮箱已被注册，请重新输入</span>");
                    $("#email").addClass("errorC");
//                    style = 'position: relative ;left: 290px;'
                }
            }
        });
    });

    var REGISTER={
        param:{
            surl:""
        },
        inputcheck:function(){
            var flag = true;
            var reg=/^\w+[@]\w+((.com)|(.net)|(.cn)|(.org)|(.gmail))$$/;

            //不能为空检查
            if ($("#regName").val() == "") {
                alert("用户名不能为空！");
                flag = false;
                $('#identifier').modal('hide');
            }
            if($("#regName").val().length>10 || $("#regName").val().length<4){
                alert("请输入4-10位长度用户名！");
                flag = false;
                $('#identifier').modal('hide');
            }

            if ($("#email").val() == "") {
                alert("邮箱不能为空！");
                flag = false;
                $('#identifier').modal('hide');
            }
            if(!reg.test($("#email").val())){
                alert("邮箱格式错误！");
                flag = false;
                $('#identifier').modal('hide');
            }


            if ($("#pwd").val() == "") {
                alert("密码不能为空！");
                flag = false;
                $('#identifier').modal('hide');
            }
            if($("#pwd").val().length>16 || $("#pwd").val().length<8){
                alert("密码长度应为8-16个字符");
                flag = false;
                $('#identifier').modal('hide');
            }
            //密码检查
            if ($("#pwd").val() != $("#pwdRepeat").val()) {
                alert("两次密码不一致！");
                flag = false;
                $('#identifier').modal('hide');
            }
            return flag;
        },
        beforeSubmit:function() {
            var userName = encodeURI(encodeURI($("#regName").val()));
            //检查用户和邮箱是否已经被占用
            $.ajax({
                url : REGISTER.param.surl + "<%=basePath %>/customer/checkboth/"+userName+"/"+escape($("#email").val())+"/4?=" + Math.random(),
                success : function(data) {
                    if (data.data) {
                        REGISTER.doSubmit();
                    } else {
                        alert("用户名或者邮箱已被注册");
                        $('#identifier').modal('hide');

                    }
                }
            });

        },
        doSubmit:function() {
            $.post("<%=basePath %>customer/register",$("#regForm_mod").serialize(), function(data){
                if(data.status == 200){
                    alert('用户注册成功，请选择您喜欢的电影！');
//                    REGISTER.login();
                    $('#myModal1').modal('show');

                } else {
                    alert("注册失败！");
                    $('#identifier').modal('hide');
                }
            });
        },
        login:function() {
            location.href = "/customer/login";
            return false;
        },
        reg:function() {
//            this.doSubmit();
            if (this.inputcheck()) {
                this.beforeSubmit();
            }
        }
    };
</script>

<!-- Javascript背景轮播 -->
<script type="text/javascript">
    var curIndex = 0;
    //时间间隔(单位毫秒)，每秒钟显示一张，数组共有3张图片放在img文件夹下
    var timeInterval = 2000;
    //定义一个存放照片位置的数组，可以放任意个，在这里放3个
    var arr = new Array();
    arr[0] = "<%=basePath%>assets/img/home_back/b3.jpg";
    arr[1] = "<%=basePath%>assets/img/home_back/t2.jpg";
    arr[2] = "<%=basePath%>assets/img/home_back/league1.jpg";
    arr[3] = "<%=basePath%>assets/img/home_back/v1.jpg";
    arr[4] = "<%=basePath%>assets/img/home_back/les.jpg";
    setInterval(changeImg, timeInterval);
    function changeImg() {
        if (curIndex == arr.length - 1) {
            curIndex = 0;
        } else {
            curIndex += 1;
        }
        //设置body的背景图片
        document.body.style.backgroundImage = "URL("+arr[curIndex]+")";  //显示对应的图片
    }
</script>
</body>

</html>

