package com.example.coupon.controller;

import com.example.coupon.service.CouponService;
import com.example.coupon.dto.CouponSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon")
    public ResponseEntity<String> registerCoupon(@Valid @RequestBody CouponSaveRequestDto requestDto) {
        couponService.registerCoupon(requestDto);
        return ResponseEntity.status(201).body("쿠폰 등록이 완료되었습니다.");
    }

}
