package com.lms.app.controller;

import static java.time.LocalTime.now;
import static java.util.Map.of;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lms.app.domain.HttpResponse;
import com.lms.app.domain.Staff;
import com.lms.app.dto.StaffDTO;
import com.lms.app.service.StaffService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/staff")
@RequiredArgsConstructor
public class StaffController {
    
    private final StaffService staffService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> sav(@RequestBody @Valid Staff staff) {
        StaffDTO staffDto = staffService.createStaff(staff);
        return ResponseEntity.created(getUri(staffDto.getId())).body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("staff", staffDto))
            .message("Staff created")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build()
        );
    }

    private URI getUri(int studentId) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student/get" + studentId).toString());
    }
}
