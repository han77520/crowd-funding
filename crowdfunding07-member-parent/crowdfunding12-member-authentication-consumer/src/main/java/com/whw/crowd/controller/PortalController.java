package com.whw.crowd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Han77
 * @Description:
 * @date 2022-01-05 12:56
 */
@Controller
public class PortalController {

    @RequestMapping("/")
    public String showPortalPage() {
        return "portal";
    }
}
