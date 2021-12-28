package com.whw.crowd.entity;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    // 主键
    private Integer id;
    // 父节点id
    private Integer pid;
    //节点名称
    private String name;
    // 点击菜单时要跳转的路径
    private String url;
    // 节点图标的样式
    private String icon;
    // 存储子节点，初始化为了避免空指针异常
    private List<Menu> children = new ArrayList<>();
    // 控制节点是否为默认打开节点，true为打开
    private boolean open = true;

    public Menu() {
    }

    public Menu(Integer id, Integer pid, String name, String url, List<Menu> children, String icon, boolean open) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.url = url;
        this.children = children;
        this.icon = icon;
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}