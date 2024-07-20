package store.novabook.front.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class FileUploadConfig {
	private static final int DEFAULT_MAX_FILE_SIZE = 8;
	private static final int DEFAULT_MAX_REQUEST_SIZE = 2;

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofMegabytes(DEFAULT_MAX_FILE_SIZE)); // 최대 파일 크기
		factory.setMaxRequestSize(DataSize.ofMegabytes(DEFAULT_MAX_REQUEST_SIZE)); // 최대 요청 크기
		return factory.createMultipartConfig();
	}
}