package com.whw.crowd.mvc.controller;

import com.whw.crowd.service.api.AuthService;
import com.whw.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-30 20:17
 */
@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @ResponseBody
    @RequestMapping("/auth/update")
    private ResultEntity<String> authUpdate(@RequestBody Map<String, List<Integer>> map) {

        authService.updateAuth(map.get("roleId").get(0), map.get("authIdArray"));

        return ResultEntity.successWithoutData();
    }

}
