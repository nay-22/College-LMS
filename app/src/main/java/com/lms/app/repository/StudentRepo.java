package com.lms.app.repository;

import java.util.List;

import com.lms.app.domain.Student;
import com.lms.app.dto.StudentDTO;

public interface StudentRepo <T extends Student> {
    T create(T data);
    List<StudentDTO> list(int page, int pageSize);
    StudentDTO get(int id);
    StudentDTO update(int id, T data);
    int delete(int id);
}
