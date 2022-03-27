package com.Controlmatic.PoS_System.model;

import com.Controlmatic.PoS_System.dao.ProductCatalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.util.*;

import static com.Controlmatic.PoS_System.model.DiscountHandler.makeDiscountedProduct;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

@Component
@Entity
@XmlRootElement(name = "Sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany
    private List<Product> products;             // The products that have been added to the sale
    private double totalSum;                    // The sum of the products in the sale
    @Transient
    private transient ProductCatalog catalog;   // The product catalog used
    @OneToMany(cascade = CascadeType.ALL)
    private List<Payment> result;
    private Date date;

    public Map<String, Integer> getCountPerProduct() {
        return countPerProduct;
    }

    @ElementCollection
    private Map<String, Integer> countPerProduct = new HashMap<>();

    public static final long serialVersionUID = 1015L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public void setCatalog(ProductCatalog catalog) {
        if(this.catalog == null)
            this.catalog = catalog;
        result = new ArrayList<>();
    }

    public Sale() {
        this.date = new Date();
        result = new ArrayList<>();
    }

    public Sale (@Qualifier("product") ProductCatalog catalog) {
        result = new ArrayList<>();
        products = new ArrayList<>();
        this.catalog = catalog;
    }

    public List<Payment> getPayment() {
        return result;
    }

    public void setPayment(List<Payment> result) {
        this.result = result;
    }

    public void addPaymentType(Payment payment) {
        this.result.add(payment);
    }


    /**
     * Adds a product to the current sale, using the product's barcode
     * Takes into account the discount if there is one
     *
     * @param barcode The barcode of the product to be added
     * @param discount The percentage of discount
     */
    public void addToSaleByBarcode(String barcode, String discount) throws IOException {
        double parsedDiscount;
        Product p;
        try {
            parsedDiscount = parseDouble(discount);
        } catch (NumberFormatException e) {
            parsedDiscount = 0;
        }

        p = makeDiscountedProduct(catalog.findByBarcode(parseInt(barcode)), parsedDiscount);

        products.add(p);
        addToHashMap(p);
        totalSum = TotalSum.calculateNewTotalSum(p, totalSum);
    }

    private void addToHashMap(Product p) {
        countPerProduct.put(p.getName(), getCountFromList(p, products));
    }

    private int getCountFromList(Product p, List<Product> products) {
        int count = 0;
        for (Product product : products) {
            if(Objects.equals(p.getName(), product.getName())) {
                count++;
            }
        }
        return count;
    }

    private void removeFromHashMap(Product p) {

    }


    /**
     * Adds a product to the current sale, using the product's name
     * Takes into account the discount if there is one
     *
     * @param name The name of the product to be added
     * @param discount The percentage of discount
     */
    public void addToSaleByName(String name, String discount) throws IOException {
        double parsedDiscount;
        Product p;
        try {
            parsedDiscount = parseDouble(discount);
        } catch (NumberFormatException e) {
            parsedDiscount = 0;
        }
        p = makeDiscountedProduct(catalog.findByName(name), parsedDiscount);
        products.add(p);
        totalSum = TotalSum.calculateNewTotalSum(p, totalSum);
    }


    /**
     * Removes a product from the current sale
     *
     * @param listIdx The index of the product in "products"
     */
    public void removeFromSale(int listIdx) {
        products.remove(products.get(listIdx));
        totalSum = TotalSum.calculateTotalSum(products);
    }

    public List<Product> getProducts() {
        return products;
    }

    public ObservableList<Product> asObservable() {
        return FXCollections.observableArrayList(products);
    }

    public double getTotalSum() {
        return totalSum;
    }

    public Date getDate() {
        return date;
    }

    public void setDate() {
        this.date = new Date();
    }

    /**
     * Saves a specific sale
     * Makes it possible to shelf an incomplete sale
     *
     * @param filename The name of the file to write to
     * @param sale The sale to be saved
     */
    public static void save(String filename, Sale sale) {
        try {
            FileOutputStream fout = new FileOutputStream(filename);
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(sale);
            oout.flush();
            oout.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a saved sale
     *
     * @param path The path to the file where the sale is saved
     * @return Returns the sale if found
     */
    public static Sale load(String path) {
        try{
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(file);
            Sale sale = (Sale) in.readObject();
            in.close();
            return sale;
        } catch(IOException ex) {
            System.out.println("Unable to read your file");
            return null;
        } catch(ClassNotFoundException ex) {
            System.out.println("Couldn't find your file");
            return null;
        }
    }
}
