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