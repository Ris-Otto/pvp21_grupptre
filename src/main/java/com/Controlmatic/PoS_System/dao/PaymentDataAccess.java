package com.Controlmatic.PoS_System.dao;

import com.Controlmatic.PoS_System.api.HTTPRequest;
import com.Controlmatic.PoS_System.model.*;
import com.Controlmatic.PoS_System.model.XML.ObjectToXML;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository("paymentDao")
public class PaymentDataAccess implements PaymentDao {

    private final SaleRepository repository;

    public PaymentDataAccess(SaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public void makeCashPayment(Sale sale) {
        sale.addPaymentType(ObjectToXML.unmarshal(Cash.class, Cash.createUnMarshallable(sale.getTotalSum())));
        repository.save(sale);
    }

    @Override
    public void makeSplitPayment(Sale sale, double cashSubTotal, double cardSubTotal) throws IOException, InterruptedException {
        sale.addPaymentType(ObjectToXML.unmarshal(Cash.class, Cash.createUnMarshallable(cashSubTotal)));

        System.out.println(cardSubTotal);
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/waitForPayment", "amount", String.valueOf(cardSubTotal));
        while(HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/status").equals("WAITING_FOR_CARD")) {
            Thread.sleep(1000);
        }
        String paymentResult = HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/status");
        if (paymentResult.equals("DONE")) {
            String xmlText = HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/result");
            //System.out.println(xmlText);
            Card card = ObjectToXML.unmarshal(Card.class, xmlText);
            if(card == null) {
                System.out.println("Something went awry :/");
                return;
            }
            card.setSubtotal(cardSubTotal);
            sale.addPaymentType(card);
            //Tänk dig att det här printas ut vid någon fin terminal eller något... :)
            System.out.println("Result of payment card transaction: \n" + xmlText);
        }

        repository.save(sale);
    }

    /**
     *
     * makes a Card payment of the provided products
     * gets the Card from the CardReader.jar by making requests
     */
    //TODO hu e de om man betalar me cash men swipear sitt bonuskort så borde man väl o få bonuspoints
    //Jo men voivoi
    @Override
    public int makeCardPayment(Sale sale) throws IOException, InterruptedException {
        double amount = sale.getTotalSum();
        //Opens the CardReader for requests
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/waitForPayment", "amount", String.valueOf(amount));
        while(HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/status").equals("WAITING_FOR_CARD")) {
            Thread.sleep(1000); //icky wicky :/
        }
        String paymentResult = HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/status");
        if (paymentResult.equals("DONE")) {
            String xmlText = HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/result");
            //System.out.println(xmlText);
            Card test = ObjectToXML.unmarshal(Card.class, xmlText);

            if(test == null) {
                System.out.println("Something went awry :/");
                return -1;
            }

            test.setSubtotal(sale.getTotalSum());
            sale.addPaymentType(test);
            //System.out.println(test.getPaymentCardNumber());
            //Card card = addCard(sale.getProducts(), xmlText);

            //Customer customer = addCustomer(card);
            /*if (!XMLFileParser.getBonusCardNumber().equals("")) {
                customer.addBonusPoints(amount * 0.1);
            }*/
            //Card card = getCardByCardNumber(cardNumber);
            System.out.println("Result of payment card transaction: \n" + xmlText);
            repository.save(sale);
            return 1;
        }
        return 0;
    }

    /**
     *
     * @return returns specific and common values of the payment e.g. note(maybe?), Product(s)
     * ah prob borde return sedel och/eller slantar men oh well
     */
    @Override
    public List<Sale> getCashPayments() {
        List<Sale> result = new ArrayList<>();
        repository.findAllByResult_PaymentType("Cash").forEach(result::add);
        return result;
    }

    /**
     *
     * @return returns specific and common values of the payment e.g. cardnumber, holder, expiry, Product(s)
     * yes
     */
    @Override
    public List<Sale> getCardPayments() {
        List<Sale> result = new ArrayList<>();
        repository.findAllByResult_PaymentType("Card").forEach(result::add);
        return result;
    }

    /**
     *
     * @return abstractPayments will return only the common values of a payment e.g. Product(s)
     * actually never mind de som står ovanför
     */
    @Override
    public List<Sale> getAllPayments() {
        List<Sale> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

}
