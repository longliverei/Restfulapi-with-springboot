package com.rei.controllers;

import com.rei.models.dto.StudentDto;
import com.rei.services.StudentsService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students/v1")
public class StudentsController {

    private final StudentsService service;

    public StudentsController(StudentsService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<StudentDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public StudentDto findById(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    @GetMapping("/findByName/{firstName}")
    public ResponseEntity<PagedModel<EntityModel<StudentDto>>> findStudentByName(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @PathVariable("firstName") String firstName
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        return ResponseEntity.ok(service.findStudentByName(firstName, pageable));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StudentDto studentDto) { return service.create(studentDto); }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody StudentDto studentDto) { return service.update(studentDto); }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> disablePerson(@PathVariable("id") Long id) {
        return service.disablePerson(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return service.delete(id);
    }

}
