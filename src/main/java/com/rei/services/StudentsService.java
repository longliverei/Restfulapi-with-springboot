package com.rei.services;

import com.rei.exceptions.ResourceNotFoundException;
import com.rei.mappers.StudentsMapper;
import com.rei.models.dto.StudentDto;
import com.rei.models.entity.Student;
import com.rei.repository.StudentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsService {

    private final StudentsRepository repository;

    private final StudentsMapper mapper;

    public StudentsService(StudentsRepository repository, StudentsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<StudentDto> findAll() {

        List<Student> entityList = repository.findAll();

        return mapper.entityListToDtoList(entityList);

    }

    public StudentDto findById(Long id) {

        Student entity = repository.findById(id)
                                    .orElseThrow(()-> new ResourceNotFoundException("No records found for this id."));

        return mapper.entityToDto(entity);
    }

    public StudentDto create(StudentDto studentDto) {

        Student entity = mapper.dtoToEntity(studentDto);

        return mapper.entityToDto(repository.save(entity));

    }

    public StudentDto update(StudentDto studentDto) {

        Student entity = repository.findById(studentDto.getId())
                                    .orElseThrow(()-> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(studentDto.getFirstName());
        entity.setLastName(studentDto.getLastName());
        entity.setEmail(studentDto.getEmail());
        entity.setCourse(studentDto.getCourse());

        return mapper.entityToDto(repository.save(entity));

    }

    public void delete(Long id) {

        Student entity = repository.findById(id)
                                    .orElseThrow(()-> new ResourceNotFoundException("No records found for this id."));

        repository.delete(entity);

    }

}
