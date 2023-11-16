package com.lms.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.app.domain.Course;
import com.lms.app.repository.CourseRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepo<Course> courseRepo;

    public Course createCourse(Course course) {
        return courseRepo.create(course);
    }

    public Course getCourse(int id) {
        return courseRepo.get(id);
    }

    public List<Course> listCourses(int pageIndex, int pageSize) {
        return courseRepo.list(pageIndex, pageSize);
    }

    public Course updateCourse(int id, Course course) {
        return courseRepo.update(id, course);
    }

    public boolean deleteCourse(int id) {
        return (courseRepo.delete(id) > 0) ? true : false;
    }
}
