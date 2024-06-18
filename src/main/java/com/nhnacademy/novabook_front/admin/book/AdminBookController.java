package com.nhnacademy.novabook_front.admin.book;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/admin/books")
@Controller
public class AdminBookController {

    @GetMapping("/book/form")
    public String getBookForm() {
        return "admin/book/book_form";
    }

    @PostMapping
    public String createBook(@RequestBody Map<String, Object> params) {
        log.info("Received params: {}", params);
        return "redirect:/admin/books/book/form";
    }
}
