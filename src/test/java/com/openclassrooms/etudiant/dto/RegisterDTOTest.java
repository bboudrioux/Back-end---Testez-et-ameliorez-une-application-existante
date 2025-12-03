package com.openclassrooms.etudiant.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterDTOTest {

    @Test
    @DisplayName("RegisterDTO - Setters and Getters")
    void testGettersAndSetters() {
        RegisterDTO dto = new RegisterDTO();

        dto.setFirstName("John");
        dto.setLastName("Doe");
        dto.setLogin("john");
        dto.setPassword("pass");

        assertThat(dto.getFirstName()).isEqualTo("John");
        assertThat(dto.getLastName()).isEqualTo("Doe");
        assertThat(dto.getLogin()).isEqualTo("john");
        assertThat(dto.getPassword()).isEqualTo("pass");
    }

    @Test
    @DisplayName("RegisterDTO - ToString contains fields")
    void testToString() {
        RegisterDTO dto = new RegisterDTO();
        dto.setFirstName("Anna");
        dto.setLastName("Smith");
        dto.setLogin("anna");
        dto.setPassword("123");

        String result = dto.toString();

        assertThat(result).contains("Anna");
        assertThat(result).contains("Smith");
        assertThat(result).contains("anna");
    }
}
