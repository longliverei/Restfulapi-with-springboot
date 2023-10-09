package com.rei.controllers;

import com.rei.models.dto.StudentDto;
import com.rei.services.StudentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students/v1")
public class StudentsController {

    private final StudentsService service;

    public StudentsController(StudentsService service) {
        this.service = service;
    }

    @GetMapping
    public List<StudentDto> findAll() {

        return service.findAll();

    }

    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable("id") Long id) {

        return service.findById(id);

    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentDto studentDto) {

        return service.create(studentDto);

    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody StudentDto studentDto) {

        return service.update(studentDto);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        return service.delete(id);

    }

}
