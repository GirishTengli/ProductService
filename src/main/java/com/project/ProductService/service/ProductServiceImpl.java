package com.project.ProductService.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.ProductService.entity.Product;
import com.project.ProductService.exception.ProductServiceCustomException;
import com.project.ProductService.model.ProductRequest;
import com.project.ProductService.model.ProductResponse;
import com.project.ProductService.repository.ProductRepository;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repo;

	@Override
	public long addProduct(ProductRequest productRequest) {
		log.info("Adding Product");
		Product product = Product.builder().productName(productRequest.getName()).quantity(productRequest.getQuantity())
				.price(productRequest.getPrice()).build();

		repo.save(product);
		log.info("Product Created");
		return product.getProductId();
	}

	@Override
	public ProductResponse getProductById(long productId) {
		log.info("get the product for productId: " + productId);
		Product product = repo.findById(productId).orElseThrow(
				() -> new ProductServiceCustomException("product with given ID doesn't exist", "PRODUCT_NOT_FOUND"));

		ProductResponse productResponse = new ProductResponse();
		BeanUtils.copyProperties(product, productResponse);
		return productResponse;
	}

	@Override
	public void reduceQuantity(long productId, long quantity) {
		log.info("Reduct Quantity {} for Id: {}", quantity, productId);
		Product product = repo.findById(productId).orElseThrow(
				() -> new ProductServiceCustomException("Product with given Id not found", "PRODUCT-NOT-FOUND"));
		if (product.getQuantity() < quantity) {
			throw new ProductServiceCustomException("Product doesn't have enough Quantity", "INSUFFICIENT_QUANTITY");
		}

		product.setQuantity(product.getQuantity() - quantity);
		repo.save(product);

		log.info("Product Quantity updated Successfully");

	}

}
