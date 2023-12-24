package com.aspy.crudwithoutauth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aspy.crudwithoutauth.service.productService;
import com.aspy.crudwithoutauth.entity.product;
import com.aspy.crudwithoutauth.repository.ProductRepository;

@RestController
@RequestMapping("/crudoperation")
public class ProductController {
	
	private final productService productService;
	private final ProductRepository ProductRepository;
	
	public ProductController(productService productService, ProductRepository ProductRepository) {
		this.productService = productService;
		this.ProductRepository = ProductRepository;
	}
	@PostMapping("/product") 
	public ResponseEntity<product> saveProduct(@RequestBody product product) 
	{ 
	    product newProduct = ProductRepository.save(product); 
	    return ResponseEntity.ok(newProduct); 
	}
	
	@GetMapping("/products")
	public ResponseEntity<List<product>> getAllProducts(){
		return productService.fetchAllProducts();
	}
	@GetMapping("/products/{product_id}") 
	public ResponseEntity<ResponseEntity<Optional<product>>> getProductById(@PathVariable int product_id) 
	{ 
	    ResponseEntity<Optional<product>> product = productService.getProductById(product_id);
	    if (product != null) { 
	        return ResponseEntity.ok(product); 
	    } 
	    else { 
	        return ResponseEntity.notFound().build(); 
	    } 
	}
	
	 
	@DeleteMapping(value = "/products/{productId}") 
	public String deleteProduct(@PathVariable int productId) 
	{ 
	    productService.deleteProduct(productId); 
	    return "Product Deleted Successfully against id "
	        + productId + " "; 
	}
}
