package com.lms.app.repository.implementation;

import com.lms.app.domain.Student;
import com.lms.app.dto.StudentDTO;
import com.lms.app.exception.ApiException;
import com.lms.app.exception.ResourceNotFoundException;
import com.lms.app.repository.StudentRepo;
import com.lms.app.repository.rowmapper.StudentRowMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

import static com.lms.app.query.StudentQuery.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class StudentRepoImpl implements StudentRepo<Student> {
    private final NamedParameterJdbcTemplate jdbc;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Student create(Student stu) {
        if (getEmailCount(stu.getEmail().trim().toLowerCase()) > 0) {
            throw new ApiException("Email already in use. Please register with a different email");
        }
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource params = getStudentSqlParameterSource(stu);
            jdbc.update(INSERT_INTO_STUDENT_QUERY, params, holder);
            stu.setCreatedAt(now());
            stu.setId(holder.getKey().intValue());
            return stu;
        } catch (ApiException e) {
            log.error(e.getMessage());
            throw new ApiException("Something went wrong");
        }
    }

    
    @Override
    public List<StudentDTO> list(int pageIndex, int pageSize) {
        try {
            SqlParameterSource params = getPagSqlParameterSource(pageIndex, pageSize);
            List<StudentDTO> students =  jdbc.query(SELECT_ALL_FROM_STUDENT_QUERY, params, new StudentRowMapper());
            return students;
        } catch (Exception e) {
            throw new ApiException("Something went wrong");
        }
    }

    @Override
    public StudentDTO get(int id) {
        try {
            SqlParameterSource params = getStudentIdSqlParameterSource(id);
            StudentDTO student =  jdbc.queryForObject(SELECT_STUDENT_WHERE_ID_QUERY, params, new StudentRowMapper());
            return student;
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Student with id: " + id + " does not exist.");
        } catch (Exception e) {
            throw new ApiException("Something went wrong");
        }
    }
    
    
    @Override
    public StudentDTO update(int id, Student student) {
        try {
            SqlParameterSource params = getStudentSqlParameterSource(student, id);
            int updatedRows = jdbc.update(UPDATE_STUDENT_PROFILE_QUERY, params);
            if (updatedRows == 0) {
                throw new ResourceNotFoundException("Student with id: " + id + " does not exist.");
            }
            return get(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
    
    @Override
    public int delete(int id) {
        try {
            SqlParameterSource params = getStudentIdSqlParameterSource(id);
            int deletedRows = jdbc.update(DELETE_STUDENT_QUERY, params);
            if (deletedRows == 0) {
                throw new ResourceNotFoundException("Student with id: " + id + " does not exist.");
            }
            return deletedRows;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("Something went wrong");
        }
    }



    // private helper functions
    
    private SqlParameterSource getStudentSqlParameterSource(Student stu) {
        return new MapSqlParameterSource()
        .addValue("semester", stu.getSemester())
        .addValue("rollNo", stu.getRollNo())
        .addValue("firstName", stu.getFirstName())
        .addValue("lastName", stu.getLastName())
        .addValue("email", stu.getEmail())
        .addValue("password", encoder.encode(stu.getPassword()));
    }


    private SqlParameterSource getStudentSqlParameterSource(Student stu, int id) {
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

    private SqlParameterSource getStudentIdSqlParameterSource(int id) {
        return new MapSqlParameterSource()
        .addValue("id", id);
    }
}
