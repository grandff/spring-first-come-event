package com.example.api.api.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.api.api.domain.Coupon;
import com.example.api.api.repository.CouponRepository;

@Component
public class CouponCreatedConsumer {
    
    private final CouponRepository couponRepository;

    public CouponCreatedConsumer(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        System.out.println("Coupon Created: " + userId);
        couponRepository.save(new Coupon(userId));
    }
}
