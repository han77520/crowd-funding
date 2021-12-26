package com.whw.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Role;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-25 19:40
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);
}
