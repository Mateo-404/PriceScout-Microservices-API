package com.products.products.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.products.products.model.Product;

public interface IProductService <ID extends Serializable> {
    // Create
    Product save(Product entity);
    List<Product> saveAll(List<Product> entities);
    // Read
    List<Product> findAll();
    Page<Product> findAll(int page, int size);
    Optional<Product> findById(ID id);
    Optional<Product> findBySku(String sku);
    boolean existsById(ID id);
    // Update
    Optional<Product> update(ID id, Product entity);
    // Delete
    void deleteById(ID id);
}
