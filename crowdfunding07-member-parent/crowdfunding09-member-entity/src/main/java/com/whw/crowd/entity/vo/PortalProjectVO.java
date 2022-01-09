package com.whw.crowd.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-08 20:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortalProjectVO {

    private Integer projectId;

    private String projectName;

    private String headerPicturePath;

    private Integer money;

    private String deployDate;

    private Integer percentage;

    private Integer supporter;
}
