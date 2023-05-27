package com.example.coupon.controller;

import com.example.coupon.service.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @Test
    public void registerCouponTest() {
        // given
        given(couponService.registerCoupon(any())).willReturn(new ArrayList<>());
        String fileName = "일괄등록_쿠폰_1000개.csv";
        String path = "src/test/resources/" + fileName;

        try {
            MockMultipartFile multipartFile = new MockMultipartFile(fileName, new FileInputStream(path));

            // when
            ResultActions resultActions = mockMvc.perform(multipart("/coupon")
                    .file(multipartFile)
                    .param("discount", "50")
                    .param("scope", "ALL")
                    .param("description", "전품목 50% 할인 쿠폰")
                    .param("expirationStartDate", "2023-05-27T00:00:00")
                    .param("expirationEndDate", "2023-06-27T00:00:00")
            );

            // then
            resultActions.andExpect(status().isCreated());
        } catch (FileNotFoundException e) {
            log.info("파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            log.info("MultipartFile 생성 중 문제가 발생했습니다.");
        } catch (SecurityException e) {
            log.info("파일에 대한 읽기 권한이 없습니다.");
        } catch (Exception e) {
            log.info("Controller 테스트 중 문제가 발생했습니다.");
        }
    }

}
