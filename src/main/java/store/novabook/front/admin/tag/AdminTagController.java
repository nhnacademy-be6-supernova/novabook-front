package store.novabook.front.admin.tag;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.tag.dto.request.CreateTagRequest;
import store.novabook.front.common.response.PageResponse;

import store.novabook.front.api.tag.service.TagService;

@RequestMapping("/admin/tags")
@Controller
@RequiredArgsConstructor
public class AdminTagController {

	private final TagService tagService;
	private static final String DEFAULT_SIZE = "5";

	@GetMapping
	public String getTagAll(Model model, @RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = DEFAULT_SIZE) int size) {
		model.addAttribute("tags", tagService.getTags(page, size));
		return "admin/tag/tag_list";
	}

	//태그 생성
	@PostMapping("/register")
	public String createTag(@ModelAttribute CreateTagRequest tagRequest) {
		tagService.createTags(tagRequest);
		return "redirect:/admin/tags";
	}

	@PostMapping("/tag/{tagId}/update")
	public String createTag(@PathVariable Long tagId) {
		return "redirect:/admin/tags";
	}

	@GetMapping("/tag/{tagId}/delete")
	public String deleteTag(@PathVariable Long tagId) {
		tagService.deleteTag(tagId);
		return "redirect:/admin/tags";
	}
}
