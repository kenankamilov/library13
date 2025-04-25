package com.example.library.management.Service;

import com.example.library.management.Model.Student;
import com.example.library.management.Repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public void createStudent(Student student) {
        studentRepository.save(student);
    }

    public void updateStudent(Long id, Student student) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Sıfır tapılmadı ID ilə: " + id);
        }

        student.setId(id);
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Tələbə tapılmadı: " + id);
        }
        studentRepository.deleteById(id);
    }
}
