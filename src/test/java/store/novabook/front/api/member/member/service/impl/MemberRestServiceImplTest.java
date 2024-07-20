package store.novabook.front.api.member.member.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
		DuplicateLoginIdRequest request = new DuplicateLoginIdRequest("test_login_id");
		DuplicateResponse expectedResponse = new DuplicateResponse(true);

		when(memberClient.isDuplicateLoginId(any())).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		DuplicateResponse actualResponse = memberRestService.isDuplicateLoginId(request);

		assertEquals(expectedResponse, actualResponse);
		verify(memberClient).isDuplicateLoginId(request);
	}

	@Test
	void isDuplicateEmail_ShouldReturnResponse() {
		DuplicateEmailRequest request = new DuplicateEmailRequest("test_email@example.com");
		DuplicateResponse expectedResponse = new DuplicateResponse(false);

		when(memberClient.isDuplicateEmail(any())).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		DuplicateResponse actualResponse = memberRestService.isDuplicateEmail(request);

		assertEquals(expectedResponse, actualResponse);
		verify(memberClient).isDuplicateEmail(request);
	}

	@Test
	void getMemberName_ShouldReturnResponse() {
		MemberNameResponse expectedResponse = new MemberNameResponse("John Doe");

		when(memberClient.getMemberName()).thenReturn(new ApiResponse<>("SUCCESS", true, expectedResponse));

		MemberNameResponse actualResponse = memberRestService.getMemberName();

		assertEquals(expectedResponse, actualResponse);
		verify(memberClient).getMemberName();
	}
}
