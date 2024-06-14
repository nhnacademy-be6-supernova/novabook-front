package com.nhnacademy.viewtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class MyPageController {
    @GetMapping("/mypage")
    public String home() {

        return "mypage";
    }

}
