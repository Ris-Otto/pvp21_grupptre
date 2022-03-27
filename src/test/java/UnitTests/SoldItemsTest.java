package UnitTests;

import com.Controlmatic.PoS_System.model.SoldItems;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;


public class SoldItemsTest {



    @Test
    void checkIfBetweenDates(){

        // WILL find something else to use other than date... :-)

        Date startDate = new Date(2021, 10, 1);
        Date endDate = new Date(2021, 10, 19);

        Date sameDate = new Date(2021, 10, 1);
        Date insideDate = new Date(2021, 10, 5);
        Date outsideDate = new Date(2021, 10, 20);

        SoldItems soldItems = new SoldItems();

        Assertions.assertTrue(soldItems.checkIfBetweenDates(startDate, endDate, sameDate));
        Assertions.assertTrue(soldItems.checkIfBetweenDates(startDate, endDate, insideDate));
        Assertions.assertFalse(soldItems.checkIfBetweenDates(startDate, endDate, outsideDate));

    }

}
