// 生成树形结构
function generateTree(){
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
}

//在鼠标离开节点范围时删除按钮组
function myRemoveHoverDom(treeId, treeNode) {
    var btnGroupId = treeNode.tId + "_btnGrp";

    $("#" + btnGroupId).remove();
}

// 在鼠标垫移入节点范围时添加按钮组
function myAddHoverDom(treeId, treeNode) {
    // 按钮组的标签结构是 <span> <a> <i></i> </a> <a> <i></i> </a> </span>
    // 按钮出现的位置：节点中 treeDemo_n_a超链接的后面，先找到附着按钮的超链接
    var anchorId = treeNode.tId + "_a";

    // 给span设置有规律的id，为了删除节点后面追加的<span>标签
    var btnGroupId = treeNode.tId + "_btnGrp";

    // 判断是否已经存在了按钮组
    if ($("#" + btnGroupId).length > 0) {
        return;
    }

    // 准备各个按钮的HTML标签
    var addBtn = "<a id='" + treeNode.id + "' class='addBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;'  title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg '></i></a>";
    var removeBtn = "<a id='" + treeNode.id + "' class='removeBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;'  title=' 删 除 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg '></i></a>";
    var editBtn = "<a id='" + treeNode.id + "' class='editBtn btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;'  title=' 修 改 节 点 '>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg '></i></a>";

    // 获取当前节点的级别
    var level = treeNode.level;

    // 声明变量存储拼装好的按钮代码
    var btnHTML = "";

    // 判断当前节点的级别
    // 级别为1，则为根节点
    if (level == 0) {
        btnHTML = addBtn;
    }

    // 级别为1，则为分支节点
    if (level == 1) {
        // 添加和修改按钮
        btnHTML = addBtn + " " + editBtn;

        // 获取当前节点的子节点数量
        var length = treeNode.children.length;

        // 如果没有子节点，则可以删除
        if (length == 0) {
            btnHTML = btnHTML + removeBtn;
        }

    }

    // 级别为2，则为叶子节点，可以修改和删除
    if (level == 2) {
        btnHTML = editBtn + " " + removeBtn;
    }

    // 在附着便签后追加需要的标签
    $("#" + anchorId).after("<span id='" + btnGroupId + "'>" + btnHTML + "</span>")
}

// 修改默认d 图标
// treeId是整个树形结构附着的ul标签的id，treeNode是当前树形结点的全部数据
function myAddDiyDom(treeId, treeNode) {
    // console.log(treeId,treeNode)

    // 根据id的生成规则，拼接出span标签的id
    /*
        例子：treeDemo_7_ico
        生成id的规则：ul标签的id_当前节点的序号_功能
        提示：treeNode 的 tId属性就是 "ul标签的id_当前节点的序号"，也就是 tId_ico
    */
    var spanId = treeNode.tId + "_ico";

    // 根据控制图标的span标签的id找到这个span标签
    // 删除旧的class
    // 添加新的class
    $("#" + spanId).removeClass().addClass(treeNode.icon);

}