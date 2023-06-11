package com.example.coupon.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<Coupon> couponList) {
        String sql = "INSERT INTO COUPON(code, discount, scope, expiration_start_date, expiration_end_date, description) "
                + "VALUE(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, couponList.get(i).getCode());
                ps.setInt(2, couponList.get(i).getDiscount());
                ps.setString(3, couponList.get(i).getScope().name());
                ps.setTimestamp(4, Timestamp.valueOf(couponList.get(i).getExpirationStartDate()));
                ps.setTimestamp(5, Timestamp.valueOf(couponList.get(i).getExpirationEndDate()));
                ps.setString(6, couponList.get(i).getDescription());
            }

            @Override
            public int getBatchSize() {
                return couponList.size();
            }
        });
    }
}
