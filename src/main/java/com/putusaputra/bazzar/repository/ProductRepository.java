package com.putusaputra.bazzar.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.putusaputra.bazzar.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {

}
