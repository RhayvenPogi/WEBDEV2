package com.rhayven.midterm.controller;

import com.rhayven.midterm.dto.ProductDTO;
import com.rhayven.midterm.entity.Product;
import com.rhayven.midterm.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Product addProduct(@Valid @RequestBody ProductDTO dto) {
        return service.save(dto);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable int id, @Valid @RequestBody ProductDTO dto) {
        Product existing = service.findById(id);
        if (existing == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        return service.updateProduct(existing, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        if (service.findById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        service.deleteProduct(id);
    }
}
