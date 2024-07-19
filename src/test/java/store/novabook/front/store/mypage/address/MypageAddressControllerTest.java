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
		// Mock data
		List<GetMemberAddressResponse> addressList = Arrays.asList(getMemberAddressResponse,
			getMemberAddressResponse2
		);
		when(memberAddressService.getMemberAddressAll()).thenReturn(addressList);
		// Mock grade service
		when(memberGradeService.getMemberGrade()).thenReturn(new GetMemberGradeResponse("JohnDoe"));

		// Set up MockMvc
		mockMvc = MockMvcBuilders.standaloneSetup(mypageAddressController).build();

		// Perform GET request to "/mypage/addresses"
		mockMvc.perform(get("/mypage/addresses"))
			.andExpect(status().isOk()) // Expect HTTP 200 OK status
			.andExpect(view().name("store/mypage/address/address_list")) // Expect view name
			.andExpect(model().attributeExists("grade")) // Expect "grade" attribute in the model
			.andExpect(model().attributeExists("addressList")); // Expect "addressList" attribute in the model
	}

	@Test
	void testUpdateAddress() throws Exception {
		Long addressId = 1L;
		UpdateMemberAddressRequest request = new UpdateMemberAddressRequest("John Doe", "12345", "456 Main St", "Apt 202");

		// Set up MockMvc
		mockMvc = MockMvcBuilders.standaloneSetup(mypageAddressController).build();

		// Perform POST request to "/mypage/addresses/address/{addressId}/update"
		mockMvc.perform(post("/mypage/addresses/address/{addressId}/update", addressId)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("nickname", request.nickname())
				.param("zipcode", request.zipcode())
				.param("streetAddress", request.streetAddress())
				.param("memberAddressDetail", request.memberAddressDetail()))
			.andExpect(status().is3xxRedirection()) // Expect HTTP redirect status
			.andExpect(redirectedUrl("/mypage/addresses")); // Expect redirect URL

		// Verify that memberAddressService.updateMemberAddress() was called with correct arguments
		verify(memberAddressService).updateMemberAddress(addressId, request);
	}

	@Test
	void testRegister() throws Exception {
		CreateMemberAddressRequest request = new CreateMemberAddressRequest("John Doe", "12345", "456 Main St", "Apt 202");

		// Perform POST request to "/mypage/addresses"
		mockMvc.perform(post("/mypage/addresses")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("nickname", request.nickname())
				.param("zipcode", request.zipcode())
				.param("streetAddress", request.streetAddress())
				.param("memberAddressDetail", request.memberAddressDetail()))
			.andExpect(status().is3xxRedirection()) // Expect HTTP redirect status
			.andExpect(redirectedUrl("/mypage/addresses")); // Expect redirect URL

		// Verify that memberAddressService.createMemberAddress() was called with correct arguments
		verify(memberAddressService).createMemberAddress(request);
	}

	@Test
	void testDeleteAddress() throws Exception {
		Long addressId = 1L;

		// Perform GET request to "/mypage/addresses/address/{addressId}/delete"
		mockMvc.perform(get("/mypage/addresses/address/{addressId}/delete", addressId))
			.andExpect(status().is3xxRedirection()) // Expect HTTP redirect status
			.andExpect(redirectedUrl("/mypage/addresses")); // Expect redirect URL

		// Verify that memberAddressService.deleteMemberAddress() was called with correct argument
		verify(memberAddressService).deleteMemberAddress(addressId);
	}


}
