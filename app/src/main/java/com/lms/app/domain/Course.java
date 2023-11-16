package com.lms.app.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class Course {
    int id;
    @Min(value = 1, message = "Semester should be a positive integer")
    int semester;
    @NotEmpty(message = "Course title cannot be empty")
    String name;
    @NotEmpty(message = "Course thumbnail cannot be empty")
    String thumbnail;
    Boolean published;
    // LocalDateTime createdAt;
    // LocalDateTime lastUpdatedAt;
}
