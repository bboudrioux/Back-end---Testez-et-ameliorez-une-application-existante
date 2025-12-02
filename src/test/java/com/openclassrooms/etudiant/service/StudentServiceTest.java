package com.openclassrooms.etudiant.service;

import com.openclassrooms.etudiant.dto.StudentDTO;
import com.openclassrooms.etudiant.entities.Student;
import com.openclassrooms.etudiant.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private StudentDTO studentDTO;
    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentDTO = new StudentDTO(null, "John", "Doe");
        student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
    }

    @Test
    void testListStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));
        List<StudentDTO> result = studentService.listStudents();
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getFirstName());
    }

    @Test
    void testGetStudentById_found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Optional<StudentDTO> result = studentService.getStudentById(1L);
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        StudentDTO result = studentService.createStudent(studentDTO);
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testUpdateStudent_found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO updateDTO = new StudentDTO(null, "Jane", "Doe");
        Optional<StudentDTO> result = studentService.updateStudent(1L, updateDTO);

        assertTrue(result.isPresent());
        assertEquals("Jane", result.get().getFirstName());
    }

    @Test
    void testDeleteStudent_found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        boolean result = studentService.deleteStudent(1L);
        assertTrue(result);
        verify(studentRepository, times(1)).delete(student);
    }

    @Test
    void testDeleteStudent_notFound() {
        when(studentRepository.findById(2L)).thenReturn(Optional.empty());
        boolean result = studentService.deleteStudent(2L);
        assertFalse(result);
    }
}
