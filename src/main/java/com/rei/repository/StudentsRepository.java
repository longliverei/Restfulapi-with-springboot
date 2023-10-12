package com.rei.repository;

import com.rei.models.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentsRepository extends JpaRepository<Student, Long> {

    @Query("UPDATE Student s set s.enabled = false WHERE s.id =:id")
    void disablePerson(@Param("id") Long id);

}
