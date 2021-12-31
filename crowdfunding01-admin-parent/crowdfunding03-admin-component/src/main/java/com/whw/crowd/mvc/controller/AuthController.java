package com.whw.crowd.mvc.controller;

import com.whw.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-30 20:17
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;


}
