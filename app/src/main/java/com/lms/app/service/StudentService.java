package com.lms.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.app.domain.Student;
import com.lms.app.dto.StudentDTO;
import com.lms.app.dto.dtomapper.DTOMapper;
import com.lms.app.repository.StudentRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo<Student> stuRepo;

    public StudentDTO createStudent(Student student) {
        return DTOMapper.fromStudent(stuRepo.create(student));
    }

    public List<StudentDTO> getStudents(int pageIndex, int pageSize) {
        return stuRepo.list(pageIndex, pageSize);
    }

    public StudentDTO getStudent(int id) {
        return stuRepo.get(id);
    }

    public boolean deleteStudent(int id) {
        return (stuRepo.delete(id) > 0) ? true : false;
    }

    public StudentDTO updateStudent(int id, Student student) {
        return stuRepo.update(id, student);
    }
}
