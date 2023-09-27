package com.rei.models;

import java.util.Objects;

public class Students {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Students students = (Students) o;
        return Objects.equals(id, students.id) && Objects.equals(firstName, students.firstName) && Objects.equals(lastName, students.lastName) && Objects.equals(email, students.email) && Objects.equals(course, students.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, course);
    }

}
