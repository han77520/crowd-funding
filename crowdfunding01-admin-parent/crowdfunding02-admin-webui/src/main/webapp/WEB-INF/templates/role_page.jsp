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

        // 4.点击新增按钮打开模态框
        $("#showAddModalBtn").click(function () {
            $("#addModal").modal('show');
        });

        // 4.模态框中的保存绑定单击事件
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
                    layer.msg("请求错误，请及时联系运维人员！")
                }
            });

            // 关闭模态框
            $("#addModal").modal('hide');

            // 清理模态框中文本框内容
            $("#inputRoleName").val("");

        });

        /*
        6.給修改按鈕綁定单机事件,这种方法无效！！！
        $(".pencilBtn").click(function () {
            alert("aaaa")
        })
        */

        var roleId;

        //使用jQuery对象的on()函数
        /*
        * ① 首先找到动态元素附着的那个静态元素
        * ② 使用on(),参数1为事件类型，参数2为绑定事件的选择器，参数3就是事件的响应函数
        */
        $("#rolePageBody").on("click", ".pencilBtn", function () {
            // 打开模态框
            $("#editModal").modal('show');

            // 获取表格中当前行中的角色名称和当前id
            var roleNameCallBack = $(this).parent().prev().text();
            roleId = this.id;

            $("#updateRoleName").val(roleNameCallBack);
        });

        // 给更新按钮绑定单击事件
        $("#updateRoleBtn").click(function () {

            // 获取文本框中的roleName
            var updateRoleName = $("#updateRoleName").val();

            $.ajax({
                "url": "role/update",
                "type": "post",
                "data": {
                    "id": roleId,
                    "name": updateRoleName
                },
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("更新成功！");
                    }

                    // 重新加载分页
                    generatePage();

                    if (result == "FAILED") {
                        layer.msg("更新失败！ 原因为：" + response.message);
                    }

                },
                "error": function (response) {
                    layer.msg("请求错误，请及时联系运维人员！")
                }
            });

            // 关闭模态框
            $("#editModal").modal('hide');
        });

        // var roleAarry = [{roleId:1,roleName:"aaa"},{roleId:2,roleName:"bbb"},{roleId:3,roleName:"ccc"},{roleId:4,roleName:"ddd"},
        //     {roleId:5,roleName:"eee"},{roleId:6,roleName:"fff"},{roleId:7,roleName:"ggg"}];
        // showConfirmModal(roleAarry);

        // 给模态框中的确认删除按钮绑定单击事件
        $("#removeRoleBtn").click(function () {

            var requestBody = JSON.stringify(window.roleIdArray);

            $.ajax({
                "url": "role/removeById",
                "type": "post",
                "data": requestBody,
                "contentType": "application/json;charset=UTF-8",
                "dataType": "json",
                "success": function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("删除成功！");
                    }

                    // 重新加载分页
                    generatePage();

                    if (result == "FAILED") {
                        layer.msg("删除失败！ 原因为：" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg("请求错误，请及时联系运维人员！")
                }
            });

            $("#confirmModal").modal('hide');
        });

        // 给删除按钮绑定单击事件
        $("#rolePageBody").on("click", ".removeBtn", function () {

            var roleName = $(this).parent().prev().text();

            // 创建role对象存入数组 roleArray 中
            var roleArray = [
                {roleId: this.id, roleName: roleName}
            ];

            showConfirmModal(roleArray);
        });

        // 给总的 checkBox 绑定单击事件
        $("#summaryBox").click(function () {

            // ①获取当前多选框自身的状态
            var currentStatus = this.checked;

            // ②用当前多选框的状态去设置其他多选框
            $(".itemBox").prop("checked", currentStatus);
        });

        // 全选全不选的反向操作
        $("#rolePageBody").on("click", ".itemBox", function () {

            // 获取当前选中的个数
            var checkedBoxCount = $(".itemBox:checked").length;

            // 获取全部 .itemBox 的个数
            var totalBoxCount = $(".itemBox").length;

            $("#summaryBox").prop("checked", checkedBoxCount == totalBoxCount);
        });

        // 给批量删除的按钮绑定单击事件
        $("#batchRemoveBtn").click(function () {

            var roleArray = [];

            // 遍历当前选中的 checkBox
            $(".itemBox:checked").each(function () {

                // 获取 role 的id
                var roleId = this.id;

                // 获取 role 的name
                var roleName = $(this).parent().next().text();

                roleArray.push({"roleId":roleId,"roleName":roleName});
            });

            // 检查roleArray是否为0
            if (roleArray.length == 0) {
                layer.msg("请选中之后再删除~~~");
                return;
            }

            showConfirmModal(roleArray);
        });
    });

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
                    <button type="button" id="batchRemoveBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i
                            class=" glyphicon glyphicon-remove"></i> 删除
                    </button>
                    <button id="showAddModalBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>

                            <tbody id="rolePageBody"></tbody>

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
<%@include file="/WEB-INF/templates/modal_role_edit.jsp" %>
<%@include file="/WEB-INF/templates/modal_role_confirm.jsp" %>
</body>
</html>

