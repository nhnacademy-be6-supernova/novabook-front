package com.nhnacademy.novabook_front.store.book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nhnacademy.novabook_front.api.book.service.BookService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/books")

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    @GetMapping
    public String getBookAll() {
        return "store/book/book_list";
    }

    @GetMapping("/book/{bookId}")
    public String getBook(@PathVariable Long bookId, Model model) {
        model.addAttribute("book", bookService.getBookClient(bookId));
        return "store/book/book_detail";
    }
}
