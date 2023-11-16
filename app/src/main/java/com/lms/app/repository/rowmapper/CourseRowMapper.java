package com.lms.app.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

import com.lms.app.domain.Course;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        // LocalDateTime createdAt = rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null;
        // LocalDateTime lastUpdatedAt = rs.getTimestamp("last_updated_at") != null ? rs.getTimestamp("last_updated_at").toLocalDateTime() : null;

        return Course.builder()
        .id(rs.getInt("id"))
        .name(rs.getString("name"))
        .thumbnail(rs.getString("thumbnail"))
        .semester(rs.getInt("semester"))
        .published(rs.getBoolean("published"))
        .createdAt(rs.getTimestamp("created_at"))
        .lastUpdatedAt(rs.getTimestamp("last_updated_at"))
        .build();

    }
    
}
