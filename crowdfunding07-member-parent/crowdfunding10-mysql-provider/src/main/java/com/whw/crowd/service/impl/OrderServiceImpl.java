package com.whw.crowd.service.impl;

import com.whw.crowd.entity.po.AddressPO;
import com.whw.crowd.entity.po.AddressPOExample;
import com.whw.crowd.entity.po.OrderPO;
import com.whw.crowd.entity.po.OrderProjectPO;
import com.whw.crowd.entity.vo.AddressVO;
import com.whw.crowd.entity.vo.OrderProjectVO;
import com.whw.crowd.entity.vo.OrderVO;
import com.whw.crowd.mapper.AddressPOMapper;
import com.whw.crowd.mapper.OrderPOMapper;
import com.whw.crowd.mapper.OrderProjectPOMapper;
import com.whw.crowd.service.api.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-09 17:37
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderProjectPOMapper orderProjectPOMapper;

    @Autowired
    private OrderPOMapper orderPOMapper;

    @Autowired
    private AddressPOMapper addressPOMapper;

    @Override
    public OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId) {

        return orderProjectPOMapper.selectOrderProjectVO(returnId);
    }

    @Override
    public List<AddressVO> getAddressVOList(Integer memberId) {

        AddressPOExample example = new AddressPOExample();
        AddressPOExample.Criteria criteria = example.createCriteria();
        criteria.andMemberIdEqualTo(memberId);

        List<AddressPO> addressPOS = addressPOMapper.selectByExample(example);

        List<AddressVO> addressVOS = new ArrayList<>();

        for (AddressPO addressPO : addressPOS) {
            AddressVO addressVO = new AddressVO();
            BeanUtils.copyProperties(addressPO,addressVO);
            addressVOS.add(addressVO);
        }
        return addressVOS;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveAddress(AddressVO addressVO) {

        AddressPO addressPO = new AddressPO();

        BeanUtils.copyProperties(addressVO,addressPO);

        addressPOMapper.insert(addressPO);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveOrder(OrderVO orderVO) {

        OrderPO orderPO = new OrderPO();

        BeanUtils.copyProperties(orderVO,orderPO);

        OrderProjectPO orderProjectPO = new OrderProjectPO();

        BeanUtils.copyProperties(orderVO.getOrderProjectVO(),orderProjectPO);

        orderPOMapper.insert(orderPO);

        Integer orderPOId = orderPO.getId();

        orderProjectPO.setOrderId(orderPOId);

        orderProjectPOMapper.insert(orderProjectPO);
    }
}
