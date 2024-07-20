package store.novabook.front.store.member;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import store.novabook.front.api.coupon.domain.CouponType;
import store.novabook.front.api.coupon.service.CouponService;
import store.novabook.front.api.member.coupon.dto.DownloadCouponMessageRequest;
import store.novabook.front.api.member.member.service.MemberAuthClient;
import store.novabook.front.api.member.member.service.MemberCouponService;
import store.novabook.front.common.security.aop.CurrentMembersArgumentResolver;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MemberCouponController.class)
@AutoConfigureMockMvc
class MemberCouponControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MemberCouponService memberCouponService;


	@MockBean
	private CouponService couponService;

	@MockBean
	private MemberAuthClient memberAuthClient;

	@BeforeEach
	void setup() {
		CurrentMembersArgumentResolver currentMembersArgumentResolver = new CurrentMembersArgumentResolver(
			memberAuthClient);
		mockMvc = MockMvcBuilders.standaloneSetup(new MemberCouponController(memberCouponService, couponService))
			.setCustomArgumentResolvers(currentMembersArgumentResolver)
			.build();
	}

	@Test
	void testDownloadLimited() throws Exception {
		// Create a request object
		DownloadCouponMessageRequest request = new DownloadCouponMessageRequest("uuid", CouponType.GENERAL, 1L);

		// Perform POST request to "/coupons/download/limited"
		mockMvc.perform(post("/coupons/download/limited")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk()); // Expect HTTP 200 OK status
	}

	@Test
	void testGetCouponAllForBook() throws Exception {
		Long bookId = 1L;

		// Perform GET request to "/coupons/book/{bookId}"
		mockMvc.perform(get("/coupons/book/{bookId}", bookId))
			.andExpect(status().isOk()); // Expect HTTP 200 OK status
	}

	@Test
	void testGetCouponAllForCategory() throws Exception {
		List<Long> categoryIdList = Arrays.asList(1L, 2L, 3L);

		// Perform GET request to "/coupons/categories"
		mockMvc.perform(get("/coupons/categories")
				.param("categoryIdList", categoryIdList.stream().map(Object::toString).collect(Collectors.joining(","))))
			.andExpect(status().isOk()); // Expect HTTP 200 OK status
	}

}
