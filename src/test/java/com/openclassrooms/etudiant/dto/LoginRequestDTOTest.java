package com.openclassrooms.etudiant.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestDTOTest {

    @Test
    @DisplayName("LoginRequestDTO - Setters and Getters")
    void testGettersAndSetters() {
        LoginRequestDTO dto = new LoginRequestDTO();

        dto.setLogin("john");
        dto.setPassword("pass");

        assertThat(dto.getLogin()).isEqualTo("john");
        assertThat(dto.getPassword()).isEqualTo("pass");
    }

    @Test
    @DisplayName("LoginRequestDTO - ToString")
    void testToString() {
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setLogin("user");
        dto.setPassword("pwd");

        assertThat(dto.toString()).contains("user");
    }
}
