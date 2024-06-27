package store.novabook.front.store.mypage.likebook;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mypage/likebooks")
@Controller
public class MypageLikeBookController {

    @GetMapping
    public String getLikeBookAll() {
        return "store/mypage/likebook/like_book_list";
    }
}
