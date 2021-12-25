package com.whw.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whw.crowd.entity.Admin;
import com.whw.crowd.entity.AdminExample;
import com.whw.crowd.exception.LoginAcctAleadyInUseException;
import com.whw.crowd.exception.LoginFailedException;
import com.whw.crowd.mapper.AdminMapper;
import com.whw.crowd.service.api.AdminService;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author 王瀚文
 * @Description:
 * @date 2021-12-18 19:00
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {

        // 1.密码加密
        String userPswd = admin.getUserPswd();
        userPswd= CrowdUtil.md5(userPswd);

        admin.setUserPswd(userPswd);

        // 2.生成创建时间
        admin.setCreateTime( new SimpleDateFormat("yyyy-MM-dd").format(new Date()) );

        try {
            adminMapper.insert(admin);
        }  catch (Exception e) {

            e.printStackTrace();

            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAleadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {

        List<Admin> admins = adminMapper.selectByExample(new AdminExample());

        return admins;
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

        // 1.根据登录账号查询Admin对象
        // ① 创建 AdminExample 对象
        AdminExample adminExample = new AdminExample();
        // ②创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // ③在criteria中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
        // ④调用 AdminMapper 的方法执行查询
        List<Admin> admins = adminMapper.selectByExample(adminExample);

        // 2.判断Admin对象是否为null
        if (admins == null || admins.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (admins.size() > 1 ) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = admins.get(0);

        // 3.如果Admin对象为null则抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4.将表单提交的明文进行加密操作
        String md5Pswd = admin.getUserPswd();

        // 5.对密码进行比较
        String userPswdDB = CrowdUtil.md5(userPswd);

        // 6.如果比较不一致则抛出异常
        if (!Objects.equals(md5Pswd,userPswdDB)) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 7.如果一致则返回 Admin 对象
        return admin;
    }

    @Override
    public int removeAdmin(Integer id) {
        int delete = adminMapper.deleteByPrimaryKey(id);
        return delete;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        // 1.调用PageHelper的静态方法开启分页功能
        // 这个分页开关充分体现了“非侵入式”设计，原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum,pageSize);

        // 2.执行查询,这里的查询结果是被PageHelper拦截器给拦截后的结果，本质是一个Page对象
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);

        // 3.将Page对象封装到PageInfo中
        return new PageInfo<>(admins);
    }



}
