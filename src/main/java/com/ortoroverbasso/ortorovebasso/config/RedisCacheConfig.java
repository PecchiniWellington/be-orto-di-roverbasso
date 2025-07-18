package com.ortoroverbasso.ortorovebasso.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.ortoroverbasso.ortorovebasso.constants.CacheNames;

@Configuration
public class RedisCacheConfig {

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
                // Configurazione default con serializer JSON
                RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(10))
                                .serializeValuesWith(
                                                RedisSerializationContext.SerializationPair.fromSerializer(
                                                                new GenericJackson2JsonRedisSerializer()));

                // Configurazioni specifiche per cache nominate
                Map<String, RedisCacheConfiguration> cacheConfigs = new HashMap<>();
                cacheConfigs.put(CacheNames.PRODUCTS, defaultConfig.entryTtl(Duration.ofMinutes(5)));
                cacheConfigs.put(CacheNames.FLAT_PRODUCTS, defaultConfig.entryTtl(Duration.ofMinutes(2)));
                cacheConfigs.put(CacheNames.CATEGORIES, defaultConfig.entryTtl(Duration.ofMinutes(15)));

                return RedisCacheManager.builder(factory)
                                .cacheDefaults(defaultConfig)
                                .withInitialCacheConfigurations(cacheConfigs)
                                .build();
        }
}
