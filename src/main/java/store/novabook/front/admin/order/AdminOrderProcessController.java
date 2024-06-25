package store.novabook.front.admin.order;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/orders")
@Controller
public class AdminOrderProcessController {

    @GetMapping
    public String getOrderAll() {
        return "admin/order/order_process_list";
    }

    @PostMapping("order/{orderId}/update")
    public String updateOrder(@PathVariable Long orderId) {
        return "";
    }

}
