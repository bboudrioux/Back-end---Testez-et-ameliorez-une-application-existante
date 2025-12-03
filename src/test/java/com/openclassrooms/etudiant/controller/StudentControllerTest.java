package com.openclassrooms.etudiant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.etudiant.configuration.security.CustomUserDetailService;
import com.openclassrooms.etudiant.dto.StudentDTO;
import com.openclassrooms.etudiant.entities.Student;
import com.openclassrooms.etudiant.entities.User;
import com.openclassrooms.etudiant.repository.StudentRepository;
import com.openclassrooms.etudiant.service.JwtService;
import com.openclassrooms.etudiant.service.StudentService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = true)
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

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailService customUserDetailService;


    @DynamicPropertySource
    static void configureTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    private String mockJwtFor(String login) {
            String token = "Bearer faketoken123";

            User user = new User(1L, "John", "Doe", login, "encoded-password", null, null);

            Mockito.when(jwtService.extractUsername("faketoken123")).thenReturn(login);
            Mockito.when(jwtService.validateToken("faketoken123", user)).thenReturn(true);
            Mockito.when(customUserDetailService.loadUserByUsername(login)).thenReturn(user);

            return token;
    }

    private String authToken;

    @BeforeEach
    void setupToken() {
            authToken = mockJwtFor("testuser");
    }

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
    }


    @Test
    @DisplayName("GET /students - List all students")
    void testListStudents() throws Exception {
        Student s1 = new Student(1L, "Alice", "Smith", null, null);
        Student s2 = new Student(2L, "Bob", "Johnson", null, null);

        Mockito.when(studentService.listStudents())
                .thenReturn(Arrays.asList(
                        new StudentDTO(1L, "Alice", "Smith"),
                        new StudentDTO(2L, "Bob", "Johnson")));

        mockMvc.perform(get("/api/students")
                .header("Authorization", authToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /students/{id} - Found")
    void testGetStudentById_found() throws Exception {
        Mockito.when(studentService.getStudentById(1L))
                .thenReturn(Optional.of(new StudentDTO(1L, "Alice", "Smith")));

        mockMvc.perform(get("/api/students/1")
                .header("Authorization",authToken))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /students/{id} - Not Found")
    void testGetStudentById_notFound() throws Exception {
        Mockito.when(studentService.getStudentById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/students/99")
                .header("Authorization", authToken))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /students - Create student")
    void testCreateStudent() throws Exception {
        StudentDTO dto = new StudentDTO(null, "Charlie",
                "Brown");
        StudentDTO saved = new StudentDTO(3L, "Charlie",
                "Brown");

        Mockito.when(studentService.createStudent(any(StudentDTO.class)))
                .thenReturn(saved);

        mockMvc.perform(post("/api/students")
                .header("Authorization",authToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT /students/{id} - Not Found")
    void testUpdateStudent_notFound() throws Exception {
        StudentDTO updated = new StudentDTO(1L, "James", "Brown");

        Mockito.when(studentService.updateStudent(eq(1L), any(StudentDTO.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/students/1")
                .header("Authorization",authToken)
                .content(objectMapper.writeValueAsString(updated))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /students - Update student")
    void testUpdateStudent() throws Exception {
        StudentDTO updated = new StudentDTO(1L, "James", "Brown");

        Mockito.when(studentService.updateStudent(eq(1L), any(StudentDTO.class)))
                .thenReturn(Optional.of(updated));

        mockMvc.perform(put("/api/students/1")
                .header("Authorization",authToken)
                .content(objectMapper.writeValueAsString(updated))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /students/{id} - Deleted")
    void testDeleteStudent() throws Exception {

        Mockito.when(studentService.deleteStudent(1L))
                .thenReturn(true);

        mockMvc.perform(delete("/api/students/1")
                .header("Authorization", authToken))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
