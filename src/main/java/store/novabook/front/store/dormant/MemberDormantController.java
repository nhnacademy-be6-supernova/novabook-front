package store.novabook.front.store.dormant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dormant")
public class MemberDormantController {

	@GetMapping
	public String memberDormant() {
		return "store/dormant/dormant";
	}
}
