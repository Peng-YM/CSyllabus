package com.peng1m.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/graph")
    public String showGraph(){
        return "graph";
    }

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

}
