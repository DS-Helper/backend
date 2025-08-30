package com.project.ds_helper.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

        // Redis Connection Configuration
        @Bean
        LettuceConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
        }

                //(name = "CustomStringRedisTemplate")
        @Bean
        StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
            StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);

            return stringRedisTemplate;
        }
}
