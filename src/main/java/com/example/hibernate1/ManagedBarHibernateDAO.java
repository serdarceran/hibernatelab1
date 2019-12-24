package com.example.hibernate1;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.example.hibernate1.model.Address;
import com.example.hibernate1.model.Bar;
import com.example.hibernate1.model.Item;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
public class ManagedBarHibernateDAO {
    @PersistenceContext
    EntityManager em;

    public Bar find(String id) {
        Bar bar = em.find(Bar.class, id);
        return bar;
    }

    @Transactional
    public Set<Item> findItems(String id) {
        Bar bar = em.find(Bar.class, id);
        Hibernate.initialize(bar.getItems());
        return bar.getItems();
    }

    public Address findAddress(String id) {
        Address address = em.find(Address.class, id);
        return address;
    }

    @Transactional
    public String save(Bar bar) {
        System.out.println("Detached: " + em.contains(bar));

        if (bar.getId() == null) {
            em.persist(bar);
        } else {
            em.merge(bar);
        }

        for (Item i : bar.getItems()) {
            i.setBar(bar);
            if (i.getId() == null) {
                em.persist(i);
            } else {
                em.merge(i);
            }
        }
        return bar.getId();
    }

    @Transactional
    public String saveItem(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
        return item.getId();
    }

    public Item findItemByName(String name) {
        TypedQuery<Item> item = em.createNamedQuery("Item.findByName", Item.class);
        item.setParameter("name", name);
        return item.getSingleResult();
    }
 
}