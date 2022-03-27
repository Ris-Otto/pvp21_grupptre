package com.Controlmatic.PoS_System.service;

import com.Controlmatic.PoS_System.dao.PaymentDao;
import com.Controlmatic.PoS_System.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentDao paymentDao;

    @Autowired
    public PaymentService(@Qualifier("paymentDao") PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public int makeCashPayment(Sale sale) {
        paymentDao.makeCashPayment(sale);
        return 4;
    }

    public int makeCardPayment(Sale sale) throws IOException, InterruptedException, JAXBException {
        paymentDao.makeCardPayment(sale);
        return 5;
    }

    public int makeSplitPayment(Sale sale, double cash, double card) throws IOException, InterruptedException, JAXBException {
        paymentDao.makeSplitPayment(sale, cash, card);
        return 6;
    }

    public List<Sale> getCashPayments() {
        return paymentDao.getCashPayments();
    }

    public List<Sale> getCardPayments() {
        return paymentDao.getCardPayments();
    }

    public List<Sale> getAllPayments() {
        return paymentDao.getAllPayments();
    }
}
