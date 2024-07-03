package store.novabook.front.api.book.dto.request;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateReviewRequest(
	@NotBlank(message = "content 값은 필수 입니다 ")
	String content,
	@NotNull(message = "score는 필수 값입니다.")
	Integer score,
	List<ReviewImageDTO> reviewImageDTOs
) {
	public void setReviewImageDTOs(List<MultipartFile> reviewImages) {
		reviewImages.forEach(reviewImage -> {
			try {
				reviewImageDTOs.add(convertToDTO(reviewImage));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

	}

	private ReviewImageDTO convertToDTO(MultipartFile reviewImage) throws IOException {
		return ReviewImageDTO.builder()
			.fileName(reviewImage.getOriginalFilename())
			.fileType(reviewImage.getContentType())
			.data(Base64.getEncoder().encodeToString(reviewImage.getBytes()))
			.build();
	}
}