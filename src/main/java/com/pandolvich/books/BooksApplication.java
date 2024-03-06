package com.pandolvich.books;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				// add cors for all origins and for all urls
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
			}
		};
	}

	@Configuration
	@EnableCaching
	public class CacheConfig {
		@Bean
		public RedisCacheConfiguration redisCacheConfiguration() {
			return RedisCacheConfiguration.defaultCacheConfig()
					.entryTtl(Duration.ofMinutes(10)) // Set cache expiration time
					.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
		}

		@Bean
		public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
			return RedisCacheManager.builder(connectionFactory)
					.cacheDefaults(redisCacheConfiguration())
					.build();
		}
	}
}
