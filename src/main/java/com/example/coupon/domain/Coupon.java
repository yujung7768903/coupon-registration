package com.example.coupon.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;

    private Integer discount;

    private CouponScope scope;

    private LocalDate expirationStartDate;

    private LocalDate expirationEndDate;

    private String description;


    @Builder
    public Coupon(
            String code,
            Integer discount,
            CouponScope scope,
            LocalDate expirationStartDate,
            LocalDate expirationEndDate,
            String description
    ) {
        this.code = code;
        this.discount = discount;
        this.scope = scope;
        this.expirationStartDate = expirationStartDate;
        this.expirationEndDate = expirationEndDate;
        this.description = description;
    }

}
