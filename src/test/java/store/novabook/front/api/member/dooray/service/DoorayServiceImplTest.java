package store.novabook.front.api.member.dooray.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import store.novabook.front.api.member.dooray.dto.DoorayAuthCodeRequest;
import store.novabook.front.api.member.dooray.dto.DoorayAuthRequest;
import store.novabook.front.api.member.dooray.service.impl.DoorayServiceImpl;

class DoorayServiceImplTest {

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
		DoorayAuthRequest request = new DoorayAuthRequest("uuid");

		doorayService.sendAuthCode(request);

		verify(doorayHookClient, times(1)).sendMessage(request);
	}

	@Test
	void testConfirmAuthCode() {
		DoorayAuthCodeRequest request = new DoorayAuthCodeRequest("user@example.com", "123456");

		doorayService.confirmAuthCode(request);

		verify(doorayHookClient, times(1)).confirmDormantMember(request);
	}
}
