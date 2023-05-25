package com.example.coupon.service;

import com.example.coupon.domain.CouponRepository;
import com.example.coupon.dto.CouponSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public void registerCoupon(CouponSaveRequestDto requestDto) {

    }

}
