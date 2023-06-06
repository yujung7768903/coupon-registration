package com.example.coupon.controller;

import com.example.coupon.dto.CouponSaveRequestDto;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.FileInputStream;
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
    public void registerCouponTest() throws Exception {
        // given
        given(couponService.registerCoupon(any(CouponSaveRequestDto.class))).willReturn(new ArrayList<>());
        String fileName = "일괄등록_쿠폰_1000개.csv";
        String path = "src/test/resources/" + fileName;
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
        MvcResult mvcResult = resultActions
                .andExpect(status().isCreated())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }

}
