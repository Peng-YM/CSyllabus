package com.peng1m.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String indexPage(){
        return "index";
    }

    @GetMapping("registered")
    public String registered(){
        return "registered";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
