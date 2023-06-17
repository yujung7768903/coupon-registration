package com.example.coupon;

import com.example.coupon.domain.CouponScope;
import com.example.coupon.dto.CouponDeleteRequestDto;
import com.example.coupon.dto.CouponSaveRequestDto;
import com.example.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@ActiveProfiles("dev")
class ExampleApplicationTests {

	@Autowired
	private CouponService couponService;

	@Value("${spring.datasource.url}")
	private String url;

	@Test
	@Transactional
	@DisplayName("데이터베이스에 중복된 코드 존재시 에러 발생")
	void duplicateCodeInDatabase() throws Exception {
		// given
		String fileName = "데이터베이스내_코드중복체크용.csv";
		String path = "src/test/resources/" + fileName;
		MockMultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(path));
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

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
	@DisplayName("단건 등록시 소요 시간 체크")
	void checkRegisterCouponTime() throws Exception {
		// given
		String fileName = "일괄등록_쿠폰_10000개.csv";
		String path = "src/test/resources/" + fileName;
		MockMultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(path));

		long timeSum = 0;
		long n = 5;

		log.info("쿠폰 단건 등록 시작");
		// when
		for (int i = 0; i < n; i++) {
			deleteCoupon(multipartFile);
			long executionTime = registerCoupon(multipartFile, false);
			timeSum += executionTime;
			log.info("{}번째 쿠폰 등록 소요 시간: {}ms", i, executionTime);
		}

		// then
		log.info("Database url: {}", url);
		log.info("쿠폰 등록 파일 이름: {}", fileName);
		log.info("단건 등록 평균 시간: {}ms", timeSum / n);
	}

	@Test
	@DisplayName("일괄 등록시 소요 시간 체크")
	void checkbulkRegisterCouponTime() throws Exception {
		// given
		String fileName = "일괄등록_쿠폰_10000개.csv";
		String path = "src/test/resources/" + fileName;
		MockMultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(path));

		long timeSum = 0;
		long n = 1;

		log.info("쿠폰 일괄 등록 시작");
		// when
		for (int i = 0; i < n; i++) {
			deleteCoupon(multipartFile);
			long executionTime = registerCoupon(multipartFile, true);
			timeSum += executionTime;
			log.info("{}번째 쿠폰 등록 소요 시간: {}ms", i, executionTime);
		}

		// then
		log.info("Database url: {}", url);
		log.info("쿠폰 등록 파일 이름: {}", fileName);
		log.info("일괄 등록 평균 시간(일괄 등록): {}ms", timeSum / 5);
	}

	long registerCoupon(MockMultipartFile multipartFile, boolean isBulkRegistration) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		Instant start = Instant.now();

		CouponSaveRequestDto couponSaveRequestDto = CouponSaveRequestDto.builder()
				.csvFile(multipartFile)
				.discount(50)
				.scope(CouponScope.ALL)
				.expirationStartDate(LocalDateTime.parse("2023-05-27T00:00:00", dateTimeFormatter))
				.expirationEndDate(LocalDateTime.parse("2023-06-25T00:00:00", dateTimeFormatter))
				.description("전품목 50% 할인 쿠폰")
				.build();

		if (isBulkRegistration) {
			couponService.bulkRegisterCoupon(couponSaveRequestDto);
		} else {
			couponService.registerCoupon(couponSaveRequestDto);
		}

		Instant end = Instant.now();
		return Duration.between(start, end).toMillis();
	}

	void deleteCoupon(MockMultipartFile multipartFile) {
		CouponDeleteRequestDto couponDeleteRequestDto = new CouponDeleteRequestDto(multipartFile);

		couponService.deleteCoupon(couponDeleteRequestDto);
	}

}
