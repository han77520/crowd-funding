<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="../include/include_head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my_menu.js"></script>
<script>
    $(function () {
        // 去调用封装好的函数初始化树形结构
        generateTree();

        // 给添加子节点按钮绑定单击响应函数
        $("#treeDemo").on("click", ".addBtn", function () {
            // 把当前节点的id作为新节点的Pid
            window.pId = this.id;

            // 打开模态框
            $("#menuAddModal").modal("show");

            return false;
        });

        $("#menuSaveBtn").click(function () {

            // 收集表单项中用户输入的数据
            var name = $.trim($("#menuAddModal [name=name]").val());
            var url = $.trim($("#menuAddModal [name=url]").val());
            var icon = $.trim($("#menuAddModal [name=icon]:checked").val());

            $.ajax({
                "url": "menu/save",
                "type": "post",
                "dataType": "json",
                "data": {
                    "pid": window.pId,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "success": function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新加载树形结构
                        generateTree();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            $("#menuAddModal").modal("hide");

            // 达到的效果是点击保存后之后清空模态框中的数据
            $("#menuResetBtn").click();

        });

        // 给编辑子节点按钮绑定单击响应函数
        $("#treeDemo").on("click", ".editBtn", function () {
            // 把当前节点的id保存到全局变量中
            window.id = this.id;

            // 打开模态框
            $("#menuEditModal").modal("show");

            // 获取zTreeObj对象
            var zTreeObj = $.fn.zTree.getZTreeObj("treeDemo");

            // 根据id查询节点对象
            var currentNode = zTreeObj.getNodeByParam("id", window.id);

            // 回显表单数据
            $("#menuEditModal [name=name]").val(currentNode.name);
            $("#menuEditModal [name=url]").val(currentNode.url);

            // radio回显的本质是把 value 属性和值为currentNode.icon一样的选中
            $("#menuEditModal [name=icon]").val([currentNode.icon]);

            return false;
        });

        // 给更新按钮绑定单击响应函数
        $("#menuEditBtn").click(function () {
            // 获取name,url,icon的值
            var name = $("#menuEditModal [name=name]").val();
            var url = $("#menuEditModal [name=url]").val();
            var icon = $("#menuEditModal [name=icon]:checked").val();

            $.ajax({
                "url": "menu/edit",
                "type": "post",
                "dataType": "json",
                "data": {
                    "id":window.id,
                    "name": name,
                    "url": url,
                    "icon": icon
                },
                "success": function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新加载树形结构
                        generateTree();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            $("#menuEditModal").modal("hide");

        });

        // 给删除子节点按钮绑定单击响应函数
        $("#treeDemo").on("click", ".removeBtn", function () {
            // 把当前节点的id保存到全局变量中
            window.id = this.id;

            // 打开模态框
            $("#menuConfirmModal").modal("show");

            return false;
        });

        // 给删除按钮绑定单击响应函数
        $("#confirmBtn").click(function () {

            $.ajax({
                "url": "menu/remove",
                "type": "post",
                "dataType": "json",
                "data": {
                    "id":window.id,
                },
                "success": function (response) {
                    var result = response.result;

                    if (result == "SUCCESS") {
                        layer.msg("操作成功！");

                        // 重新加载树形结构
                        generateTree();
                    }

                    if (result == "FAILED") {
                        layer.msg("操作失败！" + response.message);
                    }
                },
                "error": function (response) {
                    layer.msg(response.status + " " + response.statusText);
                }
            });

            $("#menuConfirmModal").modal("hide");

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
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">

                    <%-- 这个标签是zTree动态生成的结点所依赖的静态结点  --%>
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="modal_menu_add.jsp" %>
<%@include file="modal_menu_confirm.jsp" %>
<%@include file="modal_menu_edit.jsp" %>

</body>
</html>

