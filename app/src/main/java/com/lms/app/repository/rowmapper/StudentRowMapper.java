package com.lms.app.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.lms.app.domain.Student;
import com.lms.app.dto.StudentDTO;
import com.lms.app.dto.dtomapper.DTOMapper;

public class StudentRowMapper implements RowMapper<StudentDTO> {

    @Override
    public StudentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        LocalDateTime dob = rs.getTimestamp("dob") != null ? rs.getTimestamp("dob").toLocalDateTime() : null;
        LocalDateTime createdAt = rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null;

        return DTOMapper.fromStudent(Student.builder()
        .id(rs.getInt("id"))
        .semester(rs.getInt("semester"))
        .rollNo(rs.getString("roll_no"))
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
