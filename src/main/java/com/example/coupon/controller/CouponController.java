package com.example.coupon.controller;

import com.example.coupon.dto.CouponDeleteRequestDto;
import com.example.coupon.service.CouponService;
import com.example.coupon.dto.CouponSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<String>> registerCoupon(@Valid @ModelAttribute CouponSaveRequestDto requestDto) {
        return ResponseEntity.status(201).body(couponService.registerCoupon(requestDto));
    }

    @PostMapping(value = "/bulk", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<String>> bulkRegisterCoupon(@Valid @ModelAttribute CouponSaveRequestDto requestDto) {
        return ResponseEntity.status(201).body(couponService.bulkRegisterCoupon(requestDto));
    }

    @DeleteMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void deleteCoupon(CouponDeleteRequestDto couponDeleteRequestDto) {
        couponService.deleteCoupon(couponDeleteRequestDto);
    }

}
