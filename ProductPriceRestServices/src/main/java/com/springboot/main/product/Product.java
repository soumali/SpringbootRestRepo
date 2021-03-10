package com.springboot.main.product;

import java.util.Currency;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.springboot.main.price.Price;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "All details about the user")
@Entity
public class Product {
//Id as a primary key  
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	@OneToOne(mappedBy = "price")
	@Column(name = "current_price")
	private Price price;

	protected Product() {

	}

	public Product(Integer id, String name, Price price) throws Exception {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Price price) throws Exception {
			this.price = price;
	}

	public Price getPrice() {
		return price;
	}
	
	@Override
	public String toString() {
	 	return String.format(" {\"id:\"%d, \"name:\"%s,\"current_price:\"%s}", id , name,price);
		
	}
}