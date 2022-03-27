package com.Controlmatic.PoS_System.dao;

import com.Controlmatic.PoS_System.model.Sale;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface SaleRepository extends CrudRepository<Sale, Long> {


    @NotNull
    Iterable<Sale> findAll();

    @Override
    @NotNull
    Optional<Sale> findById(@NotNull Long aLong);

    Iterable<Sale> findAllByResult_PaymentType(String paymentType);
}
