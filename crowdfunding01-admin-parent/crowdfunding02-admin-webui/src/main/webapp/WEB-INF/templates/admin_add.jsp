<%--
  Created by IntelliJ IDEA.
  User: lixueqin
  Date: 2021/12/20
  Time: 19:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="../include/include_head.jsp"%>

<body>

<%@include file="../include/include_nav.jsp"%>

<div class="container-fluid">
    <div class="row">

        <%@include file="../include/inclued_siderbar.jsp"%>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="admin/to/main">首页</a></li>
                <li><a href="admin/get/page">数据列表</a></li>
                <li class="active">新增</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form action="admin/to/save" method="post" role="form">
                        <p style="color: red">${requestScope.exception.message}</p>
                        <div class="form-group">
                            <label for="exampleInputloginAcct">登录账号</label>
                            <input name="loginAcct" type="text" class="form-control" id="exampleInputloginAcct" placeholder="请输入登陆账号">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputuserPswd">登录密码</label>
                            <input type="password" name="userPswd" class="form-control" id="exampleInputuserPswd" placeholder="请输入登录密码">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputuserName">登录昵称</label>
                            <input type="text" name="userName" id="exampleInputuserName" class="form-control"  placeholder="请输入登录昵称">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputEmail1">邮箱地址</label>
                            <input type="email" name="email" class="form-control" id="exampleInputEmail1" placeholder="请输入邮箱地址">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>

