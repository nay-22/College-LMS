package com.lms.app.controller;

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
import com.lms.app.domain.Student;
import com.lms.app.dto.StudentDTO;
import com.lms.app.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static java.time.LocalTime.now;
import static java.util.Map.of;

@RestController
@RequestMapping(path = "/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService stuService;

    @GetMapping("/get")
    public ResponseEntity<HttpResponse> listStudents(
        @RequestParam(defaultValue = "0") int pageIndex,
        @RequestParam(defaultValue = "5") int pageSize) {
        List<StudentDTO> studentDTO = stuService.getStudents(pageIndex*pageSize, pageSize);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("student", studentDTO))
            .message("Student List")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getStudent(@PathVariable int id) {
        StudentDTO student = stuService.getStudent(id);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("student", student))
            .message("Student with id = " + id)
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveStudent(@RequestBody @Valid Student student) {
        StudentDTO stuDto = stuService.createStudent(student);
        return ResponseEntity.created(getUri(stuDto.getId())).body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("student", stuDto))
            .message("Student created")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build()
        );
    }

    @PutMapping("/edit{id}")
    public ResponseEntity<HttpResponse> updateStudent(@RequestParam int id, @RequestBody Student student) {
        StudentDTO stuDTO = stuService.updateStudent(id, student);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("student", stuDTO))
            .message("Student Updated")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build()
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpResponse> deleteStudent(@RequestParam int id) {
        boolean deleted = stuService.deleteStudent(id);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .message("Student deleted: " + deleted)
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }



    private URI getUri(int studentId) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/student/get" + studentId).toString());
    }
}
