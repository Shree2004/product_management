package com.example.productM.service;

import com.example.productM.dto.ProductRequest;
import com.example.productM.entity.Category;
import com.example.productM.entity.Product;
import com.example.productM.repository.CategoryRepository;
import com.example.productM.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    public Product createProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElse(null);

        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        Product product = new Product();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);

        return productRepository.save(product);
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    public Product updateProduct(Long id, ProductRequest productRequest) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElse(null);

        if (category == null) {
            throw new RuntimeException("Category not found");
        }

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);

        return productRepository.save(product);
    }


    public Product changeProductStatus(Long id, boolean enabled) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        product.setEnabled(enabled);

        return productRepository.save(product);
    }


    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        productRepository.delete(product);
    }
}