package com.whw.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-08 20:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalTypeVO {

    private Integer id;

    private String name;

    private String remark;

    private List<PortalProjectVO> portalProjectVOList;
}
