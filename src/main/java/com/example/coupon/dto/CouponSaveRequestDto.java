package com.example.coupon.dto;

import com.example.coupon.domain.CouponScope;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CouponSaveRequestDto {

    private MultipartFile csvFile;

    private String code;

    private Integer discount;

    private String email;

    private CouponScope scope;


    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDate expirationStartDate;

    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDate expirationEndDate;

    private String description;

}
