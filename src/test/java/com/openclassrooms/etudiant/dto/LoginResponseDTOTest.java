package com.openclassrooms.etudiant.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.openclassrooms.etudiant.dto.LoginResponseDTO;

import static org.assertj.core.api.Assertions.assertThat;

class LoginResponseDTOTest {

    @Test
    @DisplayName("LoginResponseDTO - Constructor")
    void testConstructor() {
        LoginResponseDTO dto = new LoginResponseDTO("token123");

        assertThat(dto.getToken()).isEqualTo("token123");
    }

    @Test
    @DisplayName("LoginResponseDTO - ToString contains token")
    void testToString() {
        LoginResponseDTO dto = new LoginResponseDTO("ABC123");

        assertThat(dto.toString()).contains("ABC123");
    }
}
