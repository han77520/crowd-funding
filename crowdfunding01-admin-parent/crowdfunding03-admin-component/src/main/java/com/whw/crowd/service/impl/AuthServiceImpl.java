package com.whw.crowd.service.impl;

import com.whw.crowd.mapper.AuthMapper;
import com.whw.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-30 20:16
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;
}
