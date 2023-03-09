package com.example.demo.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.projections.ProductProjection;
import com.example.demo.repositories.ProductRepository;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@GetMapping
	public Page<ProductProjection> getProducts(
			@RequestParam(value = "categoryId", defaultValue = "0") String categoryId, Pageable pageable) {
		
		Stream<Long> stream = Stream.of(categoryId.split(",")).map(x -> Long.parseLong(x));
		
		List<Long> categoryIds = "0".equals(categoryId) ? Arrays.asList() : stream.toList();

		return productRepository.searchProducts(categoryIds, pageable);
	}
}
