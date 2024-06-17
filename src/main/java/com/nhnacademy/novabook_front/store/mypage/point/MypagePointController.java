package com.nhnacademy.novabook_front.store.mypage.point;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage/points")
@Controller
public class MypagePointController {

    @GetMapping
    public String getPointAll() {
        return "store/mypage/point/point_list";
    }
}
