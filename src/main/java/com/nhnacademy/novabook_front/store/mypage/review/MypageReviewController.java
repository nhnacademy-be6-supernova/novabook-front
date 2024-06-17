package com.nhnacademy.novabook_front.store.mypage.review;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage/reviews")
@Controller
public class MypageReviewController {

    @GetMapping
    public String getReviewAll() {
        return "store/mypage/review/review_list";
    }
}
