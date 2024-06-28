package com.ECommerce.ECommercebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerce.ECommercebackend.Payload.ProductDTO;
import com.ECommerce.ECommercebackend.Service.ProductService;

@RestController
@RequestMapping("/admin/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	@PostMapping("/add")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO product){
		return new ResponseEntity<String>(productService.addProduct(product), HttpStatus.ACCEPTED);
	}
	@GetMapping
	public ResponseEntity<?> getProducts(){
		try {
			return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product,  @PathVariable(name = "id") long id){
		try {
			return new ResponseEntity<>(productService.updateProduct(product, id), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
