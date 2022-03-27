package UnitTests;

import com.Controlmatic.PoS_System.api.HTTPRequest;
import com.Controlmatic.PoS_System.api.JarHandler;
import com.Controlmatic.PoS_System.model.JarFile;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class HTTPRequestTests {

    static class RequestTestRunner {

        public static void main(String[] args) throws IOException, InterruptedException {

            if(JarFile.getCardReaderJar() == null || JarFile.getCashBoxJar() == null || JarFile.getProductCatalogJar() == null) {
                JarFile.openJars();
                Thread.sleep(4000); //wait for jars to open properly
            }
            Result result = JUnitCore.runClasses(HTTPRequestTests.class);

            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(result.wasSuccessful());

        }

    }

    @Test
    public void GetRequestTest() throws IOException {
        String string = HTTPRequest.makeGetRequest("http://localhost:9001/cashbox/status");
        System.out.println(string);
        assertNotEquals("", string);
    }

    @Test
    public void PostRequestTest() throws IOException {
        HTTPRequest.makePostRequest("http://localhost:9001/cashbox/open");
        String string = HTTPRequest.makeGetRequest("http://localhost:9001/cashbox/status");
        System.out.println(string);
        assertEquals("OPEN", string);
    }

    @Test
    public void CardReaderPaymentTest() throws IOException, InterruptedException {
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/waitForPayment", "amount", "69");
        while(HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/status").equals("WAITING_FOR_CARD")) {
            Thread.sleep(1000); //wait for card swipe
        }
        assertEquals("DONE",HTTPRequest.makeGetRequest("http://localhost:9002/cardreader/status"));
        Thread.sleep(1000);
        HTTPRequest.makePostRequest("http://localhost:9002/cardreader/reset");
    }

}
