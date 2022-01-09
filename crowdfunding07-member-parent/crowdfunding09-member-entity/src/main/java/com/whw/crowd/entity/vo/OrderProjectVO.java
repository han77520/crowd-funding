package com.whw.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-09 17:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProjectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String projectName;

    private String launchName;

    private String returnContent;

    private Integer returnCount;

    private Integer supportPrice;

    private Integer freight;

    private Integer orderId;

    private Integer signalPurchase;

    private Integer purchase;

}
