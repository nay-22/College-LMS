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

import com.lms.app.domain.Course;
import com.lms.app.domain.HttpResponse;
import com.lms.app.service.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> saveCourse(@RequestBody @Valid Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.created(getUri(createdCourse.getId())).body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("course", createdCourse))
            .message("Course created")
            .status(HttpStatus.CREATED)
            .statusCode(HttpStatus.CREATED.value())
            .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getCourse(@PathVariable int id) {
        Course course = courseService.getCourse(id);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("course", course))
            .message("Course fetched")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @GetMapping("/get")
    public ResponseEntity<HttpResponse> listCourses(
        @RequestParam(defaultValue = "0") int pageIndex,
        @RequestParam(defaultValue = "10") int pageSize) {
        List<Course> courses = courseService.listCourses(pageIndex, pageSize);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("courses", courses))
            .message("Courses fetched")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HttpResponse> editCourse(@PathVariable int id, @RequestBody @Valid Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .data(of("Updated Course", updatedCourse))
            .message("Course updated")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    @DeleteMapping("/delete")
    public ResponseEntity<HttpResponse> deleteCourse(@RequestParam int id) {
        boolean deleted = courseService.deleteCourse(id);
        return ResponseEntity.ok().body(
            HttpResponse.builder()
            .timeStamp(now().toString())
            .message("Course deleted: " + deleted)
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build()
        );
    }

    private URI getUri(int id) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/course/get/" + id).toString());
    }
}
