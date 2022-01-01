// 声明专门的函数用来分配Auth的模态框中显示Auth的树形结构数据
function fillAuthTree() {

    // 发送Ajax请求查询Auth数据
    var ajaxReturn = $.ajax({
        "url": "assign/get/all/auth",
        "type": "post",
        "dataType": "json",
        "async": false
    });

    console.log(ajaxReturn);

    if (ajaxReturn.status != 200) {
        layer.msg("出错了，请联系运维人员！");
        return;
    }

    // 交给zTree去组装
    var authList = ajaxReturn.responseJSON.data;

    var setting = {
        "check": {
            "chkStyle": "checkbox",
            "enable": true
        },
        "data": {
            "simpleData": {
                "enable": true,
                "pIdKey": "categoryId"
            },
            "key": {
                "name": "title"
            }
        }
    };

    // 生成树形结构
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);

    // 让结点默认展开
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");
    zTreeObj.expandAll(true);

    // 查询已分配的Auth的id组成的数组
    ajaxReturn = $.ajax({
        "url": "assign/get/assigned/authId",
        "type": "post",
        "data": {
            "roleId": window.roleId
        },
        "dataType": "json",
        "async": false
    });

    if (ajaxReturn.status != 200) {
        layer.msg("出错了，请联系运维人员！");
        return;
    }

    // 获取 authIdArray
    var authIdArray = ajaxReturn.responseJSON.data;

    // 根据authIdArray把树形结构中对应的结点勾上
    for (var i = 0; i < authIdArray.length; i++) {
        var authId = authIdArray[i];

        var treeNode = zTreeObj.getNodeByParam("id", authId);

        //参数2：true表示勾选结点
        //参数3：false表示取消联动(也就是全选全不选功能)
        zTreeObj.checkNode(treeNode, true, false);
    }

    // 给分配按钮绑定单击事件
    $("#assignBtn").click(function () {
        // 收集树形结构的各个节点中被勾选的节点
        // 声明一个存放id的数组
        var authIdArray = [];

        // 获取zTreeObj对象
        var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

        // 获取被勾选的节点
        var checkedNodes = zTreeObj.getCheckedNodes();

        for (var i = 0; i < checkedNodes.length; i++) {
            var checkedNode = checkedNodes[i];

            var authId = checkedNode.id;

            authIdArray.push(authId);
        }

        var requestBody = JSON.stringify({
            "authIdArray": authIdArray,
            "roleId":[window.roleId]
        });

        authIdArray = JSON.stringify(authIdArray);
        var roleId = JSON.stringify(window.roleId);

        // 发送Ajax请求保存修改后的值
        $.ajax({
            "url": "auth/update",
            "type": "post",
            "data":requestBody,
            "contentType":"application/json;charset=UTF-8",
            "dateType": "json",
            "success": function (response) {
                var result = response.result;

                if (result == "SUCCESS") {
                    layer.msg("配置成功！");
                }

                if (result == "FAILED") {
                    layer.msg("出错了！ 原因为：" + response.message);
                }
            },
            "error": function (response) {
                console.log(response);
                layer.msg("出错了！请及时联系运维人员");
            }
        })

        $("#assignModal").modal("hide");
    });
}

// 显示确认模态框
function showConfirmModal(roleArray) {

    // 打开模态框
    $("#confirmModal").modal('show');

    $("#roleNamDiv").empty();

    window.roleIdArray = [];

    //遍历 roleArray
    for (var i = 0; i < roleArray.length; i++) {
        var role = roleArray[i];


        var roleName = role.roleName;
        var roleId = role.roleId;


        $("#roleNamDiv").append(roleName + "     ");

        if ((i + 1) % 5 == 0) {
            $("#roleNamDiv").append("<br/>");
        }

        window.roleIdArray.push(roleId);

    }
}

// 执行分页，生成页面效果
function generatePage() {

    // 1.获取分页数据
    var pageInfo = getPageInfoRemote();

    // 2.填充表格
    fillTableBody(pageInfo);
}

// 远程访问服务器程序获取pageInfo
function getPageInfoRemote() {
    $.ajax({
        "url": "role/get/page",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pagSize": window.pageSize,
            "keyword": window.keyword
        },
        "dataType": "json",
        "success": function (response) {
            var pageInfo = response.data;
            fillTableBody(pageInfo);
        },
        "error": function (response) {
            layer.msg("请求失败，请及时联系运维人员！")
        }
    });
}

// 填充表格，显示分页数据
function fillTableBody(pageInfo) {

    // 清除 tbody 中旧数据
    $("#rolePageBody").empty();

    // 没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    // 判断pageInfo对象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜 索的数据！</td></tr>");
        return;
    }

    // 使用pageInfo的list属性填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {
        var role = pageInfo.list[i];
        var roleId = role.id;
        var roleName = role.name;
        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button id='" + roleId + "' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";

        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

        var removeBtn = "<button id=' " + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";
        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成页码导航条
function generateNavigator(pageInfo) {
    // 获取总记录数
    var totalRecord = pageInfo.total;

    // 声明其他相关属性
    var properties = {
        num_edge_entries: 3, // 边缘页数
        num_display_entries: 5, // 主体页数
        callback: paginationCallBack, // 点击页码之后的回调函数
        items_per_page: pageInfo.pageSize, // 每页显示条目数
        current_page: pageInfo.pageNum - 1, // 当前页数
        prev_text: "上一页",
        next_text: "下一页"
    };

    // 生成页码导航条
    $("#Pagination").pagination(totalRecord, properties);
}

// 翻页时的回调函数
function paginationCallBack(pageIndex, jQuery) {
    // 根据pageIndex计算得到pageNum
    window.pageNum = pageIndex + 1;

    generatePage();

    // 取消点击超链接的默认行为
    return false;
}