package store.novabook.front.common.interceptor;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import feign.RequestTemplate;
import feign.Target;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class FeignClientInterceptorTest {

	private FeignClientInterceptor feignClientInterceptor;

	@Mock
	private HttpServletRequest request;

	@Mock
	private RequestTemplate requestTemplate;

	@Mock
	private Target target;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		feignClientInterceptor = new FeignClientInterceptor(request);
	}

	@Test
	void apply_WithAuthorizationAndRefreshCookies_ShouldAddHeaders() {
		// Arrange
		String accessToken = "accessToken";
		String refreshToken = "refreshToken";
		Cookie authCookie = new Cookie("Authorization", accessToken);
		Cookie refreshCookie = new Cookie("Refresh", refreshToken);
		Cookie[] cookies = new Cookie[]{authCookie, refreshCookie};

		when(request.getCookies()).thenReturn(cookies);
		when(request.getAttribute("reissuedAccessToken")).thenReturn(null);

		// Mocking the Target object and its name method
		when(requestTemplate.feignTarget()).thenReturn(target);
		when(target.name()).thenReturn("someTarget");

		// Act
		feignClientInterceptor.apply(requestTemplate);

		// Assert
		verify(requestTemplate).header("Authorization", "Bearer " + accessToken);
		verify(requestTemplate).header("Refresh", "Bearer " + refreshToken);
	}

	@Test
	void apply_WithReissuedAccessToken_ShouldAddReissuedAccessToken() {
		// Arrange
		String accessToken = "accessToken";
		String reissuedAccessToken = "reissuedAccessToken";
		Cookie authCookie = new Cookie("Authorization", accessToken);
		Cookie refreshCookie = new Cookie("Refresh", "refreshToken");
		Cookie[] cookies = new Cookie[]{authCookie, refreshCookie};

		when(request.getCookies()).thenReturn(cookies);
		when(request.getAttribute("reissuedAccessToken")).thenReturn(reissuedAccessToken);

		// Mocking the Target object and its name method
		when(requestTemplate.feignTarget()).thenReturn(target);
		when(target.name()).thenReturn("someTarget");

		// Act
		feignClientInterceptor.apply(requestTemplate);

		// Assert
		verify(requestTemplate).header("Authorization", "Bearer " + reissuedAccessToken);
		verify(requestTemplate).header("Refresh", "Bearer refreshToken");
	}

	@Test
	void apply_WithNoCookies_ShouldNotAddHeaders() {
		// Arrange
		when(request.getCookies()).thenReturn(null);

		// Mocking the Target object and its name method
		when(requestTemplate.feignTarget()).thenReturn(target);
		when(target.name()).thenReturn("someTarget");

		// Act
		feignClientInterceptor.apply(requestTemplate);

		// Assert
		verify(requestTemplate, never()).header(any(String.class), any(String.class));
	}

	@Test
	void apply_WithPaycoFeignTarget_ShouldNotAddHeaders() {
		// Arrange
		RequestTemplate mockRequestTemplate = mock(RequestTemplate.class);
		Target mockTarget = mock(Target.class);
		when(mockTarget.name()).thenReturn("payco");
		when(mockRequestTemplate.feignTarget()).thenReturn(mockTarget);
		when(request.getCookies()).thenReturn(new Cookie[]{});

		// Act
		feignClientInterceptor.apply(mockRequestTemplate);

		// Assert
		verify(mockRequestTemplate, never()).header(any(String.class), any(String.class));
	}
}
