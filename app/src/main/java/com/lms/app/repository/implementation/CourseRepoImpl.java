package com.lms.app.repository.implementation;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.lms.app.domain.Course;
import com.lms.app.exception.ApiException;
import com.lms.app.exception.ResourceNotFoundException;
import com.lms.app.repository.CourseRepo;
import com.lms.app.repository.rowmapper.CourseRowMapper;

import static com.lms.app.query.CourseQuery.*;
import static com.lms.app.repository.utils.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CourseRepoImpl implements CourseRepo<Course> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Course create(Course course) {
        try {
            SqlParameterSource params = getCourseParams(course);
            KeyHolder holder = new GeneratedKeyHolder();
            jdbc.update(INSERT_INTO_COURSE_QUERY, params, holder);
            course.setId(holder.getKey().intValue());
            course.setPublished(false);
            return course;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Something went wrong");
        }
    }

    @Override
    public Course get(int id) {
        try {
            SqlParameterSource params = getIdParams(id);
            return jdbc.queryForObject(SELECT_COURSE_WHERE_ID_QUERY, params, new CourseRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Course with id: " + id + " does not exist.");
        } catch (Exception e) {
            throw new ApiException("Something went wrong");
        }
    }

    @Override
    public List<Course> list(int pageIndex, int pageSize) {
        try {
            SqlParameterSource params = getPaginationParams(pageIndex, pageSize);
            return jdbc.query(SELECT_ALL_FROM_COURSE_QUERY, params, new CourseRowMapper());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Something went wrong");
        }
    }

    @Override
    public Course update(int id, Course course) {
        try {
            SqlParameterSource params = getCourseParams(id, course);
            int updatedRows = jdbc.update(UPDATE_COURSE_QUERY, params);
            if (updatedRows == 0) {
                throw new ResourceNotFoundException("Course with id: " + id + " does not exist.");
            }
            return get(id);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Something went wrong");
        }
    }

    @Override
    public int delete(int id) {
        try {
            SqlParameterSource params = getIdParams(id);
            int deleted = jdbc.update(DELETE_COURSE_QUERY, params);
            if (deleted == 0) {
                throw new ResourceNotFoundException("Course with id: " + id + " does not exist.");
            }
            return deleted;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ApiException("Something went wrong");
        }
    }

    private SqlParameterSource getCourseParams(Course course) {
        return new MapSqlParameterSource()
        .addValue("name", course.getName())
        .addValue("thumbnail", course.getThumbnail())
        .addValue("semester", course.getSemester());
    }

    private SqlParameterSource getCourseParams(int id, Course course) {
        return new MapSqlParameterSource()
        .addValue("id", id)
        .addValue("name", course.getName())
        .addValue("thumbnail", course.getThumbnail())
        .addValue("semester", course.getSemester());
    }

    private SqlParameterSource getIdParams(int id) {
        return new MapSqlParameterSource().addValue("id", id);
    }
    
}
