package store.novabook.front.admin.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.admin.service.AdminService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminIndexController {

	private final AdminService adminService;

	@GetMapping
	public String index() {
		adminService.admin();
		return "admin/admin_index";
	}
}
