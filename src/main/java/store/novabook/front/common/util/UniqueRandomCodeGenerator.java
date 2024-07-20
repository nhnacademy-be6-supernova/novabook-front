package store.novabook.front.common.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class UniqueRandomCodeGenerator {

	private static final String ALPHANUMERIC = "ABCDEFGHJKLMNPRSTUVWXYZ23456789";
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final String REDIS_SET_KEY = "uniqueCodes";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	public String generateUniqueRandomCode() {
		String code;
		do {
			code = generateRandomCode();
		} while (isCodeExistsInRedis(code));

		addCodeToRedis(code);
		return code;
	}

	private boolean isCodeExistsInRedis(String code) {
		return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(REDIS_SET_KEY, code));
	}

	private void addCodeToRedis(String code) {
		redisTemplate.opsForSet().add(REDIS_SET_KEY, code);
	}

	private String generateRandomCode() {
		StringBuilder code = new StringBuilder();

		// Generate current date in YYMMDD format
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
		String date = now.format(formatter);

		// Append the date to the code
		code.append(date);

		// Generate 5 random alphanumeric characters
		for (int i = 0; i < 5; i++) {
			int index = RANDOM.nextInt(ALPHANUMERIC.length());
			code.append(ALPHANUMERIC.charAt(index));
		}

		return code.toString();
	}
}
