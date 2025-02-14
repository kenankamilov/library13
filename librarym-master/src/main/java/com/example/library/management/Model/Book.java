package com.example.library.management.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String isbn;

    // Category ilə əlaqə
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer stock; // Use Integer to allow null values

    // Author ilə ManyToMany əlaqəsi
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    public void decreaseStock() {
        if (this.stock == null || this.stock <= 0) {
            throw new RuntimeException("Kitabın stoku bitib.");
        }
        this.stock--;
    }

    public void increaseStock() {
        if (this.stock == null) {
            this.stock = 0;
        }
        this.stock++;
    }
}
