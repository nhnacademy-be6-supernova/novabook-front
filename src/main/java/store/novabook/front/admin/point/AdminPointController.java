package store.novabook.front.admin.point;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import store.novabook.front.api.point.dto.request.CreatePointPolicyRequest;
import store.novabook.front.api.point.service.PointPolicyService;
import store.novabook.front.common.security.aop.CurrentMembers;

@RequestMapping("/admin/points/point")
@Controller
@RequiredArgsConstructor
public class AdminPointController {
	private final PointPolicyService pointPolicyService;
	private static final String PAGE_SIZE = "10";

	@GetMapping("/form")
	public String getPointForm(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = PAGE_SIZE) int size, Model model, HttpServletRequest
		request, @CurrentMembers Long id) {
		model.addAttribute("pointPolicies", pointPolicyService.getPointPolicyAllPage(page,
			size));
		return "admin/point/point_form";
	}

	@PostMapping
	public String createPointPolicy(@ModelAttribute CreatePointPolicyRequest request) {
		pointPolicyService.createPointPolicy(request);
		return "redirect:/admin/points/point/form";
	}
}
