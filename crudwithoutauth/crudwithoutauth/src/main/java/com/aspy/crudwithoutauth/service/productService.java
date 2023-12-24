package com.aspy.crudwithoutauth.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.aspy.crudwithoutauth.entity.product;
import com.aspy.crudwithoutauth.repository.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service

public class productService {
	
	 private final ProductRepository productRepository;
	 
	    @Autowired
	    public productService( 
	        ProductRepository productRepository) 
	    { 
	        this.productRepository = productRepository; 
	    } 

	    public ResponseEntity<product> saveProduct(@RequestBody product product) 
	    { 
	        product newProduct = productRepository.save(product); 
	        return ResponseEntity.ok(newProduct); 
	    }

	    public ResponseEntity<List<product> > fetchAllProducts() 
	    { 
	        return ResponseEntity.ok(productRepository.findAll()); 
	    }
	
	    public ResponseEntity<Optional<product> > 
	    getProductById(int product_id) 
	    { 
	        Optional<product> product 
	            = productRepository.findById(product_id); 
	        if (product.isPresent()) { 
	            return ResponseEntity.ok(product); 
	        } 
	        else { 
	            return ResponseEntity.notFound().build(); 
	        } 
	    }

	    public ResponseEntity<product> updateProduct(int product_id , product updatedProduct) 
	    { 
	    	
	    		product Existingproduct 
	                    = productRepository.findById(product_id).orElseThrow( 
	                  () 
	                    -> new EntityNotFoundException( 
	                    String.valueOf(product_id))); 
	               
	        Existingproduct.setName(updatedProduct.getName()); 
	        Existingproduct.setDescription(updatedProduct.getDescription()); 
	        Existingproduct.setPrice( 
	            updatedProduct.getPrice()); 
	        Existingproduct.setProduct_type(updatedProduct.getProduct_type());
	         
	        product SavedEntity = productRepository.save(Existingproduct);
	        
	        return ResponseEntity.ok(SavedEntity);
	    	}
	    	
	    
	        
	    public ResponseEntity<String> deleteProduct(int id) 
	    { 
	        productRepository.deleteById(id); 
	        return ResponseEntity.ok( 
	            "Product Deleted Successfully"); 
	    }

}