package UnitTests;

import com.Controlmatic.PoS_System.model.DiscountHandler;
import com.Controlmatic.PoS_System.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DiscountHandlerTest {

    @Test
    void addDiscount() {
        Product product = new Product(10, "Tomat", null, 1234, 24);
        assertEquals(7.0, DiscountHandler.calculateDiscount(product, 30));
    }

    @Test
    void makeDiscountedProduct() {
        Product product = new Product(10, "Tomat", null, 1234, 24);
        Product discountedProduct = DiscountHandler.makeDiscountedProduct(product, 50);
        System.out.print(discountedProduct.getName());
        assertEquals(1.95, discountedProduct.getPrice());
    }
}
