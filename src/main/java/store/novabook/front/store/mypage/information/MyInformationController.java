package store.novabook.front.store.mypage.information;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.member.member.service.MemberService;

@Controller
@RequestMapping("/mypage/information")
@RequiredArgsConstructor
public class MyInformationController {
	private final MemberService memberService;

	@GetMapping
	public String getMyInformation() {
		return "store/mypage/information/my_information";
	}

}
