package com.ECommerce.ECommercebackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ECommerce.ECommercebackend.Payload.ProductDTO;
import com.ECommerce.ECommercebackend.Service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO product) {
		return new ResponseEntity<String>(productService.addProduct(product), HttpStatus.ACCEPTED);
	}

	@GetMapping
	public ResponseEntity<?> getProducts(@RequestParam(required = false) Integer pageNo,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String sort) {
		try {
			if (pageNo == null) {
				return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK);

			} else {
				return new ResponseEntity<>(productService.getProducts(pageNo, pageSize, sort), HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product, @PathVariable(name = "id") long id) {
		try {
			return new ResponseEntity<>(productService.updateProduct(product, id), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
