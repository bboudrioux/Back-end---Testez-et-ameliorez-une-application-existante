package com.openclassrooms.etudiant.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.openclassrooms.etudiant.entities.Student;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class StudentTest {

    @Test
    @DisplayName("Student - Constructor and Getters")
    void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        Student student = new Student(
                1L,
                "John",
                "Doe",
                now,
                now);

        assertThat(student.getId()).isEqualTo(1L);
        assertThat(student.getFirstName()).isEqualTo("John");
        assertThat(student.getLastName()).isEqualTo("Doe");
        assertThat(student.getCreated_at()).isEqualTo(now);
        assertThat(student.getUpdated_at()).isEqualTo(now);
    }

    @Test
    @DisplayName("Student - Setters")
    void testSetters() {
        Student student = new Student();

        student.setId(10L);
        student.setFirstName("Alice");
        student.setLastName("Smith");

        assertThat(student.getId()).isEqualTo(10L);
        assertThat(student.getFirstName()).isEqualTo("Alice");
        assertThat(student.getLastName()).isEqualTo("Smith");
    }

    @Test
    @DisplayName("Student - Equals and HashCode")
    void testEqualsAndHashCode() {
        Student s1 = new Student(1L, "John", "Doe", null, null);
        Student s2 = new Student(1L, "John", "Doe", null, null);

        assertThat(s1).isEqualTo(s2);
        assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
    }

    @Test
    @DisplayName("Student - ToString should contain field values")
    void testToString() {
        Student student = new Student(1L, "John", "Doe", null, null);

        String result = student.toString();

        assertThat(result).contains("John");
        assertThat(result).contains("Doe");
        assertThat(result).contains("1");
    }
}
