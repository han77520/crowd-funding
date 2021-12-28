<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">

<%@include file="../include/include_head.jsp" %>
<link rel="stylesheet" href="ztree/zTreeStyle.css"/>
<script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="crowd/my_menu.js"></script>
<script>
    $(function () {

        // 发送Ajax请求生成数据结构的JSON数据
        $.ajax({
            "url": "menu/get/whole/tree",
            "type": "post",
            "dataType": "json",
            "success": function (response) {
                var result = response.result;
                if (result == "SUCCESS") {
                    // 创建JSON对象用于存储对zTree所做的设置
                    var setting = {
                        "view": {
                            "addDiyDom":myAddDiyDom,
                            "addHoverDom":myAddHoverDom,
                            "removeHoverDom":myRemoveHoverDom
                        },
                        "data":{
                            "key":{
                                "url":"loveLL"
                            }
                        }
                    };

                    var zNodes = response.data;

                    // 初始化数据结构
                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                }

                if (result == "FAILED") {
                    layer.msg("请求失败，请及时联系运维人员~~");
                }
            }
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

</body>
</html>

