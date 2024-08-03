package store.novabook.front.api.cart.dto;

public record CartBookInfoDto(
	Long id,
	Long discountPrice,
	Integer bookStatusId
){
}
