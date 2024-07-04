package store.novabook.front.common.config;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;

import feign.Response;
import feign.codec.ErrorDecoder;
import store.novabook.front.common.security.exception.InternalServerErrorException;
import store.novabook.front.common.security.exception.NotFoundException;

public class CustomErrorDecoder implements ErrorDecoder {
	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		if (response.status() == 404) {
			return new NotFoundException("ASDASD");
		}
		if (response.status() == 500) {
			if(response.reason().equals("See Other")){
				System.out.println("sfsaf");
			}
			return new InternalServerErrorException();
		}
		if (response.status() == 401) {
			return new NotFoundException("401401401");
		}
		if (response.status() == 400) {
			String url = response.request().url();
			String url1 = response.request().requestTemplate().url();
			// return new BadRequestException("400400400");

			return defaultErrorDecoder.decode(methodKey, response);
		}

		return defaultErrorDecoder.decode(methodKey, response);
	}
}
