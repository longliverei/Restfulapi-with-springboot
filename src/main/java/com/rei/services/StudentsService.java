package com.rei.services;

import com.rei.mappers.StudentsMapper;
import com.rei.models.dto.StudentDto;
import com.rei.models.entity.Student;
import com.rei.repository.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentsService {

    @Autowired
    private StudentsRepository repository;

    @Autowired
    private StudentsMapper mapper;

    public List<StudentDto> findAll() {

        List<Student> entityList = repository.findAll();

        return mapper.entityListToDtoList(entityList);

    }

    public StudentDto findById(Long id) {

        Student entity = repository.findById(id).orElseThrow();

        return mapper.entityToDto(entity);
    }

    public StudentDto create(StudentDto studentDto) {

        Student entity = mapper.dtoToEntity(studentDto);

        return mapper.entityToDto(repository.save(entity));

    }

    public StudentDto update(StudentDto studentDto) {

        Student entity = mapper.dtoToEntity(studentDto);

        entity.setFirstName(studentDto.getFirstName());
        entity.setLastName(studentDto.getLastName());
        entity.setEmail(studentDto.getEmail());
        entity.setCourse(studentDto.getCourse());

        return mapper.entityToDto(repository.save(entity));
    }

    public void delete(Long id) {

        Student entity = repository.findById(id).orElseThrow();

        repository.delete(entity);

    }

}
