<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="../include/include_head.jsp" %>

<body>

<%@include file="../include/include_nav.jsp" %>

<div class="container-fluid">
    <div class="row">

        <%@include file="../include/inclued_siderbar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">修改</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <form  action="admin/update" method="post" role="form">
                        <input type="hidden" name="id" value="${editAdmin.id}">
                        <input type="hidden" name="pageNum" value="${pageNum}">
                        <input type="hidden" name="keyword" value="${keyword}">
                        <p>${requestScope.exception.message}</p>
                        <div class="form-group">
                            <label>登陆账号</label>
                            <input type="text" name="loginAcct" class="form-control"  value="${editAdmin.loginAcct}">
                        </div>
                        <div class="form-group">
                            <label>用户昵称</label>
                            <input type="text" name="userName" class="form-control" value="${editAdmin.userName}">
                        </div>
                        <div class="form-group">
                            <label>邮箱地址</label>
                            <input type="email" name="email" class="form-control"  value="${editAdmin.email}">
                            <p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
                        </div>
                        <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-edit"></i>修改</button>
                        <button type="reset" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>

