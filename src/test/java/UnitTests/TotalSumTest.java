package UnitTests;

import com.Controlmatic.PoS_System.model.Product;
import com.Controlmatic.PoS_System.model.Receipt;
import com.Controlmatic.PoS_System.model.TotalSum;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TotalSumTest {

    @Test
    void calculateTotalSum(){
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(14, "Gurka", null, 2345, 24));
        productList.add(new Product(14, "afddagfgd", null, 2345, 24));
        assertEquals(100, TotalSum.calculateTotalSum(productList));
    }

}