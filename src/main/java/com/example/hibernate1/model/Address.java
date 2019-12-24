package com.example.hibernate1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

@Entity
@org.hibernate.annotations.OptimisticLocking(type=OptimisticLockType.ALL)
@org.hibernate.annotations.DynamicUpdate()
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private String id;
    
    @Column(name = "STREET", nullable = false, length = 100)
    private String street;

    @Column(name = "CITY", nullable = false, length = 100)
    private String city;

    @Column(name = "NUMBER", nullable = true, length = 5)
    private String number;

    @OneToOne(mappedBy = "address")
    private Bar bar;

    public String getId() {
        return this.id;
    }

    public String getStreet() {
        return this.street;
    }

    public String getCity(){
        return this.city;
    }

    public String getNumber(){
        return this.number;
    }
    public Bar getBar(){
       return this.bar;
    }
    public void setBar(Bar bar) {
        this.bar = bar;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public void setCity(String city) {
        this.city=city;
    }
    public void setNumber(String number) {
        this.number=number;
    }
}