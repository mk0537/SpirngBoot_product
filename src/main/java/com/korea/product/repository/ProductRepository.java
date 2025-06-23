package com.korea.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korea.product.model.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

	//추가적으로 사용자 검색 기능이 필요하다면 메서드를 정의할 수 있다.
	ProductEntity findByEmail(String email);
}







