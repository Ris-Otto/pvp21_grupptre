package com.Controlmatic.PoS_System.dao;

import com.Controlmatic.PoS_System.model.Product;

import java.io.IOException;
import java.util.List;

public interface ProductCatalog {

    Product findByBarcode(int barcode) throws IOException;
    Product findByName(String name) throws IOException;
    List<Product> findByKeyword(String keyword) throws IOException;
    void saveProduct(Product product) throws IOException;

}
