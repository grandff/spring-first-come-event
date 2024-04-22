package com.example.api.api.service;

import org.springframework.stereotype.Service;

import com.example.api.api.domain.Coupon;
import com.example.api.api.repository.CouponRepository;

@Service
public class ApplyService {
    private final CouponRepository couponRepository;

    public ApplyService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public void apply(Long userId){
        // 쿠폰의 갯수를 가져온 후
        long count = couponRepository.count();

        // 발급이 불가능한 경우 확인
        if(count > 100) {
            return;
        }

        // 쿠폰 발급처리
        couponRepository.save(new Coupon(userId));
    }
}
