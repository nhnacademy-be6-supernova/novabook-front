package store.novabook.front.api.member.address.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.member.address.dto.response.ExceedResponse;
import store.novabook.front.api.member.address.service.MemberAddressClient;
import store.novabook.front.common.response.ApiResponse;

class MemberAddressRestServiceImplTest {

	@Mock
	private MemberAddressClient memberAddressClient;

	@InjectMocks
	private MemberAddressRestServiceImpl memberAddressRestService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testIsExceedMemberAddressCount() {
		ExceedResponse mockResponse = new ExceedResponse(true);
		ApiResponse<ExceedResponse> mockResponseEntity =new ApiResponse<>("SUCCESS", true, mockResponse);

		when(memberAddressClient.isExceedMemberAddressCount()).thenReturn(mockResponseEntity);

		ExceedResponse actualResponse = memberAddressRestService.isExceedMemberAddressCount();

		assertNotNull(actualResponse);
		assertTrue(actualResponse.isExceed());
		assertEquals(mockResponse.isExceed(), actualResponse.isExceed());

		verify(memberAddressClient, times(1)).isExceedMemberAddressCount();
	}


}
