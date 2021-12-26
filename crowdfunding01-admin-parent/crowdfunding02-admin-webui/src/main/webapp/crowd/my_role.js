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
        var checkboxTd = "<td><input type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";
        var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
        var pencilBtn = "<button type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
        var removeBtn = "<button type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";

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