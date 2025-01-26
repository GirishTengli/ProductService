package com.project.ProductService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.ProductService.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
