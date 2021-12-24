<%--
  Created by IntelliJ IDEA.
  User: lixueqin
  Date: 2021/12/18
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <%--
        ${pageContext.request.contextPath}，此表达式本身就带有 /，不需要额外的添加了
      如果带了 / ，就会导致Cookie的path属性有问题，导致不能跳转。
        最后面的 / 一定要保留，因为如果form标签，a标签等链接中包含/的话，它就不会经过base标签解析，所以只能放到base标签中
     --%>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">

    <%--如果有其他的文件引用了JQuery的话，那么其他的文件都必须放在JQuery后面--%>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">

        $(function () {
            $("#btn1").click(function () {
                $.ajax({
                    "url": "send/one", // 请求目标资源路径
                    "type": "post", // 发送方式
                    "data": {       // 要发送的请求参数
                        "array": [1, 2, 3, 4]
                    },
                    "dataType": "text", // 如何对待服务器端返回的数据
                    "success": function (response) { //服务器端处理请求成功后的回调函数，response就是响应体数据
                        alert(response);
                    },
                    "error": function (response) { //服务器端处理请求失败后的回调函数，response就是响应体数据
                        alert(response);
                    }
                });
            });

            var jsonArr = [1, 2, 3, 4];
            var jsonString = JSON.stringify(jsonArr); //json数组转字符串

            $("#btn2").click(function () {
                $.ajax({
                    "url": "send/two", // 请求目标资源路径
                    "type": "post", // 发送方式
                    "data": jsonString, // 要发送的请求参数
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "text", // 如何对待服务器端返回的数据
                    "success": function (response) { //服务器端处理请求成功后的回调函数，response就是响应体数据
                        alert(response);
                    },
                    "error": function (response) { //服务器端处理请求失败后的回调函数，response就是响应体数据
                        alert(response);
                    }
                });
            });


            var jsonObject = {
                "id": 1,
                "loginAcct": "whw",
                "wuerPswd": "111111",
                "userName": "王瀚文",
                "createTime": "2021-12-19"
            };

            var jsonString1 = JSON.stringify(jsonObject); //json对象转字符串

            $("#compose").click(function () {
                $.ajax({
                    "url": "result", // 请求目标资源路径
                    "type": "post", // 发送方式
                    "data": jsonString1, // 要发送的请求参数
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json", // 如何对待服务器端返回的数据
                    "success": function (response) { //服务器端处理请求成功后的回调函数，response就是响应体数据
                        console.log(response);
                    },
                    "error": function (response) { //服务器端处理请求失败后的回调函数，response就是响应体数据
                        console.log(response);
                    }
                });
            });

            $("#btn3").click(function () {
              layer.msg("Layer的弹框")
            });
        });

    </script>

</head>
<body>
        <a href="test">测试ssm整合</a>

        <br/>
        <br/>
        <button id="btn1">Bind [1,2,3,4] One</button>

        <br/>
        <br/>
        <button id="btn2">Bind [1,2,3,4] Two</button>

        <br/>
        <br/>
        <button id="compose">compose</button>

        <br/>
        <br/>
        <button id="btn3">弹框</button>

</body>
</html>
