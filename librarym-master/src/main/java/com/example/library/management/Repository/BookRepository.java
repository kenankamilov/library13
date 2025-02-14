package com.example.library.management.Repository;

import com.example.library.management.Model.Book;
import com.example.library.management.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    Page<Book> findByCategory(Category category, Pageable pageable);


    List<Book> findByCategory(Category category);
}
