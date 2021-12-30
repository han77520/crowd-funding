package com.whw.crowd.mvc.controller;

import com.whw.crowd.entity.Menu;
import com.whw.crowd.service.api.MenuService;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-27 8:22
 */
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 删除Menu对象
    @RequestMapping("/menu/remove")
    public ResultEntity<String> removeMenu(@RequestParam("id") Integer id) {

        menuService.removeMenu(id);

        return ResultEntity.successWithoutData();
    }

    // 更新Menu对象
    @RequestMapping("/menu/edit")
    public ResultEntity<String> editMenu(Menu menu) {

        menuService.updateMenu(menu);

        return ResultEntity.successWithoutData();
    }

    // 保存Menu对象
    @RequestMapping("/menu/save")
    public ResultEntity<String> saveMenu(Menu menu) {

        menuService.saveMenu(menu);

        return ResultEntity.successWithoutData();
    }


    // 此方法的时间复杂度为 n
    @RequestMapping("/menu/get/whole/tree")
    public ResultEntity<Menu> getWholeTreeNew() {

        // 查询全部的Menu对象
        List<Menu> menuList = menuService.getAll();

        // 来存储根节点
        Menu root = null;

        // 创建map对象，用于存储所有节点，方便后面通过Pid来直接获取父节点
        Map<Integer, Menu> menuMap = new HashMap<>();

        // 往Mpa中添加元素，此次添加的Menu对象代表的是父节点
        for (Menu menu : menuList) {
            menuMap.put(menu.getId(), menu);
        }

        // 此次遍历 menuList中的Menu对象代表的是子节点
        for (Menu sonMenu : menuList) {

            // 如果子节点的父节点为null,则说明此Menu对象为父节点
            Integer pid = sonMenu.getPid();

            if (pid == null) {
                // 将父节点赋值为root
                root = sonMenu;

                // 明确当前节点是父节点后就可以直接跳出本次循环
                continue;
            }

            // 根据节点的Pid，在Map集合中获取对应的父节点
            Menu fatherMenu = menuMap.get(pid);

            // 将子节点添加到对应的父节点中
            fatherMenu.getChildren().add(sonMenu);

        }
        // 返回根节点
        return ResultEntity.successWithData(root);
    }

    // 此方法时间复杂度太大，为 n²，用上面的方法代替
    public ResultEntity<Menu> getWholeTree() {

        // 查询全部的Menu对象
        List<Menu> menuList = menuService.getAll();

        // 来存储根节点
        Menu root = null;

        for (Menu menu : menuList) {
            // 获取该节点的父节点
            Integer Pid = menu.getPid();
            // 判断父节点是否为null,如果是，则说明此Menu就是父节点
            if (Pid == null) {
                // 将找到的Menu对象赋值给root
                root = menu;
                // 结束本次循环
                continue;
            }

            // 如果Pid 不为null。则说明有父节点，此循环目的是为了建立Menu对象之间的父子关系
            for (Menu maybeFather : menuList) {
                // 获取id
                Integer id = maybeFather.getId();

                // 如果id和Pid相等，那么就将 menu加入到maybeFather中
                if (Objects.equals(id, Pid)) {
                    maybeFather.getChildren().add(menu);
                    // 当前 Menu 创建好父子关系后直接跳出该循环
                    break;
                }
            }
        }

        return ResultEntity.successWithData(root);
    }
}
