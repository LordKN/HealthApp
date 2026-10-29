package com.HealthApp.service;

import com.HealthApp.model.Category;
import com.HealthApp.repo.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;

    @Test
    @DisplayName("Test find category by Id")
    void testFindCategoryById_whenCategoryIsProvided_returnCategoryObject() {
        // Arrange
        Category category = new Category();
        category.setId(1L);
        when(repo.findById(1L))
                .thenReturn(Optional.of(category));

        // Act
        Category c = service.findCategoryById(1L);

        // Assert
        assertEquals(1L, c.getId());

        verify(repo).findById(1L);
    }

    @Test
    @DisplayName("Test find category by Wger id")
    void testFindByWgerId_whenWgerIdIsProvided_returnCategoryObject() {
        // Arrange
        Category category = new Category();
        category.setWgerId(1);
        when(repo.findByWgerId(1))
                .thenReturn(Optional.of(category));

        // Act
        Category c = service.findByWgerId(1);

        // Assert
        assertEquals(1, c.getWgerId());

        verify(repo).findByWgerId(1);
    }

    @Test
    @DisplayName("Test find category by name")
    void testFindCategoryByName_whenNameIsProvided_returnListOfCategoryObjects() {
        // Arrange
        Category category1 = new Category();
        category1.setName("Upper Back");

        Category category2 = new Category();
        category2.setName("Lower Back");

        String name = "back";

        when(repo.findByNameContainingIgnoreCase(name))
                .thenReturn(List.of(category1, category2));

        // Act
        List<Category> categoryList = service.findCategoryByName(name);

        // Assert
        assertEquals(2, categoryList.size());
        assertEquals("Upper Back", category1.getName());
        assertEquals("Lower Back", category2.getName());
    }
}
