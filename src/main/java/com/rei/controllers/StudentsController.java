package com.rei.controllers;

import com.rei.models.dto.StudentsDto;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/students/v1")
public class StudentsController {

    public List<StudentsDto> findAll() {

        return new ArrayList<>();

    }

    public StudentsDto findById(Long id) {

        return new StudentsDto();

    }

    public StudentsDto create(StudentsDto studentsDto) {

        return studentsDto;

    }

    public StudentsDto update(StudentsDto studentsDto) {

        return studentsDto;

    }

    public void delete(Long id) {}

}
