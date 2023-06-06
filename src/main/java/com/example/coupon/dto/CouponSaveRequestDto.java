package com.example.coupon.dto;

import com.example.coupon.domain.Coupon;
import com.example.coupon.domain.CouponScope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
public class CouponSaveRequestDto {

    @NotNull(message = "쿠폰 코드 파일(csvFile)은 null이 될 수 없습니다.")
    private MultipartFile csvFile;

    @NotNull(message = "할인률(discount)는 null이 될 수 없습니다.")
    private Integer discount;

    @NotNull(message = "쿠폰 적용 범위(scope)는 null이 될 수 없습니다.")
    private CouponScope scope;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expirationStartDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expirationEndDate;

    private String description;

    public Coupon toCoupon(String code) {
        return Coupon.builder()
                .code(code)
                .discount(discount)
                .scope(scope)
                .expirationStartDate(expirationStartDate)
                .expirationEndDate(expirationEndDate)
                .description(description)
                .build();
    }

}
