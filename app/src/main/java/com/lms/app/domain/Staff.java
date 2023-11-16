package com.lms.app.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class Staff {
    private int id;
    private String empNo;
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    private String middleName;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    private String phone;
    @NotEmpty(message = "Email cannot be empty")
    private String email;
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    private String address;
    private LocalDateTime dob;
    private LocalDateTime createdAt;
}
