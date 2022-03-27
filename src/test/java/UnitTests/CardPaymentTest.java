package UnitTests;

import com.Controlmatic.PoS_System.api.HTTPRequest;
import com.Controlmatic.PoS_System.dao.PaymentDataAccess;
import com.Controlmatic.PoS_System.model.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CardPaymentTest {

    //General test for making a card payment
    //This also tests that customer gets no bonus points when not swiping a bonus or combined card
    //!! CLICK SWIPE COMBINED CARD IN THE CARDREADER JAR WINDOW !!
    @Test
    public void makePaymentTestPaymentCard() throws IOException, InterruptedException {
        /*if(JarFile.getCardReaderJar() == null || JarFile.getCashBoxJar() == null || JarFile.getProductCatalogJar() == null) {
            JarFile.openJars();
            Thread.sleep(4000); //wait for jars to open properly
        }*/
        Card card = new Card("1234", "CREDIT");
        System.out.println(card.paymentType);
        //TODO
        /*System.out.println("!!! CLICK SWIPE PAYMENT CARD !!!");
        Product firstProduct = new Product(10, "Tomat", null, 1234, 24);
        Product secondProduct = new Product(14, "Gurka", null, 2345, 24);
        ArrayList<Product> products = new ArrayList<>();
        products.add(firstProduct);
        products.add(secondProduct);
        Card expectedCard = new Card(products, "1234567890123456", "CREDIT");
        Customer expectedCustomer = new Customer(expectedCard);
        PaymentDataAccess paymentDummyDataAccess = new PaymentDataAccess();
        int result = paymentDummyDataAccess.makeCardPayment(products);
        //!!! CLICK SWIPE PAYMENT CARD !!!
        Card actualCard = paymentDummyDataAccess.getCardByCardNumber("1234567890123456");
        Customer actualCustomer = paymentDummyDataAccess.getCustomerByCardNumber("1234567890123456");
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/reset");
        //JarFile.closeJars();
        assertEquals(1, result);*/
        /*assertEquals(expectedCard.getCardNumber(), actualCard.getCardNumber());
        assertEquals(expectedCard.getProducts(), actualCard.getProducts());
        assertEquals(expectedCard.getCardType(), actualCard.getCardType());
        assertEquals(24, TotalSum.calculateTotalSum(products), 0.01);
        assertEquals(expectedCustomer.getCard().getCardNumber(), actualCustomer.getCard().getCardNumber());
        assertEquals(0.0, actualCustomer.getBonusPoints(), 0.01);*/
        //TODO make this last test work
        //assertEquals(expectedCard, actualCard);
    }

}
