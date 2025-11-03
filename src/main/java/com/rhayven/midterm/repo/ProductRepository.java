package com.rhayven.midterm.repo;

import com.rhayven.midterm.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {}