package com.putusaputra.bazzar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putusaputra.bazzar.model.Product;
import com.putusaputra.bazzar.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    
    public List<Product> findAll() {
        return (List<Product>) this.repository.findAll();
    }
    
    public Product findById(String productId) {
        return this.repository.findById(productId).orElse(null);
    }
    
    public Product save(Product product) {
        return this.repository.save(product);
    }
    
    public Product update(Product product) {
        return this.repository.save(product);
    }
    
    public void deleteById(String productId) {
        this.repository.deleteById(productId);
    }
}
