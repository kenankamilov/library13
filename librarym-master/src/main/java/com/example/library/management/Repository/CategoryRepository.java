package com.example.library.management.Repository;


import com.example.library.management.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository


public interface CategoryRepository extends JpaRepository<Category, Long> {
}
