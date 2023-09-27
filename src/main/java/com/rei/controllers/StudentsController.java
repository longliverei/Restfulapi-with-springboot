package com.rei.controllers;

import com.rei.models.dto.StudentDto;
import com.rei.services.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/students/v1")
public class StudentsController {

    @Autowired
    private StudentsService service;

    @GetMapping
    public List<StudentDto> findAll() {

        return service.findAll();

    }

    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable("id") Long id) {

        return service.findById(id);

    }

    @PostMapping
    public StudentDto create(@RequestBody StudentDto studentDto) {

        return service.create(studentDto);

    }

    @PutMapping
    public StudentDto update(@RequestBody StudentDto studentDto) {

        return service.update(studentDto);

    }

    @DeleteMapping("/{id}")
    public void delete(Long id) {

        service.delete(id);

    }

}
