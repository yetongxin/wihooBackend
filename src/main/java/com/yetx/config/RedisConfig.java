package com.yetx.config;

import org.springframework.cache.CacheManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig  {



    @Bean
    public RedisTemplate<String, Long> timeRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<String, Long>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());


        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }
}
