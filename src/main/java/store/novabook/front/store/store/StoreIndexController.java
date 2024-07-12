package store.novabook.front.store.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.category.service.CategoryService;

@Controller
@RequiredArgsConstructor
public class StoreIndexController {

    private final CategoryService categoryService;

    @GetMapping
    public String getCategory() {
        return "store/store_index";
    }
}