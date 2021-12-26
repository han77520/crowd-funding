<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="../include/include_head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="crowd/my_role.js"></script>
<script type="text/javascript">

    $(function () {
        // 1.为分页操作准备初始化数据
        window.pageNum = 1;
        window.pageSize = 5;
        window.keyword = "";

        // 2.执行分页的函数，显示分页效果
        generatePage();

        // 3.给查询按钮绑定击函数
        $("#searchBtn").click(function () {
            // 获取文本框中的值
            window.keyword = $("#keywordInput").val();

            // 调用分页函数刷新页面
            generatePage();
        });

        // 点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal('show');
        });

        // 模态框中的保存绑定单机函数
        $("#saveRoleBtn").click(function () {

            // 获取输入框中的值
            var inputRoleName = $.trim($("#inputRoleName").val());


            $.ajax({
                "url": "role/save",
                "type": "post",
                "data": {
                    "name": inputRoleName
                },
                "dataType": "json",
                "success": function (response) {

                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("添加角色成功！");
                    }

                    if (result == "FAILED") {
                        layer.msg("添加角色失败！ 原因为：" + response.message);
                    }

                    // 重新加载分页
                    window.pageNum = 999999;
                    generatePage();
                },
                "error": function (response) {
                    Layer.msg("请求错误，请及时联系运维人员！")
                }
            });

            // 关闭模态框
            $("#addModal").modal('hide');

            // 清理模态框中文本框内容
            $("#inputRoleName").val("");


        })
    })

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
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keywordInput" class="form-control has-success" type="text"
                                       placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="searchBtn" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"
                    ><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="rolePageBody">

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

<%@include file="/WEB-INF/templates/modal_role_add.jsp" %>
>
</body>
</html>

