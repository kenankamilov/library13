package com.example.library.management.Repository;

import com.example.library.management.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByIdentifier(String identifier); // düz ad
}
