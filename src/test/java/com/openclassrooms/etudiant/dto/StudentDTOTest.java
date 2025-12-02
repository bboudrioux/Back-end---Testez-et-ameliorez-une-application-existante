package com.openclassrooms.etudiant.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.openclassrooms.etudiant.dto.StudentDTO;

import static org.assertj.core.api.Assertions.assertThat;

class StudentDTOTest {

    @Test
    @DisplayName("StudentDTO - Constructor and Getters")
    void testConstructorAndGetters() {
        StudentDTO dto = new StudentDTO(1L, "John", "Doe");

        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("StudentDTO - Setters")
    void testSetters() {
        StudentDTO dto = new StudentDTO();

        dto.setId(2L);
        dto.setFirstName("Anna");
        dto.setLastName("Smith");

        assertThat(dto.getId()).isEqualTo(2L);
        assertThat(dto.getFirstName()).isEqualTo("Anna");
        assertThat(dto.getLastName()).isEqualTo("Smith");
    }

    @Test
    @DisplayName("StudentDTO - Equals and HashCode")
    void testEqualsAndHashCode() {
        StudentDTO d1 = new StudentDTO(1L, "John", "Doe");
        StudentDTO d2 = new StudentDTO(1L, "John", "Doe");

        assertThat(d1).isEqualTo(d2);
        assertThat(d1.hashCode()).isEqualTo(d2.hashCode());
    }

    @Test
    @DisplayName("StudentDTO - ToString should contain field values")
    void testToString() {
        StudentDTO dto = new StudentDTO(1L, "John", "Doe");

        String result = dto.toString();
        assertThat(result).contains("John");
        assertThat(result).contains("Doe");
        assertThat(result).contains("1");
    }
}
