package store.novabook.front.api.tag.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tag {
	Long id;
	String name;
}
