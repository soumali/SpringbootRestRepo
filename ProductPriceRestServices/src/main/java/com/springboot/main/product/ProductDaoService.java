package com.springboot.main.product;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.main.price.Price;
import com.springboot.main.price.PriceDaoService;

@Component
public class ProductDaoService {

	private List<Product> products = new ArrayList<>();
	private ProductRepository productRepository;
	private PriceDaoService priceDaoService;

	@Autowired
	public ProductDaoService(ProductRepository prodRepo) {
		productRepository = prodRepo;
	}

	public static int productsCount = 3;
//creating an instance of ArrayList

	@Autowired
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public PriceDaoService getPriceDaoService() {
		return priceDaoService;
	}

	@Autowired
	public void setPriceDaoService(PriceDaoService priceDaoService) {
		this.priceDaoService = priceDaoService;
	}

	public List<Product> findAll() {
		return products;
	}

	public Product save(Product product) throws Exception {
		if (product.getId() == null) {
			product.setId(++productsCount);
		}
		products.add(product);
		return product;
	}

	public List<Product> saveAll(List<Product> products) {
		this.products.addAll(products);
		return this.products;
	}

	public Product findOne(int id) {
		for (Product product : products) {
			if (product.getId() == id)
				return product;
		}
		return null;
	}

	public Product deleteById(int id) {
		Iterator<Product> iterator = products.iterator();
		while (iterator.hasNext()) {
			Product product = iterator.next();
			if (product.getId() == id) {
				iterator.remove();
				return product; // returns the deleted resource back
			}
		}
		return null;
	}

	public List<Product> getProductByPriceId(Integer priceId) {
		Price price = priceDaoService.findOne(priceId);
		List<Product> filteredProducts = new ArrayList<>();
		if (!(price == null)) {
			filteredProducts = products.parallelStream().filter(product -> product.getPrice().getId().equals(priceId))
					.collect(Collectors.toList());
		}
		return filteredProducts;
	}
}