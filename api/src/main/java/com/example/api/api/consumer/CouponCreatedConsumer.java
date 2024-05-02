package com.example.api.api.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.api.api.domain.Coupon;
import com.example.api.api.domain.FailedEvent;
import com.example.api.api.repository.CouponRepository;
import com.example.api.api.repository.FailedEventRepository;

@Component
public class CouponCreatedConsumer {
    
    private final CouponRepository couponRepository;
    private final FailedEventRepository failedEventRepository;
    private final Logger logger = LoggerFactory.getLogger(CouponCreatedConsumer.class);

    public CouponCreatedConsumer(CouponRepository couponRepository, FailedEventRepository failedEventRepository) {
        this.couponRepository = couponRepository;
        this.failedEventRepository = failedEventRepository;
    }

    @KafkaListener(topics = "coupon_create", groupId = "group_1")
    public void listener(Long userId) {
        try {
            System.out.println("Coupon Created: " + userId);
            couponRepository.save(new Coupon(userId));
        } catch (Exception e) {
            logger.error("Coupon Created Error: " + userId);
            failedEventRepository.save(new FailedEvent(userId));
        }
        
    }
}
