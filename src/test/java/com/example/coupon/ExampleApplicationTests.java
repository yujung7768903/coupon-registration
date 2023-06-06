package com.example.coupon;

import com.example.coupon.domain.CouponScope;
import com.example.coupon.dto.CouponSaveRequestDto;
import com.example.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class ExampleApplicationTests {

	@Autowired
	private CouponService couponService;

	@Test
	@Transactional
	@DisplayName("데이터베이스에 중복된 코드 존재시 에러 발생")
	void duplicateCodeInDatabase() throws Exception {
		// given
		String fileName = "데이터베이스내_코드중복체크용.csv";
		String path = "src/test/resources/" + fileName;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		MockMultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(path));
		CouponSaveRequestDto couponSaveRequestDto = CouponSaveRequestDto.builder()
				.csvFile(multipartFile)
				.discount(50)
				.scope(CouponScope.ALL)
				.expirationStartDate(LocalDateTime.parse("2023-05-27T00:00:00", dateTimeFormatter))
				.expirationEndDate(LocalDateTime.parse("2023-06-25T00:00:00", dateTimeFormatter))
				.description("전품목 50% 할인 쿠폰")
				.build();

		// when
		couponService.registerCoupon(couponSaveRequestDto);

		// then
		assertThrows(IllegalArgumentException.class, () -> couponService.registerCoupon(couponSaveRequestDto));
	}

	@Test
	@Transactional
	@DisplayName("1000개의 쿠폰 등록시 소요 시간 체크")
	void checkRegisterCouponTime1000() throws Exception {
		// given
		String fileName = "일괄등록_쿠폰_1000개.csv";
		String path = "src/test/resources/" + fileName;
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

		MockMultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(path));
		Instant start = Instant.now();
		CouponSaveRequestDto couponSaveRequestDto = CouponSaveRequestDto.builder()
				.csvFile(multipartFile)
				.discount(50)
				.scope(CouponScope.ALL)
				.expirationStartDate(LocalDateTime.parse("2023-05-27T00:00:00", dateTimeFormatter))
				.expirationEndDate(LocalDateTime.parse("2023-06-25T00:00:00", dateTimeFormatter))
				.description("전품목 50% 할인 쿠폰")
				.build();

		// when
		couponService.registerCoupon(couponSaveRequestDto);

		// then
		Instant end = Instant.now();
		log.info("1000개 쿠폰 등록 소요 시간: {}ms", Duration.between(start, end).toMillis());
	}

}
