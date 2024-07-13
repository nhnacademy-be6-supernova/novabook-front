package store.novabook.front.api.admin.service.impl;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import store.novabook.front.api.admin.service.AdminClient;
import store.novabook.front.api.admin.service.AdminService;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminClient adminClient;

	@Override

	public void admin() {
		adminClient.admin();
	}
}
