package com.whw.crowd.controller;

import com.whw.crowd.entity.po.MemberPO;
import com.whw.crowd.service.api.MemberService;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 8:42
 */
@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("/get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginAcct){

        try {
            MemberPO memberPO = memberService.getMemberPOByLoginacct(loginAcct);
            return ResultEntity.successWithData(memberPO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }


}
