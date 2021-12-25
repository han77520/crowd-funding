package com.whw.crowd.exception;

/**
 * @author 王瀚文
 * @Description: 保存或更新Admin时检测到登录账号重复时抛出的异常
 * @date 2021-12-25 11:04
 */
public class LoginAcctAleadyInUseException extends RuntimeException{
    public LoginAcctAleadyInUseException() {
        super();
    }

    public LoginAcctAleadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAleadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAleadyInUseException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctAleadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
