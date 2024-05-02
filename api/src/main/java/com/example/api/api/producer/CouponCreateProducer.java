package com.example.api.api.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class CouponCreateProducer {
    private final KafkaTemplate<String, Long> kafkaTemplate;
    
    public CouponCreateProducer(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // 유저 아이디를 매개변수로 받아 카프카에 데이터 전송
    public void create(Long userId) {
        kafkaTemplate.send("coupon_create", userId);
    }
}
