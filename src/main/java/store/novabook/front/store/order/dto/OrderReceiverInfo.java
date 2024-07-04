package store.novabook.front.store.order.dto;

public record OrderReceiverInfo(
	OrderAddressInfo orderAddressInfo,
	String name,
	String phone,
	String email

) {
}
