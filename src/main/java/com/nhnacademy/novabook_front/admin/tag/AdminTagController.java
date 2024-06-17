package com.nhnacademy.novabook_front.admin.tag;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/tags")
@Controller
public class AdminTagController {

    @GetMapping
    public String getTagAll() {
        return  "admin/tag/tag_list";
    }

    @GetMapping("/tag/form")
    public String getTagForm() {
        return "admin/tag/tag_form";
    }

    @PostMapping("/tag/{tagId}/update")
    public String updateTag(@PathVariable Long tagId) {
        return "";
    }

    @GetMapping("/tag/{tagId}/delete")
    public String deleteTag(@PathVariable Long tagId) {
        return "";
    }
}
