package store.novabook.front.api.book;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "searchBookClient")
public interface BookSearchClient {
}
