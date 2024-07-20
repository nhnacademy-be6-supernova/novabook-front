package store.novabook.front.admin.tag;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.tag.domain.Tag;
import store.novabook.front.api.tag.dto.request.CreateTagRequest;
import store.novabook.front.api.tag.dto.response.GetTagResponse;
import store.novabook.front.api.tag.service.TagService;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@WebMvcTest(AdminTagController.class)
class AdminTagControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TagService tagService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminTagController(tagService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testGetTagAll() throws Exception {

		Tag sampleTag = Tag.builder().id(1L).name("test").build();
		GetTagResponse getTagResponse = GetTagResponse.fromEntity(sampleTag);
		List<GetTagResponse> data = List.of(getTagResponse);
		PageResponse<GetTagResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		when(tagService.getTags(anyInt(), anyInt())).thenReturn(expectedResponse);

		mockMvc.perform(get("/admin/tags")
				.param("page", "0")
				.param("size", "5"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/tag/tag_list"))
			.andExpect(model().attribute("tags", expectedResponse))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void testCreateTag() throws Exception {
		CreateTagRequest request = new CreateTagRequest("New Tag");

		mockMvc.perform(post("/admin/tags/register")
				.contentType("application/x-www-form-urlencoded")
				.param("name", request.name()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/admin/tags"))
			.andDo(MockMvcResultHandlers.print());

		verify(tagService).createTags(any(CreateTagRequest.class));
	}


	@Test
	void testDeleteTag() throws Exception {
		mockMvc.perform(get("/admin/tags/tag/1/delete"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/admin/tags"))
			.andDo(MockMvcResultHandlers.print());

		verify(tagService).deleteTag(1L);
	}
}
