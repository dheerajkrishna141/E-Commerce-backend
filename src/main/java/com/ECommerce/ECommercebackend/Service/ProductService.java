package com.ECommerce.ECommercebackend.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ECommerce.ECommercebackend.Entity.Product;
import com.ECommerce.ECommercebackend.Payload.ProductDTO;

public interface ProductService {

	public List<Product> getProducts();
	
	public String addProduct(ProductDTO product);
	
	public String updateProduct(ProductDTO productDto, long id) throws Exception;

	public Page<Product> getProducts(Integer pageNo, Integer pageSize, String sort);
}
