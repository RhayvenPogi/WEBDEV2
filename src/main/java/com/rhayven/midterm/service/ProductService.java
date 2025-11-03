package com.rhayven.midterm.service;

import com.rhayven.midterm.dto.ProductDTO;
import com.rhayven.midterm.entity.Product;
import com.rhayven.midterm.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Product save(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setStock(dto.stock());
        product.setUnit(dto.unit());
        product.setPrice(dto.price());
        return repository.save(product);
    }

    public Product updateProduct(Product product, ProductDTO dto) {
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setStock(dto.stock());
        product.setUnit(dto.unit());
        product.setPrice(dto.price());
        return repository.save(product);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }
}