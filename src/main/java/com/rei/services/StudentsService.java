package com.rei.services;

import com.rei.controllers.StudentsController;
import com.rei.exceptions.ResourceNotFoundException;
import com.rei.mappers.StudentsMapper;
import com.rei.models.dto.StudentDto;
import com.rei.models.entity.Student;
import com.rei.repository.StudentsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class StudentsService {

    private final StudentsRepository repository;

    private final StudentsMapper mapper;

    private final PagedResourcesAssembler<StudentDto> assembler;

    public StudentsService(StudentsRepository repository, StudentsMapper mapper, PagedResourcesAssembler<StudentDto> assembler) {
        this.repository = repository;
        this.mapper = mapper;
        this.assembler = assembler;
    }

    public PagedModel<EntityModel<StudentDto>> findAll(Pageable pageable) {
        Page<Student> entityList = repository.findAll(pageable);

        Page<StudentDto> dtoList = entityList.map(mapper::entityToDto);
        dtoList.map(dto -> dto.add(linkTo(methodOn(StudentsController.class).findById(dto.getId())).withSelfRel()));

        Link link = linkTo(methodOn(StudentsController.class).findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    public StudentDto findById(Long id) {

        Student entity = repository.findById(id)
                                    .orElseThrow(()-> new ResourceNotFoundException("No records found for this id."));

        StudentDto dto = mapper.entityToDto(entity);
        dto.add(linkTo(methodOn(StudentsController.class).findById(dto.getId())).withSelfRel());

        return dto;

    }

    public ResponseEntity<?> create(StudentDto studentDto) {

        Student entity = mapper.dtoToEntity(studentDto);

        StudentDto dto = mapper.entityToDto(repository.save(entity));
        dto.add(linkTo(methodOn(StudentsController.class).findById(dto.getId())).withSelfRel());

        return ResponseEntity.created(linkTo(methodOn(StudentsController.class).findById(dto.getId())).withSelfRel().toUri()).build();

    }

    public ResponseEntity<?> update(StudentDto studentDto) {

        Student entity = repository.findById(studentDto.getId())
                                    .orElseThrow(()-> new ResourceNotFoundException("No records found for this id."));

        entity.setFirstName(studentDto.getFirstName());
        entity.setLastName(studentDto.getLastName());
        entity.setEmail(studentDto.getEmail());
        entity.setCourse(studentDto.getCourse());

        StudentDto dto = mapper.entityToDto(repository.save(entity));
        dto.add(linkTo(methodOn(StudentsController.class).findById(dto.getId())).withSelfRel());

        return ResponseEntity.noContent().build();

    }

    public ResponseEntity<?> disablePerson(Long id) {

        Student entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id."));

        repository.disablePerson(entity.getId());

        return ResponseEntity.noContent().build();

    }

    public ResponseEntity<?> delete(Long id) {

        Student entity = repository.findById(id)
                                    .orElseThrow(()-> new ResourceNotFoundException("No records found for this id."));

        repository.delete(entity);

        return ResponseEntity.noContent().build();

    }

}
