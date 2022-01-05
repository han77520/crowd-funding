package com.whw.crowd.service.impl;

import com.whw.crowd.entity.po.MemberPO;
import com.whw.crowd.entity.po.MemberPOExample;
import com.whw.crowd.mapper.MemberPOMapper;
import com.whw.crowd.service.api.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 8:51
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Override
    public MemberPO getMemberPOByLoginacct(String loginAcct) {

        MemberPOExample example = new MemberPOExample();

        MemberPOExample.Criteria criteria = example.createCriteria();

        criteria.andLoginacctEqualTo(loginAcct);

        return memberPOMapper.selectByExample(example).get(0);
    }
}
