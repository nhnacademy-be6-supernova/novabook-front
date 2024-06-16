package com.nhnacademy.novabook_front.store.mypage.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage")
@Controller
public class MypageController {

    @GetMapping
    public String getMypage() {
        return "/store/mypage/mypage_index";
    }
}
