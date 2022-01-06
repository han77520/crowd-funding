package com.whw.crowd.controller;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.whw.crowd.api.MySQLRemoteService;
import com.whw.crowd.api.RedisRemoteService;
import com.whw.crowd.config.ShortMessageProperties;
import com.whw.crowd.entity.po.MemberPO;
import com.whw.crowd.entity.vo.MemberLoginVO;
import com.whw.crowd.entity.vo.MemberVO;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 16:05
 */
@Controller
public class MemberController {

    @Autowired
    private ShortMessageProperties smp;

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/auth/member/logout")
    public String logout(HttpSession httpSession){
        httpSession.invalidate();

        return "redirect:/";
    }

    @RequestMapping("/auth/member/do/login")
    public String login(@RequestParam("loginacct") String loginacct,
                        @RequestParam("userpswd") String userpswd,
                        ModelMap modelMap,
                        HttpSession session) {

        ResultEntity<MemberPO> resultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if (ResultEntity.FAILED.equals(resultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "member_login";
        }

        MemberPO memberPO = resultEntity.getData();

        if (null == memberPO) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member_login";
        }

        String userpswdDataBase = memberPO.getUserpswd();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        // 注意不能用Objects.equals()方法，因为每次的盐值都不一样
        if (!passwordEncoder.matches(userpswd,userpswdDataBase)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member_login";
        }

        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        System.out.println(memberLoginVO);
        session.setAttribute(CrowdConstant.ATTER_NAME_LOGIN_MEMBER,memberLoginVO);

        return "member_center";
    }


    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {

        // 1.获取用户输入的手机号
        String phoneNum = memberVO.getPhoneNum();
        String loginacct = memberVO.getLoginacct();

        ResultEntity<MemberPO> memberPOByLoginAcctRemote = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);


        // 2.拼Redis中存储验证码的键
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        // 3.从Redis中读取key对应的value
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKey(key);

        // 4.检查查询操作是否有效
        String result = resultEntity.getResult();

        if (ResultEntity.FAILED.equals(result)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());

            return "member_reg";
        }

        String redisCode = resultEntity.getData();
        if (redisCode == null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXIST);

            return "member_reg";
        }

        // 5.如果能查询到value，则比较表单的验证码和redis中的值
        String formCode = memberVO.getCode();

        if (!Objects.equals(formCode, redisCode)) {

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);

            return "member_reg";

        }

        // 6.如果验证码一致则删除
        redisRemoteService.removeRedisStringValueByKey(key);

        // 7.执行密码加密，保存
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswd = memberVO.getUserpswd();
        String encodeUserPwd = passwordEncoder.encode(userpswd);
        memberVO.setUserpswd(encodeUserPwd);

        // 8.执行保存
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);

        ResultEntity<String> saveMemberResultEntity = mySQLRemoteService.saveMemberRemote(memberPO);

        if (ResultEntity.FAILED.equals(saveMemberResultEntity.getResult())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveMemberResultEntity.getMessage());

            return "member_reg";
        }


        return "redirect:/auth/member/to/login/page";
    }

    @ResponseBody
    @RequestMapping("/auth/member/send/short/message")
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {

        // 1.发送验证码到手机
        ResultEntity<String> shortMessage = HttpUtils.sendShortMessage(phoneNum, smp.getAppCode(), smp.getHost(), smp.getMethod());

        // 2.判断短信发送结果
        if (ResultEntity.SUCCESS.equals(shortMessage.getResult())) {

            // 3. 发送成功，将验证码放入Redis中
            String code = shortMessage.getData();

            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 3000, TimeUnit.SECONDS);

            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
                return ResultEntity.successWithoutData();
            } else {
                return saveCodeResultEntity;
            }

        } else {
            return shortMessage;
        }

    }

}
