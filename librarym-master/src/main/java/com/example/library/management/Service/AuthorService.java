package com.example.library.management.Service;

import com.example.library.management.Model.Author;
import com.example.library.management.Repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // Get all authors
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Save (create) an author
    public void saveAuthor(Author author) {
        // Set the createdAt field before saving
        author.setCreatedAt(LocalDateTime.now());  // Set created date
        authorRepository.save(author);
    }

    // Get an author by ID
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Müəllif tapılmadı: " + id));
    }

    // Update an author
    public void updateAuthor(Long id, Author updatedAuthor) {
        // Get the existing author by ID
        Author existingAuthor = getAuthorById(id);

        // Update the fields of the existing author
        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setBiography(updatedAuthor.getBiography());

        // You can also set the updatedAt field if you want, but it's not in the model

        authorRepository.save(existingAuthor);  // Save the updated author
    }

    // Delete an author
    public void deleteAuthor(Long id) {
        Author author = getAuthorById(id);
        authorRepository.delete(author);
    }
}
