package com.example.library.management;

import com.example.library.management.Model.Category;
import com.example.library.management.Repository.CategoryRepository;
import com.example.library.management.Service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId(1L);
        category.setName("Fiction");
        category.setDescription("Books that tell fictional stories");
    }

    @Test
    public void testCreateCategory() {
        categoryService.createCategory(category);

        // Verify that save method was called on repository
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    public void testGetCategoryById() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.getCategoryById(1L).orElse(null);
        assertNotNull(foundCategory);
        assertEquals(category.getId(), foundCategory.getId());
        assertEquals(category.getName(), foundCategory.getName());
    }

    @Test
    public void testUpdateCategory() {
        Category updatedCategory = new Category();
        updatedCategory.setName("Non-fiction");
        updatedCategory.setDescription("Books based on real facts");

        // Mocking existsById so that update proceeds
        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.updateCategory(1L, updatedCategory);

        // Capture the object passed to save()
        ArgumentCaptor<Category> categoryCaptor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(categoryCaptor.capture());

        Category savedCategory = categoryCaptor.getValue();

        assertEquals(1L, savedCategory.getId());
        assertEquals("Non-fiction", savedCategory.getName());
        assertEquals("Books based on real facts", savedCategory.getDescription());
    }

    @Test
    public void testDeleteCategory() {
        categoryService.deleteCategory(1L);

        // verify that deleteById was called with correct ID
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetCategoryByIdCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> categoryService.getCategoryById(1L).orElseThrow(() -> new RuntimeException("Category not found")));
        assertEquals("Category not found", exception.getMessage());
    }
}
