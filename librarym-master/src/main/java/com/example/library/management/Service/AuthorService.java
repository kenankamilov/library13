package com.example.library.management.Service;



import com.example.library.management.Model.Author;
import com.example.library.management.Repository.AuthorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public void updateAuthor(Author updatedAuthor) {
        Author existingAuthor = authorRepository.findById(updatedAuthor.getId())
                .orElseThrow(() -> new IllegalArgumentException("Müəllif tapılmadı: " + updatedAuthor.getId()));

        existingAuthor.setName(updatedAuthor.getName());
        existingAuthor.setSurname(updatedAuthor.getSurname());

        authorRepository.save(existingAuthor);
    }

    public void deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        }
    }
}
