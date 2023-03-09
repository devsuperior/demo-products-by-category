package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entities.Product;
import com.example.demo.projections.ProductProjection;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query(nativeQuery = true, value = """
		SELECT DISTINCT tb_product.*
		FROM tb_product
		INNER JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
		WHERE :categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds
		""",
		countQuery = """
		SELECT COUNT(*) FROM (
		SELECT DISTINCT tb_product.*
		FROM tb_product
		INNER JOIN tb_product_category ON tb_product_category.product_id = tb_product.id
		WHERE :categoryIds IS NULL OR tb_product_category.category_id IN :categoryIds
		)
		""")
	Page<ProductProjection> searchProducts(List<Long> categoryIds, Pageable pageable);
}
