package com.Controlmatic.PoS_System.api;

import com.Controlmatic.PoS_System.model.*;
import com.Controlmatic.PoS_System.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@RequestMapping("api/payment")
@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/cash", consumes = {"application/xml;charset=UTF-8"})
    public int makeCashPayment(@RequestBody Sale sale) {
        return paymentService.makeCashPayment(sale);
    }

    @PostMapping("/card")
    public int makeCardPayment(@RequestBody Sale sale) throws IOException, InterruptedException, JAXBException {
        return paymentService.makeCardPayment(sale);
    }

    @PostMapping("/split")
    public int makeSplitPayment(@RequestBody Sale sale,
                                @RequestParam double cash,
                                @RequestParam double card) throws IOException, InterruptedException, JAXBException {
        return paymentService.makeSplitPayment(sale, cash, card);
    }

    @GetMapping("/card")
    public List<Sale> getCardPayments() {
        return paymentService.getCardPayments();
    }

    @GetMapping("/cash")
    public List<Sale> getCashPayments() {
        return paymentService.getCashPayments();
    }

    @GetMapping()
    public List<Sale> getAllPayments() {
        return paymentService.getAllPayments();
    }

}
