package com.springboot.main.product;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.main.price.Price;
import com.springboot.main.price.PriceDaoService;
import com.springboot.main.price.PriceNotFoundException;
import com.springboot.main.price.PriceRepository;

@RestController
public class ProductNoSQLResource {

	private ProductDaoService service;
	private ProductRepository productRepository;
	private PriceRepository priceRepository;
	private PriceDaoService priceDaoService;
	@Autowired
	public ProductNoSQLResource(ProductDaoService service, ProductRepository productRepository,
			PriceRepository priceRepository,PriceDaoService priceDaoService) {
		super();
		this.service = service;
		this.productRepository = productRepository;
		this.priceRepository = priceRepository;
		this.setPriceDaoService(priceDaoService);
	}

	@Autowired
	public void setService(ProductDaoService service) {
		this.service = service;
	}

	@Autowired
	public void setProductRepository(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Autowired
	public void setPriceRepository(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	@Autowired
	public void setPriceDaoService(PriceDaoService priceDaoService) {
		this.priceDaoService = priceDaoService;
	}
	
	// Retrieving all products from the productRepository
	@GetMapping("/springbootmongo/listproducts")
	public Iterable<Product> retriveAllProducts() {
		return productRepository.findAll();
	}


	@GetMapping("/springbootmongo/getproduct/{id}")
	public Resource<Product> retriveProduct(@PathVariable Integer id) {
		Optional<Product> product = productRepository.findById(id);
		if (!product.isPresent())
			throw new ProductNotFoundException("id: " + id);
		Resource<Product> resource = new Resource<Product>(product.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllProducts());
		resource.add(linkTo.withRel("all-products"));
		return resource;
	}

	@DeleteMapping("/springbootmongo/deleteproduct/{id}")
	public void deleteProduct(@PathVariable Integer id) {
		productRepository.deleteById(id);
	}

	@PostMapping("/springbootmongo/addnewproduct")
	public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product) throws Exception {
		Product savedproduct = service.save(product);
		productRepository.save(savedproduct);
		Price savedPrice = priceDaoService.save(product.getPrice());
		priceRepository.save(savedPrice);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedproduct.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/springbootmongo/listproductsbyprice")
	public Iterable<Product> retriveProductsByPrice(@PathVariable Integer priceId) {
		Optional<Price> price = priceRepository.findById(priceId);
		if (!price.isPresent())
			throw new PriceNotFoundException("id: " + priceId);
		List<Product> products = service.getProductByPriceId(priceId);
		return products;
	}
	
	@PutMapping("/springbootmongo/updateproductprice/{productId}/{priceVal}")
	public ResponseEntity<Object> updateProductPrice(@PathVariable Integer productId,@PathVariable Float priceVal) throws Exception {
		Optional<Product> product = productRepository.findById(productId);
		if (!product.isPresent())
			throw new ProductNotFoundException("id: " + productId);
		Price price = product.get().getPrice();
		price.setValue(priceVal);
		product.get().setPrice(price);
		Product savedproduct = service.save(product.get());
		productRepository.save(savedproduct);
		priceRepository.save(product.get().getPrice());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedproduct.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}