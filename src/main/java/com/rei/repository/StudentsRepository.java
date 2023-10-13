package com.rei.repository;

import com.rei.models.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentsRepository extends JpaRepository<Student, Long> {
    @Modifying
    @Query("UPDATE Student s SET s.enabled = false WHERE s.id =:id")
    void disablePerson(@Param("id") Long id);

    @Query("SELECT s FROM Student s WHERE s.firstName LIKE LOWER(CONCAT('%', :firstName, '%'))")
    Page<Student> findStudentByName(@Param("firstName") String firstName, Pageable pageable);

}
