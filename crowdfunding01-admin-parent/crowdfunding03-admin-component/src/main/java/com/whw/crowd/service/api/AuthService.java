package com.whw.crowd.service.api;

import com.whw.crowd.entity.Auth;

import java.util.List;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-30 20:16
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthId(Integer roleId);

    void updateAuth(Integer roleId ,List<Integer> authIdArray);

    List<String> getAssignedAuthNameByAdminId(Integer adminId);
}
