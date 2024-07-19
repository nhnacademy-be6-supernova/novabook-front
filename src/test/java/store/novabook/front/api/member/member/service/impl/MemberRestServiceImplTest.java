package store.novabook.front.api.member.member.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import store.novabook.front.api.member.member.dto.request.DuplicateEmailRequest;
import store.novabook.front.api.member.member.dto.request.DuplicateLoginIdRequest;
import store.novabook.front.api.member.member.dto.response.DuplicateResponse;
import store.novabook.front.api.member.member.dto.response.MemberNameResponse;
import store.novabook.front.api.member.member.service.MemberClient;
import store.novabook.front.common.response.ApiResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberRestServiceImplTest {

	@Mock
	private MemberClient memberClient;

	@InjectMocks
	private MemberRestServiceImpl memberRestService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void isDuplicateLoginId_ShouldReturnResponse() {
		// Given
		DuplicateLoginIdRequest request = new DuplicateLoginIdRequest("test_login_id");
		DuplicateResponse expectedResponse = new DuplicateResponse(true);

		// Mocking the client response
		when(memberClient.isDuplicateLoginId(any())).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		DuplicateResponse actualResponse = memberRestService.isDuplicateLoginId(request);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(memberClient).isDuplicateLoginId(request);
	}

	@Test
	void isDuplicateEmail_ShouldReturnResponse() {
		// Given
		DuplicateEmailRequest request = new DuplicateEmailRequest("test_email@example.com");
		DuplicateResponse expectedResponse = new DuplicateResponse(false);

		// Mocking the client response
		when(memberClient.isDuplicateEmail(any())).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		DuplicateResponse actualResponse = memberRestService.isDuplicateEmail(request);

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(memberClient).isDuplicateEmail(request);
	}

	@Test
	void getMemberName_ShouldReturnResponse() {
		// Given
		MemberNameResponse expectedResponse = new MemberNameResponse("John Doe");

		// Mocking the client response
		when(memberClient.getMemberName()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		// When
		MemberNameResponse actualResponse = memberRestService.getMemberName();

		// Then
		assertEquals(expectedResponse, actualResponse);
		verify(memberClient).getMemberName();
	}
}
