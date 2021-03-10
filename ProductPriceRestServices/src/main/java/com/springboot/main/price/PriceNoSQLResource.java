package com.springboot.main.price;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import java.net.URI;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.main.price.Price;
import com.springboot.main.price.PriceRepository;

@RestController
public class PriceNoSQLResource {
	private PriceDaoService service;
	private PriceRepository priceRepository;

	@Autowired
	public PriceNoSQLResource(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	@Autowired
	public void setService(PriceDaoService service) {
		this.service = service;
	}

	@Autowired
	public void setPriceRepository(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	// Retrieving all products from the productRepository
	@GetMapping("/springbootmongo/listprices")
	public Iterable<Price> retriveAllProducts() {
		return priceRepository.findAll();
	}


	@GetMapping("/springbootmongo/getprice/{id}")
	public Resource<Price> retrivePrice(@PathVariable Integer id) {
		Optional<Price> price = priceRepository.findById(id);
		if (!price.isPresent())
			throw new PriceNotFoundException("id: " + id);
		Resource<Price> resource = new Resource<Price>(price.get());
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retriveAllProducts());
		resource.add(linkTo.withRel("all-products"));
		return resource;
	}

	@DeleteMapping("/springbootmongo/deleteprice/{id}")
	public void deletePrice(@PathVariable Integer id) {
		priceRepository.deleteById(id);
	}

	@PostMapping("/springbootmongo/addnewprice")
	public ResponseEntity<Object> createPrice(@Valid @RequestBody Price price) throws Exception {
		Price savedproduct = service.save(price);
		priceRepository.save(savedproduct);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedproduct.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}