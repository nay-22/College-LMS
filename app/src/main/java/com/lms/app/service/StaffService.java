package com.lms.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lms.app.domain.Staff;
import com.lms.app.dto.StaffDTO;
import com.lms.app.dto.dtomapper.DTOMapper;
import com.lms.app.repository.StaffRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepo<Staff> staffRepo;

    public StaffDTO createStaff(Staff staff) {
        return DTOMapper.fromStaff(staffRepo.create(staff));
    }

    public List<StaffDTO> getStaffs(int pageIndex, int pageSize) {
        return staffRepo.list(pageIndex, pageSize);
    }

    public StaffDTO getStaff(int id) {
        return staffRepo.get(id);
    }

    public boolean deleteStaff(int id) {
        return (staffRepo.delete(id) > 0) ? true : false;
    }

    public StaffDTO updateStaff(int id, Staff staff) {
        return staffRepo.update(id, staff);
    }
}
