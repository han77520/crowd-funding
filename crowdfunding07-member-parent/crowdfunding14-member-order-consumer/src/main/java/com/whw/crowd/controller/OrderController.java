package com.whw.crowd.controller;

import com.whw.crowd.api.MySQLRemoteService;
import com.whw.crowd.entity.vo.AddressVO;
import com.whw.crowd.entity.vo.MemberLoginVO;
import com.whw.crowd.entity.vo.OrderProjectVO;
import com.whw.crowd.util.CrowdConstant;
import com.whw.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-09 17:04
 */
@Controller
public class OrderController {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @RequestMapping("/save/address")
    public String saveAddress(AddressVO addressVO,HttpSession session){

        ResultEntity<String> resultEntity = mySQLRemoteService.saveAddressRemote(addressVO);

        logger.debug("地址保存处理结果" + resultEntity.getResult());

        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        Integer returnCount = orderProjectVO.getReturnCount();

        return "redirect:http://www.crowd.com/order/confirm/order/" + returnCount;
    }

    @RequestMapping("/confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount") Integer returnCount,
                                       HttpSession session){

        OrderProjectVO orderProjectVO = (OrderProjectVO) session.getAttribute("orderProjectVO");

        orderProjectVO.setReturnCount(returnCount);

        session.setAttribute("orderProjectVO",orderProjectVO);

        MemberLoginVO memberLoginVO = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTER_NAME_LOGIN_MEMBER);

        ResultEntity<List<AddressVO>> resultEntity = mySQLRemoteService.getAddressVORemote(memberLoginVO.getId());


        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            List<AddressVO> data = resultEntity.getData();
            session.setAttribute("addressVOList",data);
        }

        return "confirm_order";
    }

    @RequestMapping("/confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfirmInfo(@PathVariable("projectId") Integer projectId,
                                        @PathVariable("returnId") Integer returnId,
                                        HttpSession session) {

        ResultEntity<OrderProjectVO> resultEntity = mySQLRemoteService.getOrderProjectVoRemote(projectId, returnId);

        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())) {

            OrderProjectVO orderProjectVO = resultEntity.getData();

            session.setAttribute("orderProjectVO", orderProjectVO);
        }

        return "confirm_return";
    }
}
