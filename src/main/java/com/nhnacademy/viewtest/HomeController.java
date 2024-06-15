package com.nhnacademy.viewtest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String test() {
        return "main_page";
    }

    @GetMapping("/bookDetail")
    public String test1() {
        return "book_detail";
    }


    @GetMapping("/adminBookRegister")
    public String test3() {
        return "admin_book_register";
    }

    @GetMapping("/bookSearch")
    public String bookSearch() {
        return "book_search";
    }
}
