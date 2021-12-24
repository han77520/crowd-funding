package com.whw.crowd.exception;

/**
 * @author 王瀚文
 * @Description: 表示用户没有登录就访问被保护资源时抛出的异常
 * @date 2021-12-21 20:01
 */
public class AccessForbiddenException extends RuntimeException{

    public AccessForbiddenException() {
        super();
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    protected AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
