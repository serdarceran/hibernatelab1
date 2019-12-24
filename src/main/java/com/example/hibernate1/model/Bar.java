package com.example.hibernate1.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.OptimisticLockType;

@Entity
@org.hibernate.annotations.OptimisticLocking(type=OptimisticLockType.ALL)
@org.hibernate.annotations.DynamicUpdate()
@Table(name = "Bar", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ID"),
        @UniqueConstraint(columnNames = "NAME") })
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private String id;
    
    @Version
    @Column(name = "VERSION")
    private int version;
    
    @Column(name = "NAME", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "VALUE", unique = false, nullable = true, length = 1000)
    private String value;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID", nullable = false, referencedColumnName = "ID")
    private Address address;

    @OneToMany(mappedBy="bar")
    private Set<Item> items;
    
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Address getAddress(){
        return this.address;
    }

    public Set<Item> getItems(){
        return this.items;
    }
    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public String getValue(){
        return this.value;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setValue(String value) {
        this.value=value;
    }
    public void setAdress(Address address) {
        this.address = address;
    }
}