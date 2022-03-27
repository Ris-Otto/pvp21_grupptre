package com.Controlmatic.PoS_System.model;

import org.springframework.stereotype.Component;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;


/**
 *
 */
@Component
@Entity
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private double subtotal;
    public String paymentType = this.getClass().getSimpleName();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Payment() {

    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
