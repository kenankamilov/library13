package com.example.library.management.Service;

import com.example.library.management.Model.Student;
import com.example.library.management.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Create a new student
    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    // Update an existing student
    public void updateStudent(Long id, Student student) {
        // Check if the student exists first
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with id: " + id);
        }

        student.setId(id);  // Ensure the student ID is retained during update
        studentRepository.save(student);
    }

    // Delete a student by ID
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Sagird tapilmadi: " + id);
        }
        studentRepository.deleteById(id);
    }
}
