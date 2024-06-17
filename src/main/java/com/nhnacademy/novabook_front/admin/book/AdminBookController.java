package com.nhnacademy.novabook_front.admin.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/books")
@Controller
public class AdminBookController {

    @GetMapping("book/form")
    public String getBookForm() {
        return "admin/book/book_form";
    }

    @PostMapping
    public String createBook() {
        return "";
    }


}
