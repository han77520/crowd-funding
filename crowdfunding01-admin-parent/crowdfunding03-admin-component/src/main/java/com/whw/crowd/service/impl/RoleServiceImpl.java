package com.whw.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Role;
import com.whw.crowd.entity.RoleExample;
import com.whw.crowd.mapper.RoleMapper;
import com.whw.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-25 19:40
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 1.开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        // 2.查询
        List<Role> roles = roleMapper.SelectRoleByKeyword(keyword);

        return new PageInfo<>(roles);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void deleteRole(List<Integer> roleIds) {
        RoleExample roleExample = new RoleExample();

        RoleExample.Criteria criteria = roleExample.createCriteria();

        criteria.andIdIn(roleIds);

        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssignedRole(Integer id) {
        return roleMapper.selectAssignedRole(id);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer id) {
        return roleMapper.selectUnAssignedRole(id);
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleList) {
        roleMapper.deleteOldRelationship(adminId);

       if (roleList != null || roleList.size() > 0) {
           roleMapper.insertNewRelationship(adminId,roleList);
       }
    }
}
