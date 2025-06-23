package com.korea.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.korea.product.dto.ProductDTO;
import com.korea.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products") // 공통 URL
@RequiredArgsConstructor
public class ProductController {

	//서비스 주입
	private final ProductService productService;
	
	//추가
	@PostMapping
	public ResponseEntity<?> addProduct(@RequestBody ProductDTO dto){
		List<ProductDTO> response = productService.addProduct(dto);
		return ResponseEntity.ok(response);
	}
	
	//모든 상품의 조회
	//클라이언트가 최소금액을 전달할 수도 있다.
	@GetMapping
	public ResponseEntity<?> getAllProducts(
			@RequestParam(required=false) Double minPrice){
		List<ProductDTO> products = productService.getFilteredProducts(minPrice);
		return ResponseEntity.ok(products);
	}
	
	//수정(ID를 통한 수정) 
	//ProductService 클래스에 같은 메서드가 있어야 함!
	@PutMapping("/{id}") // @PathVariable을 통해 URL에서 id를 추출
	public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProductDTO dto) {
		List<ProductDTO> products = productService.updateProduct(id, dto);
		return ResponseEntity.ok(products);
		
	}
	
	
	//삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable int id) {
		boolean isDeleted = productService.deleteProduct(id);
		
		// 삭제가 정상 처리 되면 본문 없이 204 No Content 응답
		if(isDeleted) {
			return ResponseEntity.noContent().build(); // 삭제는 됐는데 보여줄 건 없다
		}
		// 삭제 실패 시 404 Not Found 응답
		return ResponseEntity.notFound().build();
	}
	
	
	
	
	
	
	
	
}





