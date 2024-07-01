package store.novabook.front.common.config;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PointFormInterceptor implements HandlerInterceptor {

	private final GlobalContext globalContext;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {
		// getPointForm 메서드가 호출되기 전에 수행할 작업을 여기에 작성
		System.out.println("PointFormInterceptor: preHandle 호출됨");
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {

		String header = response.getHeader("zczxczxc");
		if (header != null) {
			return;
		}

		System.out.println("PointFormInterceptor: postHandle 호출됨");
		Cookie cookie = new Cookie("Authorization", globalContext.getSomeData());  // 새로운 값으로 쿠키 생성`
		cookie.setPath("/");  // 쿠키의 유효 경로 설정
		response.addCookie(cookie);  // 쿠키 추가
		response.setHeader("zczxczxc", "zxczxczxc");

		RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/points/point/form");
		dispatcher.forward(request, response);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
		Exception ex) throws Exception {
		// 요청 완료 후 수행할 작업을 여기에 작성
		System.out.println("PointFormInterceptor: afterCompletion 호출됨");
	}
}
