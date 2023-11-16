package com.lms.app.dto.dtomapper;

import org.springframework.beans.BeanUtils;

import com.lms.app.domain.Staff;
import com.lms.app.domain.Student;
import com.lms.app.dto.StaffDTO;
import com.lms.app.dto.StudentDTO;

public class DTOMapper {
    public static StudentDTO fromStudent(Student student) {
        StudentDTO dto = new StudentDTO();
        BeanUtils.copyProperties(student, dto);
        return dto;
    }
    public static Student toStudent(StudentDTO dto) {
        Student student = new Student();
        BeanUtils.copyProperties(dto, student);
        return student;
    }
    public static StaffDTO fromStaff(Staff staff) {
        StaffDTO dto = new StaffDTO();
        BeanUtils.copyProperties(staff, dto);
        return dto;
    }
    public static Staff toStaff(StaffDTO dto) {
        Staff staff = new Staff();
        BeanUtils.copyProperties(dto, staff);
        return staff;
    }
}
