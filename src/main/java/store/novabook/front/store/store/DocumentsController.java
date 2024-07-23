package store.novabook.front.store.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentsController {

	@GetMapping("/contact-us")
	public String contact() {
		return "store/documents/contact_us";
	}

	@GetMapping("/media")
	public String media() {
		return "store/documents/media";
	}

	@GetMapping("/features")
	public String featrues() {
		return "store/documents/features";
	}

	@GetMapping("/about-us")
	public String about() {
		return "store/documents/about_us";
	}

}
