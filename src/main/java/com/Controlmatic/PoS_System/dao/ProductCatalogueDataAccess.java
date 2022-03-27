package com.Controlmatic.PoS_System.dao;

import com.Controlmatic.PoS_System.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("product")
public class ProductCatalogueDataAccess implements ProductCatalog {

    private final ProductRepository repository;

    public ProductCatalogueDataAccess(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product findByBarcode(int barcode) {
        return repository.findByBarCode(barcode);
    }

    @Override
    public Product findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        return repository.findByKeyword(keyword);
    }

    @Override
    public void saveProduct(Product product) {

    }
}
