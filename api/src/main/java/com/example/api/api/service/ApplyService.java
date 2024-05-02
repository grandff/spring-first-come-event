package com.example.api.api.service;

import org.springframework.stereotype.Service;

import com.example.api.api.domain.Coupon;
import com.example.api.api.producer.CouponCreateProducer;
import com.example.api.api.repository.AppliedUserRepository;
import com.example.api.api.repository.CouponCountRepository;
import com.example.api.api.repository.CouponRepository;

@Service
public class ApplyService {
    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;
    private final AppliedUserRepository appliedUserRepository;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer, AppliedUserRepository appliedUserRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
        this.appliedUserRepository = appliedUserRepository;
    }

    public void applyV1(Long userId){
        // 쿠폰의 갯수를 가져온 후
        long count = couponRepository.count();

        // 발급이 불가능한 경우 확인
        if(count > 100) {
            return;
        }

        // 쿠폰 발급처리
        couponRepository.save(new Coupon(userId));
    }

    public void applyV2(Long userId){
        // redis에서 1 증가 
        Long count = couponCountRepository.increment();

        // 발급이 불가능한 경우 확인
        if(count > 100) {
            return;
        }

        // 쿠폰 발급처리
        couponRepository.save(new Coupon(userId));
    }

    public void applyV3(Long userId){
        // 추가 갯수가 1이 아니라면 이미 발급받은 유저임
        Long apply = appliedUserRepository.add(userId);
        if(apply != 1) return;

        Long count = couponCountRepository.increment();
        if(count > 100) {
            return;
        }

        // 프로듀서 사용
        couponCreateProducer.create(userId);        
    }
}
