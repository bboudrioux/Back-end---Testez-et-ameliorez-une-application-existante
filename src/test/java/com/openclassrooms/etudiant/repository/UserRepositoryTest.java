package com.openclassrooms.etudiant.repository;

import com.openclassrooms.etudiant.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Container
    private static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0");

    @DynamicPropertySource
    static void setDatasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @BeforeEach
    void reset() {
        userRepository.deleteAll();
    }

    @Test
    void testSaveAndFindByLogin() {
        User user = new User();
        user.setFirstName("Brian");
        user.setLastName("Boudrioux");
        user.setLogin("brian.login");
        user.setPassword("secret");

        userRepository.save(user);

        Optional<User> found = userRepository.findByLogin("brian.login");

        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Brian");
    }

    @Test
    void testFindByLoginNotFound() {
        Optional<User> user = userRepository.findByLogin("unknown");
        assertThat(user).isEmpty();
    }
}
