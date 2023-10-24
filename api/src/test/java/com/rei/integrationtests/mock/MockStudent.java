package com.rei.integrationtests.mock;

import com.rei.models.dto.StudentDto;

public class MockStudent {

    public StudentDto mockStudentDto() { return mockStudentDto(1L); }

    public StudentDto mockStudentDto(Long i) {
        StudentDto dto = new StudentDto();

        dto.setId(i);
        dto.setFirstName("Vento" + i);
        dto.setLastName("Áureo" + i);
        dto.setEmail("vento@aureo.com" + i);
        dto.setCourse("Ciência da Computação" + i);

        return dto;
    }
}
