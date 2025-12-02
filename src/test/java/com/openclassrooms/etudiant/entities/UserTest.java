package com.openclassrooms.etudiant.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.openclassrooms.etudiant.entities.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    @DisplayName("User - Constructor and Getters")
    void testConstructorAndGetters() {
        LocalDateTime now = LocalDateTime.now();

        User user = new User(
                1L,
                "John",
                "Doe",
                "john.doe",
                "password123",
                now,
                now);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getLogin()).isEqualTo("john.doe");
        assertThat(user.getPassword()).isEqualTo("password123");
        assertThat(user.getCreated_at()).isEqualTo(now);
        assertThat(user.getUpdated_at()).isEqualTo(now);
    }

    @Test
    @DisplayName("User - Setters")
    void testSetters() {
        User user = new User();

        user.setId(2L);
        user.setFirstName("Alice");
        user.setLastName("Smith");
        user.setLogin("alice");
        user.setPassword("testpass");

        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getFirstName()).isEqualTo("Alice");
        assertThat(user.getLastName()).isEqualTo("Smith");
        assertThat(user.getLogin()).isEqualTo("alice");
        assertThat(user.getPassword()).isEqualTo("testpass");
    }

    @Test
    @DisplayName("User - Equals and HashCode")
    void testEqualsAndHashCode() {
        User u1 = new User(1L, "A", "B", "login", "pass", null, null);
        User u2 = new User(1L, "A", "B", "login", "pass", null, null);

        assertThat(u1).isEqualTo(u2);
        assertThat(u1.hashCode()).isEqualTo(u2.hashCode());
    }

    @Test
    @DisplayName("User - ToString contains fields")
    void testToString() {
        User user = new User(1L, "John", "Doe", "john", "pass", null, null);
        String result = user.toString();

        assertThat(result).contains("John");
        assertThat(result).contains("Doe");
        assertThat(result).contains("john");
        assertThat(result).contains("1");
    }

    @Test
    @DisplayName("User - UserDetails methods must return correct values")
    void testUserDetails() {
        User user = new User();
        user.setLogin("username");

        assertThat(user.getUsername()).isEqualTo("username");
        assertThat(user.getAuthorities()).isEmpty();
        assertThat(user.isAccountNonExpired()).isTrue();
        assertThat(user.isAccountNonLocked()).isTrue();
        assertThat(user.isCredentialsNonExpired()).isTrue();
        assertThat(user.isEnabled()).isTrue();
    }
}
