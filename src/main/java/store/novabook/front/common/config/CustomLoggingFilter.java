// package store.novabook.front.common.config;
//
//
// import jakarta.servlet.Filter;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.FilterConfig;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.ServletRequest;
// import jakarta.servlet.ServletResponse;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.logging.Logger;
//
// public class CustomLoggingFilter implements Filter {
//
// 	private static final Logger logger = Logger.getLogger(CustomLoggingFilter.class.getName());
//
// 	@Override
// 	public void init(FilterConfig filterConfig) throws ServletException {
// 		// 필터 초기화
// 		logger.info("CustomLoggingFilter initialized");
// 	}
//
// 	@Override
// 	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
// 		throws IOException, ServletException {
// 		HttpServletRequest httpRequest = (HttpServletRequest) request;
// 		HttpServletResponse httpResponse = (HttpServletResponse) response;
//
// 		// 요청 정보 로그 기록
// 		logger.info("Request URL: " + httpRequest.getRequestURL());
// 		logger.info("Request Method: " + httpRequest.getMethod());
//
// 		// 필터 체인으로 요청 전달
// 		chain.doFilter(request, response);
//
// 		// 응답 정보 로그 기록
// 		logger.info("Response Status Code: " + httpResponse.getStatus());
// 	}
//
// 	@Override
// 	public void destroy() {
// 		// 필터 종료
// 		logger.info("CustomLoggingFilter destroyed");
// 	}
// }
