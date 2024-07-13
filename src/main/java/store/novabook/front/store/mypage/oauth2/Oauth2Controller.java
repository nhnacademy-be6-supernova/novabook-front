package store.novabook.front.store.mypage.oauth2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/mypage/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

	@GetMapping
	public String oauth2() {
		return "store/mypage/oauth2/oauth2";
	}
}
