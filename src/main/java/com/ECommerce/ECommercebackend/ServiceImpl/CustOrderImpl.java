package com.ECommerce.ECommercebackend.ServiceImpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ECommerce.ECommercebackend.Annotation.UserAvailabiltyCheck;
import com.ECommerce.ECommercebackend.Entity.Address;
import com.ECommerce.ECommercebackend.Entity.CustOrder;
import com.ECommerce.ECommercebackend.Entity.CustOrderQuantities;
import com.ECommerce.ECommercebackend.Entity.Inventory;
import com.ECommerce.ECommercebackend.Entity.LocalUser;
import com.ECommerce.ECommercebackend.Entity.Product;
import com.ECommerce.ECommercebackend.Exceptions.AddressNotFoundException;
import com.ECommerce.ECommercebackend.Exceptions.OrderCouldNotBePlacedException;
import com.ECommerce.ECommercebackend.Exceptions.OrderNotFoundExcpetion;
import com.ECommerce.ECommercebackend.Exceptions.ProductNotFoundException;
import com.ECommerce.ECommercebackend.Exceptions.UserNotFoundException;
import com.ECommerce.ECommercebackend.Payload.CustOrderDTO;
import com.ECommerce.ECommercebackend.Payload.CustOrderQuantityDTO;
import com.ECommerce.ECommercebackend.Repository.AddressRepo;
import com.ECommerce.ECommercebackend.Repository.CustOrderQuantityRepo;
import com.ECommerce.ECommercebackend.Repository.CustOrderRepo;
import com.ECommerce.ECommercebackend.Repository.ProductRepo;
import com.ECommerce.ECommercebackend.Repository.UserRepo;
import com.ECommerce.ECommercebackend.Service.CustOrderService;

import jakarta.transaction.Transactional;

@Service
@UserAvailabiltyCheck
public class CustOrderImpl implements CustOrderService {

	@Autowired
	private CustOrderRepo orderRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private CustOrderQuantityRepo orderQRepo;

	@Value("${admin.credentials}")
	private String adminCredentials;

	@Autowired
	private ProductRepo productRepo;

	@Value("${Ecommerce.baseUrl}")
	private String BaseUrl;

	@Override
	public Page<CustOrder> getOrders(String username, Integer pageNo, Integer pageSize, String sort) {

		LocalUser Luser = userRepo.findByEmail(username);


		if (pageSize == null) {
			pageSize = 10;
		}
		Pageable pr = PageRequest.of(pageNo, pageSize);

		if (sort == null) {
			pr = PageRequest.of(pageNo, (pageSize != null ? pageSize : 10), Direction.DESC, "id");
		} else {

			if (sort.startsWith("-")) {
				sort = sort.substring(1);
				pr = PageRequest.of(pageNo, pageSize, Direction.DESC, sort);
			} else {
				pr = PageRequest.of(pageNo, pageSize, Direction.ASC, sort);
			}
		}

		return orderRepo.findByUser(pr, Luser);

	}

	@Override
	public List<CustOrder> getOrders(String username) {

		LocalUser Luser = userRepo.findByEmail(username);

		return orderRepo.findByUser(Luser);

	}

	@Override
	@Transactional
	public String createOrder(String username, CustOrderDTO order) throws Exception {
		LocalUser Luser = userRepo.findByEmail(username);
		DecimalFormat df = new DecimalFormat("#.##");
		Double totalPrice = (double) 0;
		CustOrder newOrder = new CustOrder();
		List<CustOrderQuantityDTO> orderqDTO = order.getOrderQuantity();

		List<Address> addressList = addressRepo.findByUser(Luser);
		if (addressList == null) {
			throw new AddressNotFoundException("No addresses are found");
		}
		newOrder.setAddress(addressList.get(0));
		newOrder.setUser(Luser);
		newOrder.setTimeStamp(new Date(System.currentTimeMillis()));
		newOrder = orderRepo.save(newOrder);
		List<Product> productList = new ArrayList<>();
		List<CustOrderQuantities> orderQList = new ArrayList<>();
		List<CustOrder> orderList = new ArrayList<>();

		try {
			for (CustOrderQuantityDTO a : orderqDTO) {
				Double price = (double) 0;

				CustOrderQuantities newQuant = new CustOrderQuantities();
				newQuant.setOrder(newOrder);
				Product fetchedProd = productRepo.findById(a.getProductId())
						.orElseThrow(() -> new ProductNotFoundException("Product not found!"));
				if (fetchedProd.getInventory().getStockQuantity() < a.getQuantity()) {
					throw new OrderCouldNotBePlacedException("Product: " + fetchedProd.getName() + " inventory: "
							+ fetchedProd.getInventory().getStockQuantity());
				}
				newQuant.setProduct(fetchedProd);
				newQuant.setQuantity(a.getQuantity());
				Inventory inventory = fetchedProd.getInventory();
				inventory.setStockQuantity(inventory.getStockQuantity() - a.getQuantity());
				fetchedProd.setInventory(inventory);
				price += fetchedProd.getPrice() * a.getQuantity();
				totalPrice += price;

				productList.add(fetchedProd);
				price = Double.valueOf(df.format(price));
				newQuant.setQprice(price);
				orderQList.add(newQuant);

				totalPrice = Double.valueOf(df.format(totalPrice));
				newOrder.setTotal(totalPrice);
				orderList.add(newOrder);
			}
			productRepo.saveAll(productList);
			orderQRepo.saveAll(orderQList);
			orderRepo.saveAll(orderList);
		} catch (ProductNotFoundException | OrderCouldNotBePlacedException e) {
			// TODO Auto-generated catch block
			throw new BadRequestException(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}

		return "Created Successfully!";
	}

	@Override
	@Transactional
	public String cancelOrder(String username, List<Long> orderIds) throws Exception {

		try {
			LocalUser Luser = userRepo.findByEmail(username);

			for (Long id : orderIds) {

				CustOrder order = orderRepo.findById(id)
						.orElseThrow(() -> new OrderNotFoundExcpetion("Order not found!"));
				if (order.getUser() != Luser) {
					throw new BadRequestException("Unauthorized operation.");
				}

				List<CustOrderQuantities> quantList = order.getOrders();
				for (CustOrderQuantities quants : quantList) {
					Inventory inventory = quants.getProduct().getInventory();
					inventory.setStockQuantity(inventory.getStockQuantity() + quants.getQuantity());
					Product product = quants.getProduct();
					product.setInventory(inventory);
					productRepo.save(product);
				}

				orderRepo.delete(order);
			}
		} catch (OrderNotFoundExcpetion | BadRequestException e) {
			throw new BadRequestException(e.getMessage());
		}

		catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}

		return "Successfully deleted.";
	}

	@Override
	@Transactional
	public String cancelOrder(String username, long orderId, List<Long> quantityIds) throws Exception {
		// TODO Auto-generated method stub
		LocalUser Luser = userRepo.findByEmail(username);
		try {
			CustOrder order = orderRepo.findById(orderId)
					.orElseThrow(() -> new OrderNotFoundExcpetion("Order not found!"));
			if (order.getUser() != Luser) {
				throw new BadRequestException("Unauthorized operation.");
			}
			List<CustOrderQuantities> orders = order.getOrders();
			for (CustOrderQuantities quants : orders) {
				if (quantityIds.contains(quants.getId())) {
					Inventory inventory = quants.getProduct().getInventory();
					inventory.setStockQuantity(inventory.getStockQuantity() + quants.getQuantity());
					Product product = quants.getProduct();
					product.setInventory(inventory);
					productRepo.save(product);
					Double total = order.getTotal();
					order.setTotal(total - quants.getQprice());
					orderQRepo.delete(quants);
					orderRepo.save(order);
				}
			}
		} catch (OrderNotFoundExcpetion | BadRequestException e) {
			throw new BadRequestException(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}

		return "Successfully removed.";
	}

}
