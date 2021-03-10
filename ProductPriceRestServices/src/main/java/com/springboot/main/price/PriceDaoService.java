package com.springboot.main.price;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceDaoService {
	@Autowired
	public PriceDaoService(PriceRepository priceRepo) {
		priceRepository = priceRepo;
	}

	private PriceRepository priceRepository;
	private int priceCount;

	@Autowired
	public void setPriceRepository(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	private static List<Price> prices = new ArrayList<>();

	public List<Price> findAll() {
		return prices;
	}

	public Price save(Price price) throws Exception {
		if (price.getId() == null) {
			price.setId(++priceCount);
		}
		//Set<Currency> currencies = Currency.getAvailableCurrencies();
		//if (!(!(price.getCurrency() ==null) &&currencies.contains(price.getCurrency())))
			//throw new Exception("Invalid Currency: " + price.getCurrency() + ". Aborting execution.");
		prices.add(price);
		return price;
	}

	public List<Price> saveAll(List<Price> prices) {
		this.prices.addAll(prices);
		return this.prices;
	}

	public Price findOne(int id) {
		for (Price price : prices) {
			if (price.getId() == id)
				return price;
		}
		return null;
	}

	public Price deleteById(int id) {
		Iterator<Price> iterator = prices.iterator();
		while (iterator.hasNext()) {
			Price price = iterator.next();
			if (price.getId() == id) {
				iterator.remove();
				return price; // returns the deleted resource back
			}
		}
		return null;
	}
}