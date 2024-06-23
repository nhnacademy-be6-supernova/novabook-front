package com.nhnacademy.novabook_front.admin.category;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@RequestMapping("/admin/categories")
@Controller
public class AdminCategoryController {
		@GetMapping
		public String getCategories() {
			return  "admin/category/category_list";
		}

}
