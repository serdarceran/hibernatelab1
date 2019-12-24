package com.example.hibernate1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.OptimisticLockType;

@Entity
@org.hibernate.annotations.OptimisticLocking(type=OptimisticLockType.ALL)
@org.hibernate.annotations.DynamicUpdate()
@Table(name = "Item")
@NamedQueries({
    @NamedQuery(name="Item.findByName",
                query="SELECT i FROM Item i WHERE i.name = :name")
}) 
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private String id;
    
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name="BAR_ID", nullable=false)
    private Bar bar;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Bar getBar(){
       return this.bar;
    }
    public void setBar(Bar bar) {
        this.bar = bar;
    }
}