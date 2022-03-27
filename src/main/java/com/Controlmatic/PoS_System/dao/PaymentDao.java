package com.Controlmatic.PoS_System.dao;

import com.Controlmatic.PoS_System.model.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PaymentDao {

    void makeCashPayment(Sale sale);
    int makeCardPayment(Sale sale) throws IOException, InterruptedException, JAXBException;
    void makeSplitPayment(Sale sale, double cash, double card) throws IOException, InterruptedException, JAXBException;
    List<Sale> getCashPayments();
    List<Sale> getCardPayments();
    List<Sale> getAllPayments();
}
