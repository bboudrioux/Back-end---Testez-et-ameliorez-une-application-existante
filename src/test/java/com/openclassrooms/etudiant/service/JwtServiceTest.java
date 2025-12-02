package com.openclassrooms.etudiant.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", "abcdefghijklmnopqrstuvwxyz123456");
    }

    @Test
    void testGenerateToken_returnsNonNullToken() {
        UserDetails user = new User("testuser", "password", new ArrayList<>());
        String token = jwtService.generateToken(user);
        assertNotNull(token);
    }

    @Test
    void testExtractUsername_returnsCorrectUsername() {
        UserDetails user = new User("testuser", "password", new ArrayList<>());
        String token = jwtService.generateToken(user);
        assertEquals("testuser", jwtService.extractUsername(token));
    }

    @Test
    void testValidateToken_returnsTrueForCorrectUser() {
        UserDetails user = new User("testuser", "password", new ArrayList<>());
        String token = jwtService.generateToken(user);
        assertTrue(jwtService.validateToken(token, user));
    }

    @Test
    void testValidateToken_returnsFalseForWrongUser() {
        UserDetails user = new User("testuser", "password", new ArrayList<>());
        UserDetails other = new User("otheruser", "password", new ArrayList<>());
        String token = jwtService.generateToken(user);
        assertFalse(jwtService.validateToken(token, other));
    }
}
