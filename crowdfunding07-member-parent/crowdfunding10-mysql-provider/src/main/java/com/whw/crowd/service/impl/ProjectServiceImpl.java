package com.whw.crowd.service.impl;

import com.whw.crowd.mapper.ProjectPOMapper;
import com.whw.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-07 21:21
 */
@Service
@Transactional(readOnly = true)
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;
}
