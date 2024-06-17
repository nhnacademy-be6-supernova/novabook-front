package com.nhnacademy.novabook_front.store.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("books")

@Controller
public class BookController {

    @GetMapping
    public String getBookAll() {
        return "/store/book/book_list";
    }

    @GetMapping("/book/{bookId}")
    public String getBook(@PathVariable Long bookId) {
        return "/store/book/book_detail";
    }
}
