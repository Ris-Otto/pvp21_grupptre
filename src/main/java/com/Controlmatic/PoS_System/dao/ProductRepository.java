package com.Controlmatic.PoS_System.dao;

import com.Controlmatic.PoS_System.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Currently a runtime database
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{

    Product findByBarCode(int barCode);

    List<Product> findByKeyword(String keyword);

    Product findByName(String name);



}
