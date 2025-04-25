package com.example.library.management;


import com.example.library.management.Model.Author;
import com.example.library.management.Repository.AuthorRepository;
import com.example.library.management.Service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class) // JUnit5 annotation
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        // Testlərdən əvvəl hər bir test üçün yeni Author obyektini hazırlayırıq.
        author = new Author();
        author.setId(1L);
        author.setName("Orxan Hüseynov");
        author.setBiography("Some biography text.");
        author.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void testGetAllAuthors() {
        // Test data
        Author author2 = new Author();
        author2.setId(2L);
        author2.setName("Rəşad Quliyev");
        author2.setBiography("Another biography.");
        author2.setCreatedAt(LocalDateTime.now());

        // Mocking `findAll` method of the repository to return a list of authors
        Mockito.when(authorRepository.findAll()).thenReturn(Arrays.asList(author, author2));

        // Method call
        List<Author> authors = authorService.getAllAuthors();

        // Assertions
        assertNotNull(authors);
        assertEquals(2, authors.size());
        assertEquals("Orxan Hüseynov", authors.get(0).getName());
        assertEquals("Rəşad Quliyev", authors.get(1).getName());
    }

    @Test
    void testSaveAuthor() {
        // save metodunun qaytardığı nəticəni mock edirik
        Mockito.when(authorRepository.save(any(Author.class))).thenReturn(author);

        // Method call
        authorService.saveAuthor(author);

        // Verifying save was called
        Mockito.verify(authorRepository, Mockito.times(1)).save(author);
    }

    @Test
    void testGetAuthorById() {
        // Mocking findById to return the author object
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // Method call
        Author fetchedAuthor = authorService.getAuthorById(1L);

        // Assertions
        assertNotNull(fetchedAuthor);
        assertEquals("Orxan Hüseynov", fetchedAuthor.getName());
        assertEquals(1L, fetchedAuthor.getId());
    }

    @Test
    void testGetAuthorById_ThrowsException_WhenNotFound() {
        // Mocking findById to return empty (not found)
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        // Method call & Exception assertion
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authorService.getAuthorById(1L);
        });

        // Verifying exception message
        assertEquals("Müəllif tapılmadı: 1", exception.getMessage());
    }

    @Test
    void testUpdateAuthor() {
        // Mocking findById to return the existing author
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // Prepare updated author object
        Author updatedAuthor = new Author();
        updatedAuthor.setName("Orxan Hüseynov Updated");
        updatedAuthor.setBiography("Updated biography");

        // Method call
        authorService.updateAuthor(1L, updatedAuthor);

        // Verifying that repository save method is called with updated author
        Mockito.verify(authorRepository, Mockito.times(1)).save(Mockito.any(Author.class));

        // Fetch updated author and validate changes
        Author fetchedUpdatedAuthor = authorService.getAuthorById(1L);
        assertEquals("Orxan Hüseynov Updated", fetchedUpdatedAuthor.getName());
    }

    @Test
    void testDeleteAuthor() {
        // Mocking findById to return the existing author
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        // Mocking delete method
        Mockito.doNothing().when(authorRepository).delete(author);

        // Method call
        authorService.deleteAuthor(1L);

        // Verifying that delete method was called once
        Mockito.verify(authorRepository, Mockito.times(1)).delete(author);
    }
}
