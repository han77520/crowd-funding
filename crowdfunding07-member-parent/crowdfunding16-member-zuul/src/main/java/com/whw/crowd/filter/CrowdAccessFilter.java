package com.whw.crowd.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.whw.crowd.util.AccessPassResources;
import com.whw.crowd.util.CrowdConstant;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-07 8:21
 */
@Component
public class CrowdAccessFilter extends ZuulFilter {

    @Override
    public String filterType() {
        // 返回pre意思是在目标微服务前执行过滤
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {

        // 1.获取获取RequestContext对象
        RequestContext currentContext = RequestContext.getCurrentContext();

        // 2.通过RequestContext对象获取当前请求对象(框架是通过ThreadLocal来获取事先绑定的Request对象)
        HttpServletRequest request = currentContext.getRequest();

        // 3.获取servletPath的值
        String servletPath = request.getServletPath();

        // 4.根据servletPath判断当前是否可以直接放行
        boolean containResult = AccessPassResources.PASS_RES_SET.contains(servletPath);

        if (containResult) {
            return false;
        }

        // 5.判断当前请求是否是静态资源
        boolean staticResource = AccessPassResources.judgeCurrentServletPathWetherStaticResource(servletPath);
        if (staticResource) {
            return false;
        }

        return !staticResource;
    }

    @Override
    public Object run() throws ZuulException {

        // 1.获取获取RequestContext对象
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        // 2.获取当前的session对象
        HttpSession session = request.getSession();

        // 3.尝试从session对象中获取已经登录的用户
        Object loginMember = session.getAttribute(CrowdConstant.ATTER_NAME_LOGIN_MEMBER);

        // 4.判断loginMember是否为空
        if (null == loginMember) {
            HttpServletResponse response = currentContext.getResponse();

            session.setAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
            try {
                response.sendRedirect("/auth/member/to/login/page");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
