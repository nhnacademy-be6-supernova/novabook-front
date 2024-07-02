package store.novabook.front.common.config;

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
			return new InternalServerErrorException();
		}
		if (response.status() == 401) {
			return new NotFoundException("401401401");
		}

		return defaultErrorDecoder.decode(methodKey, response);
	}
}
