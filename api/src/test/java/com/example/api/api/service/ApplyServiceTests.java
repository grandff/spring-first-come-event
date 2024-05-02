package com.example.api.api.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.api.api.repository.CouponRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class ApplyServiceTests {
    @Autowired
    private ApplyService applyService;

    @Autowired
    private CouponRepository couponRepository;

    @Test
    public void 한번만응모() {
        applyService.applyV1(1L);
        long count = couponRepository.count();

        assertThat(count).isEqualTo(1);
    }

    @Test
    public void 여러명응모() throws InterruptedException {
        int threadCount = 1000;
        // 병렬작업을 간단하게 도와주는 ExecutorService 사용 
        ExecutorService executorService =  Executors.newFixedThreadPool(32);
        // 다른 스레드에서의 작업을 기다려줌
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i= 0; i<threadCount; i++) {
            long userId = i;
            executorService.submit(() -> {
                try{                    
                    applyService.applyV3(userId);
                }finally {
                    latch.countDown();
                }                
            });
        }
        latch.await();

        Thread.sleep(10000);

        long count = couponRepository.count();
        assertThat(count).isEqualTo(100);
    }
}
