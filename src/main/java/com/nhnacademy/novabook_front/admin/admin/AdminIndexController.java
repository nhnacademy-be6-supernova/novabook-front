package com.nhnacademy.novabook_front.admin.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {

    @GetMapping
    public String index() {
        return "admin/admin_index";
    }

    @GetMapping("book/form")
    public String getBookForm() {
        return "admin/book/book_form";
    }

    @PostMapping
    public String createBook() {
        return "";
    }

    @GetMapping("books")
    public String getBooks() {

        return "admin/book/book_manage";
    }

    // @GetMapping
    // public String getBooks(Model model, Pageable pageable) {
    //     ResponseEntity<Page<GetBookAllResponse>> response =  storeBookClient.getBooks(pageable);
    //     model.addAttribute("books", response.getBody());
    //     return "admin/book/book_manage";
    // }


}
