package com.lms.app.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StaffDTO {
    private int id;
    private int empNo;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private LocalDateTime dob;
    private LocalDateTime createdAt;
}
