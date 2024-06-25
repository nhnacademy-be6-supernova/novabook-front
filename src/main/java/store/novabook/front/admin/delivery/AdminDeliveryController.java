package store.novabook.front.admin.delivery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/deliverys/delivery")
@Controller
public class AdminDeliveryController {

    @GetMapping("/form")
    public String getDeliveryForm() {
        return "admin/delivery/deliveryfee_form";
    }

    @PostMapping("/{deliveryId}/update")
    public String updateDelivery(@PathVariable Long deliveryId) {
        return "";
    }
}
