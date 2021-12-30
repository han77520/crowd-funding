<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form action="assign/do/role" method="post" role="form" class="form-inline">

                        <input type="hidden" name="adminId" value="${requestScope.adminId}">
                        <input type="hidden" name="pageNum" value="${requestScope.pageNum}">
                        <input type="hidden" name="keyword" value="${requestScope.keyword}">

                        <div class="form-group">
                            <label>未分配角色列表</label><br>
                            <select class="form-control" multiple="" size="10" style="width:150px;overflow-y:auto;">
                                <c:forEach items="${requestScope.unAssignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label>已分配角色列表</label><br>
                            <select name="roleId" class="form-control" multiple="multiple" size="10" style="width:100px;overflow-y:auto;">
                                <c:forEach items="${requestScope.assignedRoleList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" style="width: 150px;margin-left: 250px" class="btn btn-lg btn-success btn-block">保存</button>
                    </form>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>

