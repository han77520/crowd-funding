package com.whw.crowd.service.api;

import com.whw.crowd.entity.po.MemberPO;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 8:51
 */
public interface MemberService {

    MemberPO getMemberPOByLoginacct(String loginAcct);

    void saveMember(MemberPO memberPO);
}
