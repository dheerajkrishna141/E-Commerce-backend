package com.ECommerce.ECommercebackend.Service;

import java.util.List;

import com.ECommerce.ECommercebackend.Entity.Product;
import com.ECommerce.ECommercebackend.Payload.ProductDTO;

public interface ProductService {

	public List<Product> getProducts();
	
	public String addProduct(ProductDTO product);
	
	public String updateProduct(ProductDTO productDto, long id) throws Exception;
}
