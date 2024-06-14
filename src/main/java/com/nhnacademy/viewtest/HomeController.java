package com.nhnacademy.viewtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "register";
    }

    @GetMapping("/index")
    public String test() {
        return "템플릿";
    }
}
