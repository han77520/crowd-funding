package com.whw.crowd.service.impl;

import com.whw.crowd.entity.Auth;
import com.whw.crowd.entity.AuthExample;
import com.whw.crowd.mapper.AuthMapper;
import com.whw.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-30 20:16
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        List<Auth> authList = authMapper.selectByExample(new AuthExample());

        return authList;
    }

    @Override
    public List<Integer> getAssignedAuthId(Integer roleId) {

        List<Integer> authIdList = authMapper.selectAssignedAuthId(roleId);

        return authIdList;
    }

    @Override
    public void updateAuth(Integer roleId, List<Integer> authIdArray) {

        authMapper.deleteAuthIdByRoleId(roleId);

        if (authIdArray != null && authIdArray.size() > 0) {
            authMapper.insertAuthIdByRoleId(roleId, authIdArray);
        }

    }
}
