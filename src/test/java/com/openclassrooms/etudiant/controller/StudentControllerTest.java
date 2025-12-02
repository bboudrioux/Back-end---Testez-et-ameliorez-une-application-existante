package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.etudiant.entities.Student;
import com.openclassrooms.etudiant.repository.StudentRepository;
import com.openclassrooms.etudiant.repository.UserRepository;
import com.openclassrooms.etudiant.service.StudentService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@Testcontainers
class StudentControllerTest {

    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.33")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @MockBean
    private StudentService studentService;

    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
    }

    @Test
    @DisplayName("GET /students - List all students")
    @WithMockUser
    void testListStudents() throws Exception {
        Student s1 = new Student(1L, "Alice", "Smith", null, null);
        Student s2 = new Student(2L, "Bob", "Johnson", null, null);

        Mockito.when(studentService.listStudents())
                .thenReturn(Arrays.asList(
                        new com.openclassrooms.etudiant.dto.StudentDTO(1L, "Alice", "Smith"),
                        new com.openclassrooms.etudiant.dto.StudentDTO(2L, "Bob", "Johnson")));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /students/{id} - Found")
    @WithMockUser
    void testGetStudentById_found() throws Exception {
        Mockito.when(studentService.getStudentById(1L))
                .thenReturn(Optional.of(new com.openclassrooms.etudiant.dto.StudentDTO(1L, "Alice", "Smith")));

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /students/{id} - Not Found")
    @WithMockUser
    void testGetStudentById_notFound() throws Exception {
        Mockito.when(studentService.getStudentById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /students - Create student")
    @WithMockUser
    void testCreateStudent() throws Exception {
        com.openclassrooms.etudiant.dto.StudentDTO dto = new com.openclassrooms.etudiant.dto.StudentDTO(null, "Charlie",
                "Brown");
        com.openclassrooms.etudiant.dto.StudentDTO saved = new com.openclassrooms.etudiant.dto.StudentDTO(3L, "Charlie",
                "Brown");

        Mockito.when(studentService.createStudent(any(com.openclassrooms.etudiant.dto.StudentDTO.class)))
                .thenReturn(saved);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
