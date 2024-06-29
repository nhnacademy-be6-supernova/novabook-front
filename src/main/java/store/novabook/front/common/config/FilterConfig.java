// package store.novabook.front.common.config;
//
//
// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
//
// @Configuration
// public class FilterConfig {
//
// 	@Bean
// 	public FilterRegistrationBean<CustomLoggingFilter> loggingFilter() {
// 		FilterRegistrationBean<CustomLoggingFilter> registrationBean = new FilterRegistrationBean<>();
// 		registrationBean.setFilter(new CustomLoggingFilter());
// 		registrationBean.addUrlPatterns("/*"); // 필터가 적용될 URL 패턴
// 		return registrationBean;
// 	}
// }
