package com.example.coupon.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CouponScope {

    ALL("전체"),
    CLOTHES("의류"),
    BEUTY("화장품"),
    SHOES("신발"),
    LIFE("생활용품"),
    KIDS("유아용"),
    FASHION_ACCESSORIES("패션 소품");
    private String title;

}
