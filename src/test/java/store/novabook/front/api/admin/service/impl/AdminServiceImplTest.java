package store.novabook.front.api.admin.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import store.novabook.front.api.admin.service.AdminClient;

import static org.mockito.Mockito.*;

public class AdminServiceImplTest {

	@Mock
	private AdminClient adminClient;

	@InjectMocks
	private AdminServiceImpl adminService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAdmin() {
		// Given - No specific setup needed as we are not expecting a return value

		// When
		adminService.admin();

		// Then
		verify(adminClient, times(1)).admin();
	}
}
