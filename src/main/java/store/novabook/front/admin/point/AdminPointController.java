package store.novabook.front.admin.point;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/points/point")
@Controller
public class AdminPointController {

    @GetMapping("/form")
    public String getPointForm() {
        return "admin/point/point_form";
    }

    @PostMapping("/{pointId}/update")
    public String updatePoinr(@PathVariable Long pointId) {
        return "";
    }
}
