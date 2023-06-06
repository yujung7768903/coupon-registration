package com.example.coupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    public Long countByCodeIn(List<String> codeList);

    public List<Coupon> findByCodeIn(List<String> codeList);

}
