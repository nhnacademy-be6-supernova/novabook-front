package com.nhnacademy.novabook_front.admin.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminIndexController {

    @GetMapping
    public String index() {
        return "/admin/admin_index";
    }

}
