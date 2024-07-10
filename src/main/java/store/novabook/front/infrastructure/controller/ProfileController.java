package store.novabook.front.infrastructure.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {
	private final Environment environment;

	@GetMapping
	public String profile() {
		final List<String> profiles = Arrays.asList(environment.getActiveProfiles());
		final List<String> prodProfiles = Arrays.asList("set1", "set2");

		final String defaultProfile = profiles.isEmpty() ? "default" : profiles.getFirst();

		return profiles.stream()
			.filter(prodProfiles::contains)
			.findAny()
			.orElse(defaultProfile);
	}
}
