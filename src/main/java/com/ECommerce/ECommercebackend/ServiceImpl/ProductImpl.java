package com.ECommerce.ECommercebackend.ServiceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ECommerce.ECommercebackend.Entity.Inventory;
import com.ECommerce.ECommercebackend.Entity.Product;
import com.ECommerce.ECommercebackend.Exceptions.ProductNotFoundException;
import com.ECommerce.ECommercebackend.Exceptions.ProductNotUniqueException;
import com.ECommerce.ECommercebackend.Payload.ProductDTO;
import com.ECommerce.ECommercebackend.Repository.InventoryRepo;
import com.ECommerce.ECommercebackend.Repository.ProductRepo;
import com.ECommerce.ECommercebackend.Service.ProductService;

@Service
public class ProductImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private InventoryRepo inventoryRepo;

	@Autowired
	private ModelMapper modelmapper;

	@Override
	public List<Product> getProducts() {

		return productRepo.findAll();
	}

	public Page<Product> getProducts(Integer pageNo, Integer pageSize, String sort) {
		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		if(sort!=null) {
			if(sort.startsWith("-")) {
				sort = sort.substring(1);
				pageable = PageRequest.of(pageNo, pageSize, Direction.DESC, sort);
			}else {
				pageable = PageRequest.of(pageNo, pageSize, Direction.ASC, sort);
				
			}
		}
		return productRepo.findAll(pageable);
	}

	@Override
	public String addProduct(ProductDTO product) {

		Product prod = productRepo.findByName(product.getName());
		if (prod != null) {
			throw new ProductNotUniqueException("Product is not unique");
		}
		prod = modelmapper.map(product, Product.class);
		prod = productRepo.save(prod);
		Inventory newEntry = new Inventory();
		newEntry.setProduct(prod);
		newEntry.setStockQuantity(product.getQuantity());
		inventoryRepo.save(newEntry);

		return "Added Successfully!";
	}

	@Override
	public String updateProduct(ProductDTO productDto, long id) throws Exception {
		// TODO Auto-generated method stub
		Product product = productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("product not found"));

		if (productDto.getName() != null) {
			product.setName(productDto.getName());
		}
		if (productDto.getPrice() != null) {
			product.setPrice(productDto.getPrice());
		}
		if (productDto.getQuantity() != null) {
			Inventory newInven = product.getInventory();
			newInven.setStockQuantity(productDto.getQuantity());
			product.setInventory(newInven);
		}
		if (productDto.getShortDescription() != null) {
			product.setShortDescription(productDto.getShortDescription());
		}
		if (productDto.getLongDescription() != null) {
			product.setLongDescription(productDto.getLongDescription());
		}

		try {
			productRepo.save(product);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("Could not update the product!");
		}
		return "Updated successfully!";

	}

}
