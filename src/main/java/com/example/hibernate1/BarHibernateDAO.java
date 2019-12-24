package com.example.hibernate1;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;
import javax.transaction.Transactional;

import com.example.hibernate1.model.Bar;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarHibernateDAO {
 
    @Autowired
    private SessionFactory sessionFactory;

    public Bar find(String id) {
        EntityManager em = sessionFactory.createEntityManager();
        Bar bar = em.find(Bar.class, id);
        return bar;
    }

	public String save(Bar bar) {
        EntityManager em = sessionFactory.createEntityManager();
        EntityTransaction t = em.getTransaction();
        System.out.println("Detached: " + em.contains(bar));
        t.begin();
        if(bar.getId() == null) {
            em.persist(bar);
        } else {
            em.merge(bar);
        }
        t.commit();
        em.close();
        return bar.getId();
	}
 
}