package com.lms.app.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lms.app.domain.Staff;
import com.lms.app.dto.StaffDTO;

@Repository
public interface StaffRepo <T extends Staff> {
    T create(T data);
    List<StaffDTO> list(int page, int pageSize);
    StaffDTO get(int id);
    StaffDTO update(int id, T data);
    int delete(int id);;
}
