package com.project.ProductService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ProductService.model.ProductRequest;
import com.project.ProductService.model.ProductResponse;
import com.project.ProductService.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	private ProductService service;

	@PostMapping
	public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {
		long productId = service.addProduct(request);
		return new ResponseEntity<Long>(productId, HttpStatus.CREATED);
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponse> getProductById(@PathVariable long productId) {

		ProductResponse productResponse = service.getProductById(productId);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	@PutMapping("/reducequantity/{id}")
	public ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity) {
		service.reduceQuantity(productId, quantity);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
