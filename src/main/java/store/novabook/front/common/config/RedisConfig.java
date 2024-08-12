package store.novabook.front.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import store.novabook.front.common.util.KeyManagerUtil;
import store.novabook.front.common.util.dto.RedisConfigDto;
import store.novabook.front.redis.listener.RedisMessageSubscriber;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {
	private final Environment environment;

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RestTemplate restTemplate = new RestTemplate();

		RedisConfigDto redisConfig = KeyManagerUtil.getRedisConfig(environment, restTemplate);

		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisConfig.host());
		redisStandaloneConfiguration.setPort(redisConfig.port());
		redisStandaloneConfiguration.setPassword(redisConfig.password());
		redisStandaloneConfiguration.setDatabase(redisConfig.database());
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	@Bean
	public RedisTemplate<String, Object> redisCartTemplate() {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//		redisTemplate.expire("cart",60, TimeUnit.SECONDS);
		return redisTemplate;
	}

	/**
	 * RedisMessageListenerContainer는 Spring Data Redis에서 제공하는 클래스이다.
	 * 컨테이너는 메시지가 도착하면 등록된 MessageListener를 호출하여 메시지를 처리한다.
	 */
	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
		MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new ChannelTopic("notificationTopic"));
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(RedisMessageSubscriber subscriber) {
		return new MessageListenerAdapter(subscriber, "onMessage");
	}

}