package com.springboot.main.product;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;


/**
 * Created by jt on 1/10/17.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
}