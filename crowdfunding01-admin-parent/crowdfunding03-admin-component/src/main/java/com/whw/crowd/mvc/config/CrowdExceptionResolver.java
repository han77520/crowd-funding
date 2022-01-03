package com.whw.crowd.mvc.config;

import com.google.gson.Gson;
import com.whw.crowd.exception.AccessForbiddenException;
import com.whw.crowd.exception.LoginAcctAleadyInUseException;
import com.whw.crowd.exception.LoginAcctAleadyInUseForUpdateException;
import com.whw.crowd.exception.LoginFailedException;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.CrowdUtil;
import com.whw.crowd.util.ResultEntity;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-19 14:37
 */
@ControllerAdvice
public class CrowdExceptionResolver {

    /**
     * 处理空指针异常(NPE)
     * @param exception 程序捕获到的异常
     * @param request 当前请求
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = commonResolver("system-errors", exception, request, response);

        return modelAndView;
    }

    @ExceptionHandler(value = LoginAcctAleadyInUseException.class)
    public ModelAndView resolveLoginAcctAleadyInUseException(LoginAcctAleadyInUseException exception,
                                                HttpServletRequest request,
                                                HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = commonResolver("admin_add", exception, request, response);

        return modelAndView;
    }

    @ExceptionHandler(value = LoginAcctAleadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAleadyInUseForUpdateException(LoginAcctAleadyInUseForUpdateException exception,
                                                             HttpServletRequest request,
                                                             HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = commonResolver("system-errors", exception, request, response);

        return modelAndView;
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView resolveRuntimeException(RuntimeException exception,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = commonResolver("system-errors", exception, request, response);

        return modelAndView;
    }

    @ExceptionHandler(value = {Exception.class})
    public ModelAndView resolveException(Exception exception,
                                         HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        ModelAndView modelAndView = commonResolver("admin_login", exception, request, response);

        return modelAndView;
    }


    /**
     * 抽取处理异常的公共部分
     * @param viewName 处理完异常之后跳转到指定的页面
     * @param exception 出现的异常
     * @param request 当前的请求
     * @param response 当前的响应
     * @return
     */
    private ModelAndView commonResolver(String viewName,Exception exception,
                                        HttpServletRequest request,
                                        HttpServletResponse response){

        //判断当前请求类型
        boolean judgeResult = CrowdUtil.isAjaxRequestType(request);

        if (judgeResult) {
            ResultEntity<Object> failed = ResultEntity.failed(exception.getMessage());

            //将 ResultEntity 对象转为json字符串
            Gson gson = new Gson();
            String failedGson = gson.toJson(failed);

            try {
                response.getWriter().write(failedGson);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(CrowdConstant.ATTER_NAME_EXCEPTION,exception);
        modelAndView.setViewName(viewName);

        return modelAndView;
    }
}
