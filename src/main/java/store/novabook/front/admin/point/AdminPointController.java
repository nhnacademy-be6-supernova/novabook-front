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
import store.novabook.front.api.PageResponse;
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
    public String getPointForm(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = PAGE_SIZE) int size, Model model) {


        PageResponse<GetPointPolicyResponse> pointPolicyAllPage = pointPolicyService.getPointPolicyAllPage(page-1, size);

        //현재 페이지의 데이터 리스트
        model.addAttribute("pointPolicies",pointPolicyAllPage.getData());
        //현재 페이지 번호
        model.addAttribute("currentPage", page);
        //전체 페이지 수
        model.addAttribute("pageSize", pointPolicyAllPage.getTotalPageCount());

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
