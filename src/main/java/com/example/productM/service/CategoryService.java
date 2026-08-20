package com.example.productM.service;

import com.example.productM.dto.CategoryRequest;
import com.example.productM.entity.Category;
import com.example.productM.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest categoryRequest) {

        Category category = new Category();

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Long id, CategoryRequest categoryRequest) {

        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.delete(category);
    }
}