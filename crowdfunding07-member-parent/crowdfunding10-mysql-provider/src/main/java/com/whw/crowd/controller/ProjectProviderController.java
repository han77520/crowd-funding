package com.whw.crowd.controller;

import com.whw.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-07 21:21
 */
@RestController
public class ProjectProviderController {

    @Autowired
    private ProjectService projectProviderService;


}
