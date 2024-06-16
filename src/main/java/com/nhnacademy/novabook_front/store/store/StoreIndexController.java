package com.nhnacademy.novabook_front.store.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StoreIndexController {

    @GetMapping
    public String index() {
        return "/store/store_index";
    }
}
