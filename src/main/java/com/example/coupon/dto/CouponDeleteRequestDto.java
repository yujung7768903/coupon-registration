package com.example.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class CouponDeleteRequestDto {

    private MultipartFile csvFile;

}