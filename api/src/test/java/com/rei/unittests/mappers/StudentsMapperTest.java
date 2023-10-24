package com.rei.unittests.mappers;

import com.rei.mappers.StudentsMapperImpl;
import com.rei.models.dto.StudentDto;
import com.rei.models.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentsMapperTest {

    private StudentsMapperImpl sut;

    @BeforeEach
    public void setup() {
        sut = new StudentsMapperImpl();
    }

    @Test
    public void shouldMapEntityToDto() {

        Student entity = new Student();
        entity.setId(42L);

        StudentDto dto = sut.entityToDto(entity);

        assertThat(dto)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 42L);

    }

    @Test
    public void shouldMapDtoToEntity() {

        StudentDto dto = new StudentDto();
        dto.setId(42L);

        Student entity = sut.dtoToEntity(dto);

        assertThat(entity)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 42L);

    }

    @Test
    public void shouldMapEntityListToDtoList() {

        List<Student> entities = new ArrayList<Student>();
        for (long i = 0; i < 10; i++) {
            Student entity = new Student();
            entity.setId(i);

            entities.add(entity);
        }

        List<StudentDto> dtoList = sut.entityListToDtoList(entities);

        assertThat(dtoList)
                .isNotNull();

        StudentDto dtoZero = dtoList.get(0);

        assertThat(dtoZero)
                .hasFieldOrPropertyWithValue("id", 0L);

        StudentDto dtoFive = dtoList.get(5);

        assertThat(dtoFive)
                .hasFieldOrPropertyWithValue("id", 5L);
    }

    @Test
    public void shouldMapDtoListToEntityList() {

        List<StudentDto> dtoList = new ArrayList<StudentDto>();
        for (long i = 0; i < 10; i++) {
            StudentDto dto = new StudentDto();
            dto.setId(i);

            dtoList.add(dto);
        }

        List<Student> entities = sut.dtoListToEntityList(dtoList);

        assertThat(entities)
                .isNotNull();

        Student entitiesZero = entities.get(0);

        assertThat(entitiesZero)
                .hasFieldOrPropertyWithValue("id", 0L);

        StudentDto entitiesFive = dtoList.get(5);

        assertThat(entitiesFive)
                .hasFieldOrPropertyWithValue("id", 5L);
    }

}
