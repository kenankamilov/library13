package com.example.library.management.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    // Author və Book arasında ManyToMany əlaqəsi qurulur
    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;
}
