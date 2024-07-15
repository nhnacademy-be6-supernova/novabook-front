package store.novabook.front.store.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreIndexController {

    @GetMapping
    public String getCategory() {
        return "store/store_index";
    }
}