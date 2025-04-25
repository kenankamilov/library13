package com.example.library.management;

import com.example.library.management.Model.Student;
import com.example.library.management.Repository.StudentRepository;
import com.example.library.management.Service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creating a mock student object
        student = new Student();
        student.setId(1L);
        student.setIdentifier("S123");
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");
    }

    @Test
    public void testCreateStudent() {
        // Create a student
        studentService.createStudent(student);

        // Verify that the save method was called on the repository
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testGetAllStudents() {
        // Simulate retrieval of all students
        when(studentRepository.findAll()).thenReturn(List.of(student));

        // Retrieve all students
        assertEquals(1, studentService.getAllStudents().size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void testGetStudentById() {
        // When finding a student by ID, return the mock student
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        // Retrieve the student by ID
        Optional<Student> foundStudent = studentService.getStudentById(1L);
        assertTrue(foundStudent.isPresent());
        assertEquals(student.getId(), foundStudent.get().getId());
    }

    @Test
    public void testGetStudentByIdNotFound() {
        // Simulate that the student with ID doesn't exist
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Try to retrieve the student and check that it's empty
        Optional<Student> foundStudent = studentService.getStudentById(1L);
        assertFalse(foundStudent.isPresent());
    }

    @Test
    public void testUpdateStudent() {
        // When the student exists, return the mock student
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(studentRepository.save(student)).thenReturn(student);

        // Update the student's details
        student.setName("John Smith");
        studentService.updateStudent(1L, student);

        // Verify that save was called after updating
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    public void testUpdateStudentNotFound() {
        // Simulate that the student doesn't exist
        when(studentRepository.existsById(1L)).thenReturn(false);

        // Attempt to update the student, and it should throw a RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(1L, student);
        });
        assertEquals("Student not found with id: 1", exception.getMessage());
    }

    @Test
    public void testDeleteStudent() {
        // Simulate that the student exists and can be deleted
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        // Perform the deletion
        studentService.deleteStudent(1L);

        // Verify that deleteById was called
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteStudentNotFound() {
        // Simulate that the student doesn't exist
        when(studentRepository.existsById(1L)).thenReturn(false);

        // Attempt to delete the student, and it should throw a RuntimeException
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.deleteStudent(1L);
        });
        assertEquals("Sagird tapilmadi: 1", exception.getMessage());
    }
}
