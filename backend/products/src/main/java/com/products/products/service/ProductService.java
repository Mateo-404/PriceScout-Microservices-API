package com.products.products.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.products.products.model.Product;
import com.products.products.repository.IProductRepository;

@Service
public class ProductService implements IProductService<Long> {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public void deleteById(Long id) {
        java.util.Objects.requireNonNull(id, "id must not be null");
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        java.util.Objects.requireNonNull(id, "id must not be null");
        return productRepository.existsById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAll(int page, int size) {
        return productRepository.findAll(org.springframework.data.domain.PageRequest.of(page, size));
    }

    @Override
    public Optional<Product> findById(Long id) {
        java.util.Objects.requireNonNull(id, "id must not be null");
        return productRepository.findById(id);
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        java.util.Objects.requireNonNull(sku, "sku must not be null");
        return Optional.ofNullable(productRepository.findBySku(sku));
    }

    @Override
    public Product save(Product entity) {
        java.util.Objects.requireNonNull(entity, "entity must not be null");
        productRepository.save(entity);
        return entity;
    }

    @Override
    public List<Product> saveAll(List<Product> entities) {
        java.util.Objects.requireNonNull(entities, "entities must not be null");
        return productRepository.saveAll(entities);
    }

    @Override
    public Optional<Product> update(Long id, Product entity) {
        java.util.Objects.requireNonNull(id, "id must not be null");
        java.util.Objects.requireNonNull(entity, "entity must not be null");

        Optional<Product> existingOpt = productRepository.findById(id);
        if (!existingOpt.isPresent()) {
            return Optional.empty();
        }

        Product existing = existingOpt.get();
        // Copy non-id properties from incoming entity to existing entity
        org.springframework.beans.BeanUtils.copyProperties(entity, existing, "id");

        Product saved = productRepository.save(existing);
        return Optional.of(saved);
    }
    
}
