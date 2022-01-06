package com.whw.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-06 9:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO {

    private Integer id;

    private String username;

    private String email;


}
