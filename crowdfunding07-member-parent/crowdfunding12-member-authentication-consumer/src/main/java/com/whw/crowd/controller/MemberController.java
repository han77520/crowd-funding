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


    @RequestMapping("/member/my/crowd")
    public String memberMyCrowd(){
        return "member_crowd";
    }

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

        // ???????????????Objects.equals()??????????????????????????????????????????
        if (!passwordEncoder.matches(userpswd,userpswdDataBase)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member_login";
        }

        MemberLoginVO memberLoginVO = new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());
        System.out.println(memberLoginVO);
        session.setAttribute(CrowdConstant.ATTER_NAME_LOGIN_MEMBER,memberLoginVO);

        return "redirect:/auth/member/to/center/page";
    }


    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {

        // 1.??????????????????????????????
        String phoneNum = memberVO.getPhoneNum();
        String loginacct = memberVO.getLoginacct();

        ResultEntity<MemberPO> memberPOByLoginAcctRemote = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);


        // 2.???Redis????????????????????????
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;

        // 3.???Redis?????????key?????????value
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKey(key);

        // 4.??????????????????????????????
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

        // 5.??????????????????value?????????????????????????????????redis?????????
        String formCode = memberVO.getCode();

        if (!Objects.equals(formCode, redisCode)) {

            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);

            return "member_reg";

        }

        // 6.??????????????????????????????
        redisRemoteService.removeRedisStringValueByKey(key);

        // 7.???????????????????????????
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String userpswd = memberVO.getUserpswd();
        String encodeUserPwd = passwordEncoder.encode(userpswd);
        memberVO.setUserpswd(encodeUserPwd);

        // 8.????????????
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

        // 1.????????????????????????
        ResultEntity<String> shortMessage = HttpUtils.sendShortMessage(phoneNum, smp.getAppCode(), smp.getHost(), smp.getMethod());

        // 2.????????????????????????
        if (ResultEntity.SUCCESS.equals(shortMessage.getResult())) {

            // 3. ?????????????????????????????????Redis???
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
