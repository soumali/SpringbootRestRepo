package com.springboot.main.price;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Currency;
import java.util.Set;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "All details about the price")
@Entity
public class Price {
//Id as a primary key  
	@Id
	@GeneratedValue
	private Integer id;
	private Float value;

	private String currencyCode;

	protected Price() {

	}

	public Price(Integer id, Float value, String currency) throws Exception {
		super();
		this.id = id;
		this.value = value;
		Set<Currency> currencies = Currency.getAvailableCurrencies();
		if (currencies.contains(currency))
			this.currencyCode = currency;
		else
			throw new Exception("Invalid Currency: " + currency + ". Aborting execution.");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	public String getCurrency() {
		return currencyCode;
	}

	public void setCurrency(String currency) throws Exception {	
		this.currencyCode = currency;
	}

	@Override
	public String toString() {
		return String.format("{\"value\"=%f, \"name\"=%s}", value, currencyCode);
	}
}