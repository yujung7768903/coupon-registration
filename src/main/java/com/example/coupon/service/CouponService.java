package com.example.coupon.service;

import com.example.coupon.domain.Coupon;
import com.example.coupon.domain.CouponRepository;
import com.example.coupon.domain.CouponRepositoryCustom;
import com.example.coupon.dto.CouponDeleteRequestDto;
import com.example.coupon.dto.CouponSaveRequestDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponRepositoryCustom couponRepositoryCustom;

    @Transactional
    public List<String> registerCoupon(CouponSaveRequestDto requestDto) {
        try(InputStreamReader inputStreamReader = new InputStreamReader(requestDto.getCsvFile().getInputStream())) {
            try (CSVReader csvReader = new CSVReader(inputStreamReader)) {
                List<String> codeList = csvReader.readAll().stream()
                        .map(arr -> arr[0])
                        .collect(Collectors.toList());
                checkDuplicateCoupon(codeList);
                codeList.forEach(c -> couponRepository.save(requestDto.toCoupon(c)));
                return codeList;
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public List<String> bulkRegisterCoupon(CouponSaveRequestDto requestDto) {
        try(InputStreamReader inputStreamReader = new InputStreamReader(requestDto.getCsvFile().getInputStream())) {
            try (CSVReader csvReader = new CSVReader(inputStreamReader)) {
                List<String> codeList = new ArrayList<>();
                List<Coupon> couponList = new ArrayList<>();
                csvReader.readAll().stream()
                        .forEach(row -> {
                            codeList.add(row[0]);
                            couponList.add(requestDto.toCoupon(row[0]));
                        });
                checkDuplicateCoupon(codeList);
                couponRepositoryCustom.saveAll(couponList);
                return codeList;
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteCoupon(CouponDeleteRequestDto requestDto) {
        try(InputStreamReader inputStreamReader = new InputStreamReader(requestDto.getCsvFile().getInputStream())) {
            try (CSVReader csvReader = new CSVReader(inputStreamReader)) {
                List<String> codeList = csvReader.readAll().stream()
                        .map(arr -> arr[0])
                        .collect(Collectors.toList());
                couponRepository.deleteAllInBatch(couponRepository.findByCodeIn(codeList));
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public void checkDuplicateCoupon(List<String> codeList) {
        if (codeList.size() != new HashSet<>(codeList).size()) {
            throw new IllegalArgumentException("csv 파일 내 중복된 쿠폰 코드가 있습니다.");
        } else if (couponRepository.countByCodeIn(codeList) > 0) {
            throw new IllegalArgumentException("중복된 쿠폰 코드가 등록되어 있습니다.");
        }
    }

}
