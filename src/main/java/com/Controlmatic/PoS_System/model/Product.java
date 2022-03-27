package com.Controlmatic.PoS_System.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

import java.util.Date;
import java.util.List;


/**
 * Represents a Product-object during runtime.
 * Initialised mainly from XML-schemas via ProductCatalog and ProductRepository
 * The @Entity tag works as a reminder for Spring Boot to treat it as a possible DB entry
 * The @XML***** tags works as pointers for JAXB and ObjectToXML to treat it as an XML-ready object
 * The @Field expirationDate is to be populated manually
 */
@XmlRootElement(name = "product")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private double price;

    private String name;

    private double discount;

    private int barCode;

    private double vat;


    @Convert(converter = StringListConverter.class)
    private List<String> keyword;


    public static final long serialVersionUID = 1003L;

    private Date expirationDate = null; // Expiration date = EXP

    private Date dateSold;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product() {
        dateSold = new Date();
    }

    public Product(double price, String name, Date EXP, int barcode, double vat) {
        this.price = price;
        this.name = name;
        this.expirationDate = EXP;
        this.barCode = barcode;
        this.vat = vat;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getBarCode() {
        return barCode;
    }

    public double getVat() {
        return vat;
    }

    public List<String> getKeyword() {
        return keyword;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() { return expirationDate; }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

}


