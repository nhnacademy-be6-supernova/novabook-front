package store.novabook.front.store.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/contact-us")
@RequiredArgsConstructor
public class ContactController {

	@GetMapping
	public String contact() {
		return "store/contact_us";
	}

}
