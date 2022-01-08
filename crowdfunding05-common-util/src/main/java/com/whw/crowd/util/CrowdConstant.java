package com.whw.crowd.util;

/**
 * @author 王瀚文
 * @Description: 定义项目中需要的常量，防止引用的时候写错
 * @date 2021-12-19 15:20
 */
public class CrowdConstant {

    public static final String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误,请重新输入！";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用了";
    public static final String MESSAGE_ACCESS_FORBIDEN = "请登录以后在访问！";
    public static final String MESSAGE_STRING_INVALIDATE = "非法字符串，请重新输入！";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误，登录账号不唯一！";
    public static final String MESSAGE_CODE_NOT_EXIST = "验证码已过期！请检查手机号是否正确或重新发送";
    public static final String MESSAGE_HEADER_PIC_UPLOAD_FAILED = "图片上传失败！";
    public static final String MESSAGE_HEADER_PIC_EMPTY = "头图不可为空！";
    public static final String MESSAGE_DETAIL_PIC_EMPTY = "详情图不可为空！";
    public static final String MESSAGE_CODE_INVALID = "验证码错误！";
    public static final String MESSAGE_ACCESS_DENIED = "抱歉！ 您无权限";
    public static final String MESSAGE_DETAIL_PIC_UPLOAD_FAILED = "详情图上传失败，请重新上传！";
    public static final String MESSAGE_TEMPLE_PROJECT_MISSING = "临时存储的Project丢失";

    public static final String DELETE_ERROR = "不能删除当前登录用户，防止下次不能登录！";

    public static final String ATTER_NAME_EXCEPTION = "exception";
    public static final String ATTER_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTER_NAME_LOGIN_MEMBER = "loginMember";
    public static final String ATTER_NAME_EDIT_ADMIN= "editAdmin";

    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_NAME_MESSAGE = "message";
    public static final String ATTR_NAME_TEMPLE_PROJECT = "tempPorject";

    public static final String REDIS_CODE_PREFIX = "redis_code_prefix_";
}
