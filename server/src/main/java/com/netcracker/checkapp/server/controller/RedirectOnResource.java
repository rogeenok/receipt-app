package com.netcracker.checkapp.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectOnResource {

    @RequestMapping(value = "/{path:[^\\.]*}/**")
    public String redirect() {
        return "forward:/";
    }

}
