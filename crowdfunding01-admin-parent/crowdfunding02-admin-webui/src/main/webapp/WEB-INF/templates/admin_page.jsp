<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@include file="../include/include_head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>

<script type="text/javascript">

    $(function () {
        // 对页码导航条进行初始化操作
        initPagination();
    });

    // 生成页码导航条
    function initPagination() {
        // 获取总记录数
        var totalRecord = ${requestScope.pageInfo.total};

        // 创建Json对象用于存储PageInfo对象的属性
        var properties = {
            num_edge_entries: 3, // 边缘页数
            num_display_entries: 5, // 主体页数
            callback: pageSelectCallback, // 点击页码之后的回调函数
            items_per_page:${requestScope.pageInfo.pageSize}, // 每页显示条目数
            current_page:${requestScope.pageInfo.pageNum - 1}, // 当前页数
            prev_text: "上一页",
            next_text: "下一页"
        };

        // 生成页码导航条
        $("#Pagination").pagination(totalRecord, properties);
    }

    // 用户点击 "上一页、下一页、1、2、3......"这样的页码时调用这个函数实现页面跳转
    // 回调函数的含义：声明出来以后交给系统或者框架使用
    // pageIndex是Pagination传进来的页码数(从0开始)
    function pageSelectCallback(pageIndex, jQuery) {

        // 根据pageIndex计算得到pageNum
        var pageNum = pageIndex + 1;

        // 跳转路径
        window.location.href = "admin/get/page?pageNum=" + pageNum + "&keyword=${param.keyword}";

        // 取消点击超链接的默认行为
        return false;

    }

</script>

<body>

<%@include file="../include/include_nav.jsp" %>

<div class="container-fluid">
    <div class="row">

        <%@include file="../include/inclued_siderbar.jsp" %>

        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form action="admin/get/page" method="post" class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input name="keyword" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;">
                        <i class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <a style="float:right" class="btn btn-primary" href="admin/to/add">
                        <i class="glyphicon glyphicon-plus"></i> 新增
                    </a>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope.pageInfo.list}">
                                <tr>
                                    <td colspan="6" align="center"> 抱歉!数据为空~~~</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope.pageInfo.list}">
                                <c:forEach items="${requestScope.pageInfo.list}" var="admin" varStatus="myStatus">
                                    <tr>
                                        <td>${myStatus.count}</td>
                                        <td><input type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <c:if test="${empty param.keyword}">
                                                <a href="assign/to/assign/role/${admin.id}/${param.pageNum}"
                                                   class="btn btn-success btn-xs"><i
                                                        class=" glyphicon glyphicon-check"></i></a>
                                                <a href="admin/to/edit/${admin.id}/${param.pageNum}"
                                                   class="btn btn-primary btn-xs">
                                                    <i class=" glyphicon glyphicon-pencil"></i>
                                                </a>
                                                <a href="admin/remove/${admin.id}/${param.pageNum}"
                                                   class="btn btn-danger btn-xs">
                                                    <i class=" glyphicon glyphicon-remove"></i></button>
                                                </a>
                                            </c:if>
                                            <c:if test="${!empty param.keyword}">
                                                <a href="assign/to/assign/role/${admin.id}/${param.pageNum}/${param.keyword}"
                                                   class="btn btn-success btn-xs"><i
                                                        class=" glyphicon glyphicon-check"></i></a>
                                                <a href="admin/to/edit/${admin.id}/${param.pageNum}/${param.keyword}"
                                                   class="btn btn-primary btn-xs">
                                                    <i class=" glyphicon glyphicon-pencil"></i>
                                                </a>
                                                <a href="admin/remove/${admin.id}/${param.pageNum}/${param.keyword}"
                                                   class="btn btn-danger btn-xs">
                                                    <i class=" glyphicon glyphicon-remove"></i></button>
                                                </a>
                                            </c:if>
                                        </td>
                                    </tr>

                                </c:forEach>
                            </c:if>

                            </tbody>

                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination">
                                        <!-- 这里显示分页 -->
                                    </div>
                                </td>
                            </tr>
                            </tfoot>

                        </table>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

</body>
</html>

