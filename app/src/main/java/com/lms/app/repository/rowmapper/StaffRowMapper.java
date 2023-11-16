package com.lms.app.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.lms.app.domain.Staff;
import com.lms.app.dto.StaffDTO;
import com.lms.app.dto.dtomapper.DTOMapper;

public class StaffRowMapper implements RowMapper<StaffDTO> {

    @Override
    public StaffDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime dob = rs.getTimestamp("dob") != null ? rs.getTimestamp("dob").toLocalDateTime() : null;
        LocalDateTime createdAt = rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null;

        return DTOMapper.fromStaff(Staff.builder()
        .id(rs.getInt("id"))
        .empNo(rs.getString("emp_no"))
        .firstName(rs.getString("first_name"))
        .middleName(rs.getString("middle_name"))
        .lastName(rs.getString("last_name"))
        .phone(rs.getString("phone"))
        .email(rs.getString("email"))
        .password(rs.getString("password"))
        .address(rs.getString("address"))
        .dob(dob)
        .createdAt(createdAt)
        .build());
    }
    
}
