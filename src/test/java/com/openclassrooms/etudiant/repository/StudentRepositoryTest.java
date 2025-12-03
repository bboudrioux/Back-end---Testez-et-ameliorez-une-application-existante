package com.openclassrooms.etudiant.repository;

import com.openclassrooms.etudiant.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @BeforeEach
    void setup() {
        studentRepository.deleteAll();
    }

    @Test
    void testSaveAndFindAll() {
        Student s = new Student();
        s.setFirstName("John");
        s.setLastName("Doe");

        studentRepository.save(s);

        List<Student> students = studentRepository.findAll();

        assertThat(students).isNotEmpty();
        assertThat(students.size()).isEqualTo(1);
        assertThat(students.get(0).getFirstName()).isEqualTo("John");
    }

    @Test
    void testFindById() {
        Student s = new Student();
        s.setFirstName("Anna");
        s.setLastName("Smith");

        Student saved = studentRepository.save(s);
        Student found = studentRepository.findById(Objects.requireNonNull(saved.getId())).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getLastName()).isEqualTo("Smith");
    }
}
