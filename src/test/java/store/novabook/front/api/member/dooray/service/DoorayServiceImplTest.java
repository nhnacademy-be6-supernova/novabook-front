package store.novabook.front.api.member.dooray.service.impl;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.member.dooray.dto.DoorayAuthCodeRequest;
import store.novabook.front.api.member.dooray.dto.DoorayAuthRequest;
import store.novabook.front.api.member.dooray.service.DoorayHookClient;

public class DoorayServiceImplTest {

	@Mock
	private DoorayHookClient doorayHookClient;

	@InjectMocks
	private DoorayServiceImpl doorayService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSendAuthCode() {
		// Given
		DoorayAuthRequest request = new DoorayAuthRequest("uuid");

		// When
		doorayService.sendAuthCode(request);

		// Then
		verify(doorayHookClient, times(1)).sendMessage(request);
	}

	@Test
	void testConfirmAuthCode() {
		// Given
		DoorayAuthCodeRequest request = new DoorayAuthCodeRequest("user@example.com", "123456");

		// When
		doorayService.confirmAuthCode(request);

		// Then
		verify(doorayHookClient, times(1)).confirmDormantMember(request);
	}
}
