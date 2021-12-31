package com.whw.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Role;

import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-25 19:40
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRole(List<Integer> roleIds);

    List<Role> getAssignedRole(Integer id);

    List<Role> getUnAssignedRole(Integer id);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleList);
}
