package com.whw.crowd.controller;

import com.whw.crowd.entity.vo.AddressVO;
import com.whw.crowd.entity.vo.OrderProjectVO;
import com.whw.crowd.service.api.OrderService;
import com.whw.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-09 17:36
 */
@RestController
public class OrderProviderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/save/address/vo/remote")
    public ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO){
        try {
            orderService.saveAddress(addressVO);

            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/address/vo/remote")
    public ResultEntity<List<AddressVO>> getAddressVORemote(Integer memberId) {


        try {
            List<AddressVO> addressVOList = orderService.getAddressVOList(memberId);

            return ResultEntity.successWithData(addressVOList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    @RequestMapping("/get/order/project/vo/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectVoRemote(@RequestParam("projectId") Integer projectId,
                                                         @RequestParam("returnId") Integer returnId) {

        try {
            OrderProjectVO orderProjectVO = orderService.getOrderProjectVO(projectId, returnId);

            return ResultEntity.successWithData(orderProjectVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

}
