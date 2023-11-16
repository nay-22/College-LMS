package com.lms.app.repository;

import java.util.List;

import com.lms.app.domain.Course;

public interface CourseRepo <T extends Course>{
    T create(T data);
    T get(int id);
    List<T> list(int pageIndex, int pageSize);
    Course update(int id, T data);
    int delete(int id);
}
