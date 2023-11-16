package com.lms.app.repository.implementation;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.lms.app.domain.Staff;
import com.lms.app.dto.StaffDTO;
import com.lms.app.exception.ApiException;
import com.lms.app.repository.StaffRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.lms.app.query.StaffQuery.*;
import static java.util.Map.of;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StaffRepoImpl implements StaffRepo<Staff> {
    private final NamedParameterJdbcTemplate jdbc;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Staff create(Staff staff) {
        if (getEmailCount(staff.getEmail().trim().toLowerCase()) > 0) {
            throw new ApiException("Email already in use. Please register with a different email");
        }
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource params = getStaffSqlParameterSource(staff);
            jdbc.update(INSERT_INTO_STAFF_QUERY, params, holder);
            staff.setId(holder.getKey().intValue());
            return staff;
        } catch (ApiException e) {
            throw new ApiException("Something went wrong");
        }
    }

    @Override
    public List list(int page, int pageSize) {
        
        throw new UnsupportedOperationException("Unimplemented method 'list'");
    }

    @Override
    public StaffDTO get(int id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public StaffDTO update(int id, Staff data) {
        
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public int delete(int id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }




    private SqlParameterSource getStaffSqlParameterSource(Staff stu) {
        return new MapSqlParameterSource()
        .addValue("empNo", stu.getEmpNo())
        .addValue("firstName", stu.getFirstName())
        .addValue("lastName", stu.getLastName())
        .addValue("email", stu.getEmail())
        .addValue("password", encoder.encode(stu.getPassword()));
    }


    private SqlParameterSource getStaffSqlParameterSource(Staff stu, int id) {
        return new MapSqlParameterSource()
        .addValue("id", id)
        .addValue("firstName", stu.getFirstName())
        .addValue("middleName", stu.getMiddleName())
        .addValue("lastName", stu.getLastName())
        .addValue("phone", stu.getPhone())
        .addValue("dob", stu.getDob())
        .addValue("address", stu.getAddress());
    }
    
    private SqlParameterSource getPagSqlParameterSource(int pageIndex, int pageSize) {
        return new MapSqlParameterSource()
        .addValue("limit", pageSize)
        .addValue("offset", pageIndex);
    }
    
    private int getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email", email), Integer.class);
    }

    private SqlParameterSource getStaffIdSqlParameterSource(int id) {
        return new MapSqlParameterSource()
        .addValue("id", id);
    }
    
}
