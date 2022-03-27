package com.Controlmatic.PoS_System;

import com.Controlmatic.PoS_System.api.HTTPRequest;
import com.Controlmatic.PoS_System.dao.ProductRepository;
import com.Controlmatic.PoS_System.model.JarFile;
import com.Controlmatic.PoS_System.model.XML.ObjectToXML;
import com.Controlmatic.PoS_System.model.PriceLookupTable;
import com.Controlmatic.PoS_System.model.Product;
import javafx.application.Application;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication
public class PoSSystemApplication {

	public static void main(String[] args) throws IOException {
		//JarFile.openJars();
		Application.launch(CustomerWindowApplication.class, args);

	}

	/**
	 * Simulates wares in stock.
	 * @param repository Your stock.
	 * @return w/e
	 */
	@Bean
	public CommandLineRunner runner(ProductRepository repository) {
		return args -> {
			String string = HTTPRequest.makeGetRequest("http://localhost:9003/rest/findByKeyword/product").split("<products>")[1];
			String[] strings = string.split("</product>");

			PriceLookupTable.setPriceTable();

			for(int i = 0; i < strings.length-1; i++)
				strings[i] += "</product>";

			for(int j = 0; j < strings.length-1; j++) {
				Product p = ObjectToXML.unmarshal(Product.class, strings[j]);
				assert p != null;
				p.setPrice(PriceLookupTable.lookupPriceForItem(p.getName()));
				repository.save(p);
			}
		};
	}
}
