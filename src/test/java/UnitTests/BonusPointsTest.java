package UnitTests;

import com.Controlmatic.PoS_System.api.HTTPRequest;
import com.Controlmatic.PoS_System.dao.PaymentDataAccess;
import com.Controlmatic.PoS_System.model.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BonusPointsTest {

    @Test
    public void addBonusPointsTestCombinedCard() throws IOException, InterruptedException {
        if(JarFile.getCardReaderJar() == null || JarFile.getCashBoxJar() == null || JarFile.getProductCatalogJar() == null) {
            JarFile.openJars();
            Thread.sleep(4000); //wait for jars to open properly
        }
        //TODO
        /*System.out.println("!!! CLICK SWIPE COMBINED CARD !!!");
        Product firstProduct = new Product(10, "Tomat", null, 1234, 24);
        Product secondProduct = new Product(14, "Gurka", null, 2345, 24);
        ArrayList<Product> products = new ArrayList<>();
        products.add(firstProduct);
        products.add(secondProduct);
        Card expectedCard = new Card(products, "1234567890123456", "CREDIT");
        Customer expectedCustomer = new Customer(expectedCard);
        double amount = TotalSum.calculateTotalSum(products);
        expectedCustomer.addBonusPoints(amount * 0.1);
        /*PaymentDataAccess paymentDummyDataAccess = new PaymentDataAccess();
        int result = paymentDummyDataAccess.makeCardPayment(products);
        //!!! CLICK SWIPE COMBINED CARD !!!
        Card actualCard = paymentDummyDataAccess.getCardByCardNumber("1234567890123456");
        Customer actualCustomer = paymentDummyDataAccess.getCustomerByCardNumber("1234567890123456");
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/reset");
        double expectedBonusPoints = (55 + 14) * 0.1;
        JarFile.closeJars();
        assertEquals(1, result);
        assertEquals(expectedCard.getCardNumber(), actualCard.getCardNumber());
        //bonuscardnumber is cardnumber on a combined card
        //assertEquals(expectedCard.getBonusCardNumber(), actualCard.getBonusCardNumber());
        assertEquals(expectedBonusPoints, actualCustomer.getBonusPoints(), 0.01);
        assertEquals(expectedCard.getProducts(), actualCard.getProducts());
        assertEquals(expectedCard.getCardType(), actualCard.getCardType());
        assertEquals(69, TotalSum.calculateTotalSum(products), 0.01);*/
    }

    @Test
    public void bonusCardTest() throws IOException, InterruptedException {
        if(JarFile.getCardReaderJar() == null || JarFile.getCashBoxJar() == null || JarFile.getProductCatalogJar() == null) {
            JarFile.openJars();
            Thread.sleep(4000); //wait for jars to open properly
        }
        //TODO
        /*System.out.println("!!! CLICK SWIPE BONUS CARD !!!");
        Product firstProduct = new Product(10, "Tomat", null, 1234, 24);
        Product secondProduct = new Product(14, "Gurka", null, 2345, 24);
        ArrayList<Product> products = new ArrayList<>();
        products.add(firstProduct);
        products.add(secondProduct);
        Card expectedBonusCard = new Card(products, "9876543210987654", "BONUS");
        Customer expectedCustomer = new Customer(expectedBonusCard);
        double amount = TotalSum.calculateTotalSum(products);
        expectedCustomer.addBonusPoints(amount * 0.1);
        /*PaymentDataAccess paymentDummyDataAccess = new PaymentDataAccess();
        //paymentDummyDataAccess.makeCashPayment(products);
        expectedCustomer.addBonusPoints(amount * 0.1);
        Card actualBonusCard = paymentDummyDataAccess.getCardByCardNumber("9876543210987654");
        Customer actualCustomer = paymentDummyDataAccess.getCustomerByCardNumber("9876543210987654");
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/reset");
        double expectedBonusPoints = (55 + 13) * 0.1;
        JarFile.closeJars();
        //assertEquals(1, result);
        assertEquals(expectedBonusCard.getCardNumber(), actualBonusCard.getCardNumber());
        //bonuscardnumber is cardnumber on a combined card
        //assertEquals(expectedCard.getBonusCardNumber(), actualCard.getBonusCardNumber());
        assertEquals(expectedBonusPoints, actualCustomer.getBonusPoints(), 0.01);
        //assertEquals(expectedBonusCard.getProducts(), actualBonusCard.getProducts());
        assertEquals(expectedBonusCard.getCardType(), actualBonusCard.getCardType());
        assertEquals(68, TotalSum.calculateTotalSum(products), 0.01);*/
    }

}
