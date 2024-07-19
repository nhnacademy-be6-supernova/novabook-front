package store.novabook.front.admin.point;

import static org.mockito.ArgumentMatchers.*;
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
import store.novabook.front.api.point.dto.request.CreatePointPolicyRequest;
import store.novabook.front.api.point.dto.response.GetPointPolicyResponse;
import store.novabook.front.api.point.service.PointPolicyService;
import store.novabook.front.common.response.PageResponse;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@WebMvcTest(AdminPointController.class)
public class AdminPointControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PointPolicyService pointPolicyService;
	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new AdminPointController(pointPolicyService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	public void testGetPointForm() throws Exception {

		GetPointPolicyResponse getPointPolicyResponse = GetPointPolicyResponse.builder()
			.reviewPointRate(10L)
			.basicPoint(100L)
			.registerPoint(50L)
			.build();
		List<GetPointPolicyResponse> data = List.of(getPointPolicyResponse);
		PageResponse<GetPointPolicyResponse> expectedResponse = new PageResponse<>(1, 10, 30, data);

		when(pointPolicyService.getPointPolicyAllPage(anyInt(), anyInt())).thenReturn(expectedResponse);

		// Act and Assert
		mockMvc.perform(get("/admin/points/point/form")
				.param("page", "0")
				.param("size", "10"))
			.andExpect(status().isOk())
			.andExpect(view().name("admin/point/point_form"))
			.andExpect(model().attribute("pointPolicies", expectedResponse))
			.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreatePointPolicy() throws Exception {
		// Arrange
		CreatePointPolicyRequest request = CreatePointPolicyRequest.builder()
			.reviewPointRate(10L)
			.basicPoint(100L)
			.registerPoint(50L)
			.build();
//
		// Act and Assert
		mockMvc.perform(post("/admin/points/point")
				.contentType("application/x-www-form-urlencoded")
				.param("reviewPointRate", String.valueOf(request.reviewPointRate()))
				.param("basicPoint", String.valueOf(request.basicPoint()))
				.param("registerPoint", String.valueOf(request.registerPoint())))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/admin/points/point/form"))
			.andDo(MockMvcResultHandlers.print());

		// Verify that createPointPolicy was called with the correct request
		verify(pointPolicyService).createPointPolicy(any(CreatePointPolicyRequest.class));
	}
}
