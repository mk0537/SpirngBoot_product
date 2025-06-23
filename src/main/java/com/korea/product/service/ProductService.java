package com.korea.product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.korea.product.dto.ProductDTO;
import com.korea.product.model.ProductEntity;
import com.korea.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository repository;
	
	//추가
	public List<ProductDTO> addProduct(ProductDTO dto){
		ProductEntity entity = ProductDTO.toEntity(dto);
		//jpa로 데이터베이스에 CRUD할 때 반드시 entity로만 해야한다.
		repository.save(entity);
		return repository.findAll()
				.stream()
				.map(ProductDTO::new)
				.collect(Collectors.toList());
	}
	
	//조회(최소금액이 있다면 최소금액 이상의 제품만 조회)
	public List<ProductDTO> getFilteredProducts(Double minPrice){
		//일단 전체 조회를 한다.
		List<ProductEntity> products = repository.findAll();
		
		//가격 필터링(minPrice가 있을 경우)
		if(minPrice != null) {
			products = products
						.stream()
						.filter(product -> product.getPrice() >= minPrice)
						.collect(Collectors.toList());
		}
		
		return products.stream().map(ProductDTO::new).collect(Collectors.toList());
	}
	
	
	
	//수정 (ID를 통한 수정)
	public List<ProductDTO> updateProduct(int id, ProductDTO dto) {
		// id를 통해서 데이터베이스에 저장되어 있는 데이터를 꺼내온다.
		Optional<ProductEntity> optionalEntity = repository.findById(id);
		
		// 데이터가 존재한다면
		if(optionalEntity.isPresent()) {
			// Optional 에 들어 있는 데이터를 꺼낸다.
			ProductEntity entity = optionalEntity.get();
			// 수정하려고 하는 데이터로 다시 세팅한다.
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setPrice(dto.getPrice());
			//다시 세팅했으면 데이터베이스에 저장한다.
			repository.save(entity);
		}
		
		return repository.findAll().stream().map(ProductDTO::new).collect(Collectors.toList());
	}
	
	
	//삭제
	public boolean deleteProduct(int id) {
		// id를 통해서 데이터베이스에 데이터가 실제로 존재하는지
		Optional<ProductEntity> optionalEntity = repository.findById(id);
		// 존재한다면 
		if(optionalEntity.isPresent()) {
			// 데이터베이스에서 삭제
			repository.deleteById(id);
			return true; // 삭제가 잘 됨
		}
		
		return false; // 삭제가 잘 안 됨
	}
	
	
	
	
	
	
	
	
}
