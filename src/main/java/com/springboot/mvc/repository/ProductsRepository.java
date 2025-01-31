package com.springboot.mvc.repository;

	import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

	import com.springboot.mvc.entity.ProductsEntity;

	@Repository
	public interface ProductsRepository extends JpaRepository<ProductsEntity, Long> {
		
	}
