package com.example.api.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppliedUserRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public AppliedUserRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 데이터를 넣기 위한 메소드
    public Long add(Long userId) {
        return redisTemplate.opsForSet().add("applied_user", userId.toString());
    }
}
