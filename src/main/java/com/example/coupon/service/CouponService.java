package com.example.coupon.service;

import com.example.coupon.domain.CouponRepository;
import com.example.coupon.dto.CouponSaveRequestDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public List<String[]> registerCoupon(CouponSaveRequestDto requestDto) {
        requestDto.getCsvFile();
        try(InputStreamReader inputStreamReader = new InputStreamReader(requestDto.getCsvFile().getInputStream())) {
            try (CSVReader csvReader = new CSVReader(inputStreamReader)) {
                return csvReader.readAll();
            } catch (CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
