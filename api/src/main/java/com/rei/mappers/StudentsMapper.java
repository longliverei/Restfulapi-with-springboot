package com.rei.mappers;

import com.rei.models.dto.StudentDto;
import com.rei.models.entity.Student;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentsMapper {

    StudentDto entityToDto(Student student);
    Student dtoToEntity(StudentDto studentDto);
    List<StudentDto> entityListToDtoList(List<Student> students);
    List<Student> dtoListToEntityList(List<StudentDto> dtoStudents);

}
