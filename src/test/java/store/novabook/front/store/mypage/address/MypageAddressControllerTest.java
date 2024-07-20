package store.novabook.front.store.mypage.address;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import store.novabook.front.api.member.address.dto.request.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.request.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.address.service.MemberAddressService;
import store.novabook.front.api.member.grade.dto.GetMemberGradeResponse;
import store.novabook.front.api.member.grade.service.MemberGradeService;

@ExtendWith(MockitoExtension.class)
class MypageAddressControllerTest {

	@Mock
	private MemberAddressService memberAddressService;

	@Mock
	private MemberGradeService memberGradeService;

	@InjectMocks
	private MypageAddressController mypageAddressController;

	private MockMvc mockMvc;


	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(new MypageAddressController(memberAddressService, memberGradeService))
			.build();
	}

	@Test
	void testGetAddress() throws Exception {

		GetMemberAddressResponse getMemberAddressResponse = GetMemberAddressResponse.builder()
			.id(1L)
			.streetAddressId(10L)
			.memberId(100L)
			.zipcode("12345")
			.nickname("JohnDoe")
			.streetAddress("123 Main St")
			.memberAddressDetail("Apt 101")
			.build();
		GetMemberAddressResponse getMemberAddressResponse2 = GetMemberAddressResponse.builder()
			.id(1L)
			.streetAddressId(10L)
			.memberId(100L)
			.zipcode("12345")
			.nickname("JohnDoe")
			.streetAddress("123 Main St")
			.memberAddressDetail("Apt 101")
			.build();
		List<GetMemberAddressResponse> addressList = Arrays.asList(getMemberAddressResponse,
			getMemberAddressResponse2
		);
		when(memberAddressService.getMemberAddressAll()).thenReturn(addressList);
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("JohnDoe"));

		mockMvc = MockMvcBuilders.standaloneSetup(mypageAddressController).build();

		mockMvc.perform(get("/mypage/addresses"))
			.andExpect(status().isOk())
			.andExpect(view().name("store/mypage/address/address_list"))
			.andExpect(model().attributeExists("grade"))
			.andExpect(model().attributeExists("addressList"));
	}

	@Test
	void testUpdateAddress() throws Exception {
		Long addressId = 1L;
		UpdateMemberAddressRequest request = new UpdateMemberAddressRequest("John Doe", "12345", "456 Main St", "Apt 202");

		mockMvc = MockMvcBuilders.standaloneSetup(mypageAddressController).build();

		mockMvc.perform(post("/mypage/addresses/address/{addressId}/update", addressId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("nickname", request.nickname())
				.param("zipcode", request.zipcode())
				.param("streetAddress", request.streetAddress())
				.param("memberAddressDetail", request.memberAddressDetail()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/mypage/addresses"));

		verify(memberAddressService).updateMemberAddress(addressId, request);
	}

	@Test
	void testRegister() throws Exception {
		CreateMemberAddressRequest request = new CreateMemberAddressRequest("John Doe", "12345", "456 Main St", "Apt 202");

		mockMvc.perform(post("/mypage/addresses")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("nickname", request.nickname())
				.param("zipcode", request.zipcode())
				.param("streetAddress", request.streetAddress())
				.param("memberAddressDetail", request.memberAddressDetail()))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/mypage/addresses"));

		verify(memberAddressService).createMemberAddress(request);
	}

	@Test
	void testDeleteAddress() throws Exception {
		Long addressId = 1L;

		mockMvc.perform(get("/mypage/addresses/address/{addressId}/delete", addressId))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/mypage/addresses"));

		verify(memberAddressService).deleteMemberAddress(addressId);
	}


}
