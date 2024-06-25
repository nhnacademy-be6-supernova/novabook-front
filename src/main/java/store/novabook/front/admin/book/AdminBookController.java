package store.novabook.front.admin.book;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/books")
@Controller
public class AdminBookController {

    @GetMapping("book/form")
    public String getBookForm() {
        return "admin/book/book_form";
    }

    // @GetMapping
    // public String getBookAll(Model model, Pageable pageable) {
    //     ResponseEntity<Page<GetBookAllResponse>> response =  storeBookClient.getBooks(pageable);
    //     model.addAttribute("books", response.getBody());
    //     return "admin/book/book_manage";
    // }


}
