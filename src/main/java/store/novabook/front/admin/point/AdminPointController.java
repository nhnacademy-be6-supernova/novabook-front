package store.novabook.front.admin.point;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.point.dto.request.CreatePointPolicyRequest;
import store.novabook.front.api.point.dto.response.GetPointPolicyResponse;
import store.novabook.front.api.point.service.PointPolicyService;

@RequestMapping("/admin/points/point")
@Controller
@RequiredArgsConstructor
public class AdminPointController {
	private final PointPolicyService pointPolicyService;
	private static final String PAGE_SIZE = "10";

	@GetMapping("/form")
	public String getPointForm(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = PAGE_SIZE) int size, Model model) {
		PageResponse<GetPointPolicyResponse> pointPolicyAllPage = pointPolicyService.getPointPolicyAllPage(page,
			size);

		model.addAttribute("pointPolicies", pointPolicyAllPage);


		return "admin/point/point_form";
	}

	@PostMapping
	public String createPointPolicy(@ModelAttribute CreatePointPolicyRequest request) {
		pointPolicyService.createPointPolicy(request);
		return "redirect:/admin/points/point/form";
	}

	@PostMapping("/{pointId}/update")
	public String updatePoinr(@PathVariable Long pointId) {
		return "";
	}
}
