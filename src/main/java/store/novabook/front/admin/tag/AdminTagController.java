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
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.api.tag.dto.CreateTagRequest;
import store.novabook.front.api.tag.dto.CreateTagResponse;
import store.novabook.front.api.tag.dto.GetTagResponse;
import store.novabook.front.api.tag.service.TagService;

@RequestMapping("/admin/tags")
@Controller
@RequiredArgsConstructor
public class AdminTagController {
    private final TagService tagService;
    private static final String DEFAULT_SIZE = "5" ;

    @GetMapping
    public String getTagAll( Model model,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = DEFAULT_SIZE) int size) {
        // 페이져블 라이브러리를 사용하여 페이지별 태그 데이터를 가져옵니다.
        PageResponse<GetTagResponse> tagPage = tagService.getTags(page-1, size);
        //현재 페이지의 데이터 리스트
        model.addAttribute("tags",tagPage.getData());
        //현재 페이지 번호
        model.addAttribute("currentPage", page);
        //전체 페이지 수
        model.addAttribute("pageSize", tagPage.getTotalPageCount());

        return "admin/tag/tag_list";
    }

    //태그 생성
    @PostMapping("/register")
    public String createTag(@ModelAttribute CreateTagRequest tagRequest, Model model) {
        CreateTagResponse response = tagService.createTags(tagRequest);
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
