package com.whw.crowd.service.api;

import com.whw.crowd.entity.vo.AddressVO;
import com.whw.crowd.entity.vo.OrderProjectVO;

import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-09 17:37
 */
public interface OrderService {

    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer memberId);

    void saveAddress(AddressVO addressVO);
}
