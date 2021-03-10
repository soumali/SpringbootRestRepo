package com.springboot.main.price;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<Price,Integer> {

}
