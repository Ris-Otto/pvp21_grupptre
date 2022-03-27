package com.Controlmatic.PoS_System;

import com.Controlmatic.PoS_System.api.PaymentController;
import com.Controlmatic.PoS_System.dao.PaymentDataAccess;
import com.Controlmatic.PoS_System.model.Product;
import com.Controlmatic.PoS_System.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PoSSystemApplicationTests {

	private WebTestClient webTestClient;
	private TestRestTemplate testRestTemplate;
	private Integer port;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PaymentController paymentController;
	@Autowired
	PaymentService paymentService;
	@Autowired
    PaymentDataAccess paymentDA;

	@Test
	void contextLoads() {



	}


	@Test
	void paymentWorksThroughAllLayers() throws IOException, InterruptedException {
		List<Product> list = new ArrayList<>();
		Product product = new Product(10, "Tomat", null, 1234, 24);
		list.add(product);

		//paymentController.makeCardPayment(list);
		//assert(list.equals(paymentController.getAllPayments().get(0).getProducts()));
	}



}
