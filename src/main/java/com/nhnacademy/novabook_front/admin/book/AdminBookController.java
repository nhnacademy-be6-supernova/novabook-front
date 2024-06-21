package com.nhnacademy.novabook_front.admin.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.novabook_front.api.book.BookClient;

@RequestMapping("/admin/books")
@Controller
public class AdminBookController {

    BookClient bookClient;

    @GetMapping("book/form")
    public String getBookForm() {
        return "admin/book/book_form";
    }

    @PostMapping
    public String createBook() {
        return "";
    }

    // @GetMapping
    // public String getBookAll(Model model, Pageable pageable) {
    //     ResponseEntity<Page<GetBookAllResponse>> response =  storeBookClient.getBooks(pageable);
    //     model.addAttribute("books", response.getBody());
    //     return "admin/book/book_manage";
    // }


}
