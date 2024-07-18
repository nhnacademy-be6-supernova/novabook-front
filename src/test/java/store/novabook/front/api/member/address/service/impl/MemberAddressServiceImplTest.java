package store.novabook.front.api.member.address.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.member.address.domain.StreetAddress;
import store.novabook.front.api.member.address.dto.request.CreateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.request.UpdateMemberAddressRequest;
import store.novabook.front.api.member.address.dto.response.CreateMemberAddressResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressListResponse;
import store.novabook.front.api.member.address.dto.response.GetMemberAddressResponse;
import store.novabook.front.api.member.address.service.MemberAddressClient;
import store.novabook.front.common.response.ApiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

public class MemberAddressServiceImplTest {

	@Mock
	private MemberAddressClient memberAddressClient;

	@InjectMocks
	private MemberAddressServiceImpl memberAddressService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateMemberAddress() {
		StreetAddress streetAddress = StreetAddress.builder()
			.id(1L)
			.zipcode("12345")
			.streetAddress("123 Main St")
			.createdAt(LocalDateTime.now())
			.updatedAt(LocalDateTime.now())
			.build();
		// Given
		CreateMemberAddressRequest request = new CreateMemberAddressRequest("John Doe", "123 Street", "City", "12345");
		CreateMemberAddressResponse expectedResponse = new CreateMemberAddressResponse(1L, streetAddress, "detail");

		ApiResponse<CreateMemberAddressResponse> apiResponse = new ApiResponse<>("SUCCESS", true, expectedResponse);
		when(memberAddressClient.createMemberAddress(request)).thenReturn(apiResponse);

		// When
		CreateMemberAddressResponse actualResponse = memberAddressService.createMemberAddress(request);

		// Then
		assertEquals(expectedResponse.id(), actualResponse.id());
		verify(memberAddressClient, times(1)).createMemberAddress(request);
	}

	@Test
	void testGetMemberAddressAll() {
		// Given

		GetMemberAddressResponse response = GetMemberAddressResponse.builder()
			.id(1L)
			.streetAddressId(2L)
			.memberId(3L)
			.zipcode("12345")
			.nickname("John Doe")
			.streetAddress("123 Street")
			.memberAddressDetail("Apt 101")
			.build();

		GetMemberAddressResponse response2 = GetMemberAddressResponse.builder()
			.id(2L)
			.streetAddressId(3L)
			.memberId(4L)
			.zipcode("555")
			.nickname("John Doe2")
			.streetAddress("123 Street")
			.memberAddressDetail("Apt 101")
			.build();
		GetMemberAddressListResponse responseBody = new GetMemberAddressListResponse(List.of(
			response,
			response2
		));
		ApiResponse<GetMemberAddressListResponse> apiResponse = new ApiResponse<>("SUCCESS", true, responseBody);
		when(memberAddressClient.getMemberAddressAll()).thenReturn(apiResponse);

		// When
		List<GetMemberAddressResponse> actualResponse = memberAddressService.getMemberAddressAll();

		// Then
		assertEquals(2, actualResponse.size());
		assertEquals(1L, actualResponse.get(0).id());
		assertEquals("John Doe", actualResponse.get(0).nickname());
		assertEquals("123 Street", actualResponse.get(0).streetAddress());
		assertEquals("12345", actualResponse.get(0).zipcode());


		verify(memberAddressClient, times(1)).getMemberAddressAll();
	}

	@Test
	void testUpdateMemberAddress() {
		// Given
		Long memberAddressId = 1L;
		UpdateMemberAddressRequest request = new UpdateMemberAddressRequest("456 Avenue", "Town", "67890", "detail");

		// When
		memberAddressService.updateMemberAddress(memberAddressId, request);

		// Then
		verify(memberAddressClient, times(1)).updateMemberAddress(memberAddressId, request);
	}

	@Test
	void testDeleteMemberAddress() {
		// Given
		Long memberAddressId = 1L;

		// When
		memberAddressService.deleteMemberAddress(memberAddressId);

		// Then
		verify(memberAddressClient, times(1)).deleteMemberAddress(memberAddressId);
	}

}
