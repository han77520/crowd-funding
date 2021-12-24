package com.whw.crowd.mvc.interceptor;

import com.whw.crowd.entity.Admin;
import com.whw.crowd.exception.AccessForbiddenException;
import com.whw.crowd.util.CrowdConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-21 19:55
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // 1.通过request对象获取Session对象
        HttpSession session = request.getSession();

        // 2.尝试从Session域中获取Admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTER_NAME_LOGIN_ADMIN);

        if (request.getRequestURI().contains("/admin/login") ||
                request.getRequestURI().contains("/admin/do/login") ||
                request.getRequestURI().contains("/admin/do/logout")) {
            return true;
        }


        // 3.判断admin对象是否为空
        if (admin == null) {

            // 4.抛出异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);

        }

        // 5.如果Admin对象不为null，则返回true放行
        return true;
    }

}

