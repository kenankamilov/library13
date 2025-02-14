package com.example.library.management.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Category və Book arasında OneToMany əlaqəsi
    @OneToMany(mappedBy = "category")
    private Set<Book> books;
}
