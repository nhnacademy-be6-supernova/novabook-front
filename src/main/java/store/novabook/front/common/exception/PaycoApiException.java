package store.novabook.front.common.exception;

public class PaycoApiException extends NovaException{
	public PaycoApiException(ErrorCode errorCode) {
		super(errorCode);
	}
}
