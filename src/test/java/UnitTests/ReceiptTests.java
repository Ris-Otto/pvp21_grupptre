package UnitTests;

import com.Controlmatic.PoS_System.model.Product;
import com.Controlmatic.PoS_System.model.Receipt;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ReceiptTests {

    static class ReceiptTestRunner {

        public static void main(String[] args) {

            Result result = JUnitCore.runClasses(ReceiptTests.class);

            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(result.wasSuccessful());
        }
    }

    @Test
    void printReceiptExpectTrue() throws IOException {
    }

    @Test
    void printReceiptExpectFalse() throws IOException {

    }
}
