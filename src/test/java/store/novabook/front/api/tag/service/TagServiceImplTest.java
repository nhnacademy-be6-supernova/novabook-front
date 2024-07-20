package store.novabook.front.api.tag.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.tag.dto.request.CreateTagRequest;
import store.novabook.front.api.tag.dto.response.CreateTagResponse;
import store.novabook.front.api.tag.dto.response.GetTagListResponse;
import store.novabook.front.api.tag.dto.response.GetTagResponse;
import store.novabook.front.common.response.ApiResponse;
import store.novabook.front.common.response.PageResponse;

class TagServiceImplTest {

	@Mock
	private TagClient tagClient;

	@InjectMocks
	private TagServiceImpl tagService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateTags() {
		CreateTagRequest request = new CreateTagRequest("TestTag");
		CreateTagResponse expectedResponse = new CreateTagResponse(1L);
		when(tagClient.createTag(request)).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		CreateTagResponse actualResponse = tagService.createTags(request);

		assertEquals(expectedResponse, actualResponse);
		verify(tagClient, times(1)).createTag(request);
	}

	@Test
	void testGetTags() {
		int page = 0;
		int size = 10;
		PageResponse<GetTagResponse> expectedResponse = new PageResponse<>(1, 10, 30, Arrays.asList(
			new GetTagResponse(1L, "Tag1"),
			new GetTagResponse(2L, "Tag2")
		));
		when(tagClient.getTagAll(page, size)).thenReturn(expectedResponse);

		PageResponse<GetTagResponse> actualResponse = tagService.getTags(page, size);

		assertEquals(expectedResponse, actualResponse);
		verify(tagClient, times(1)).getTagAll(page, size);
	}

	@Test
	void testGetTagList() {
		GetTagListResponse mockResponse = new GetTagListResponse(Arrays.asList(
			new GetTagResponse(1L, "Tag1"),
			new GetTagResponse(2L, "Tag2")
		));
		when(tagClient.getTagAllList()).thenReturn(new ApiResponse<>("SUCCESS", true, mockResponse));

		List<GetTagResponse> actualList = tagService.getTagList();

		assertEquals(mockResponse.getTagResponseList(), actualList);
		verify(tagClient, times(1)).getTagAllList();
	}

	@Test
	void testDeleteTag() {
		Long tagId = 1L;

		tagService.deleteTag(tagId);

		verify(tagClient, times(1)).deleteTag(tagId);
	}
}
