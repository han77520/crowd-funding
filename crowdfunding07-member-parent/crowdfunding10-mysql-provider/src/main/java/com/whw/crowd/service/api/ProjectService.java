package com.whw.crowd.service.api;

import com.whw.crowd.entity.vo.DetailProjectVO;
import com.whw.crowd.entity.vo.PortalTypeVO;
import com.whw.crowd.entity.vo.ProjectVO;

import java.util.List;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-07 21:21
 */
public interface ProjectService {

    void saveProject(ProjectVO projectVO, Integer memberId);

    List<PortalTypeVO> getPortalTypeVO();

    DetailProjectVO getDetailProjectVO(Integer projectId);
}
