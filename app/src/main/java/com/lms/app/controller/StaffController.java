package com.lms.app.controller;

import static java.time.LocalTime.now;
import static java.util.Map.of;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<HttpResponse> saveStaff(@RequestBody @Valid Staff staff) {
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

    @GetMapping("/get")
    public ResponseEntity<HttpResponse> listStaff(
        @RequestParam(defaultValue = "0") int pageIndex,
        @RequestParam(defaultValue = "5") int pageSize) {
        List<StaffDTO> staffDto = staffService.getStaffs(pageIndex*pageSize, pageSize);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("Staffs", staffDto))
            .message("Staff List")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getStaff(@PathVariable int id) {
        StaffDTO staff = staffService.getStaff(id);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("Staff", staff))
            .message("Staff with id = " + id)
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @PutMapping("/edit{id}")
    public ResponseEntity<HttpResponse> updateStaff(@RequestParam int id, @RequestBody Staff staff) {
        StaffDTO staffDto = staffService.updateStaff(id, staff);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("student", staffDto))
            .message("Student Updated")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build()
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpResponse> deleteStudent(@RequestParam int id) {
        boolean deleted = staffService.deleteStaff(id);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .message("Staff deleted: " + deleted)
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }





    private URI getUri(int studentId) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student/get" + studentId).toString());
    }
}
