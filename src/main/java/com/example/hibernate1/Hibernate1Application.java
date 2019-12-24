package com.example.hibernate1;

import java.util.HashSet;
import java.util.Set;

import com.example.hibernate1.model.Address;
import com.example.hibernate1.model.Bar;
import com.example.hibernate1.model.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class Hibernate1Application {

	public static void main(String[] args) {
		SpringApplication.run(Hibernate1Application.class, args);
	}

	@Autowired
	private BarHibernateDAO barHibernateDAO;

	@Autowired
	private ManagedBarHibernateDAO managedBarHibernateDAO;

	@EventListener(ApplicationReadyEvent.class)
	public void ready() throws Exception {
		// doPersistAndRead();
		// optimisticLockExample();
		// doManagedPersistAndRead();
		// doManagedPersistAndReadOneToOne();
		doManagedPersistAndReadOneToMany();
	}

	public void doPersistAndRead() {
		System.out.println("doPersistAndRead=================>");
		
		Bar bar = new Bar();
		bar.setName("BarMe2");
		bar.setValue("This is the first Bar");
		String id = barHibernateDAO.save(bar);

		System.out.println("Saved with Id; " + id);

		Bar bar2 = barHibernateDAO.find(id);
		bar2.setValue("New Value");

		String id2 = barHibernateDAO.save(bar2);

		System.out.println("Updated with Id; " + id2);

		Bar barMe = barHibernateDAO.find(id2);
		System.out.println("Bar ID : " + barMe.getId());
		System.out.println("Bar Name : " + barMe.getName());
		System.out.println("Bar Value : " + barMe.getValue());

		System.out.println("doPersistAndRead=================<<");
	}

	public void doManagedPersistAndRead() {
		System.out.println("doManagedPersistAndRead=================>");
		
		Bar bar = new Bar();
		bar.setName("BarMe2");
		bar.setValue("This is the first Bar");
		String id = managedBarHibernateDAO.save(bar);

		System.out.println("Saved with Id; " + id);

		Bar bar2 = managedBarHibernateDAO.find(id);
		bar2.setValue("New Value");

		String id2 = managedBarHibernateDAO.save(bar2);

		System.out.println("Updated with Id; " + id2);

		Bar barMe = managedBarHibernateDAO.find(id2);
		System.out.println("Bar ID : " + barMe.getId());
		System.out.println("Bar Name : " + barMe.getName());
		System.out.println("Bar Value : " + barMe.getValue());

		System.out.println("doManagedPersistAndRead=================<<");
	}

	public void optimisticLockExample() throws Exception {
		System.out.println("optimisticLockExample=================>");
		
		Bar bar = new Bar();
		bar.setName("BarMe");
		bar.setValue("This is the first Bar");
		String id = barHibernateDAO.save(bar);
		System.out.println("Saved with Id; " + id);

		Bar bar2 = barHibernateDAO.find(id);
		bar2.setValue("New Value2");

		Bar bar3 = barHibernateDAO.find(id);
		bar3.setValue("New Value3");
		barHibernateDAO.save(bar3);

		try {
			System.out.println("Updatig with Id; " + id);
			String id2 = barHibernateDAO.save(bar2);
			System.out.println("Updated with Id; " + id2);
			throw new Exception("Optimistic is not thrown!");
		} catch(javax.persistence.OptimisticLockException e) {
			System.out.print("Optimisic lock: " + e.getMessage());
		}
		System.out.println("optimisticLockExample=================<");
	}

	public void doManagedPersistAndReadOneToOne() {
		System.out.println("doManagedPersistAndReadOneToOne=================>");
		
		Bar bar = new Bar();
		bar.setName("BarMe2");
		bar.setValue("This is the first Bar");
		Address address = new Address();
		address.setCity("istanbul");
		address.setStreet("murat sok.");
		address.setNumber("45/6");
		bar.setAdress(address);
		String id = managedBarHibernateDAO.save(bar);

		Bar foundBar = managedBarHibernateDAO.find(id);
		System.out.println(foundBar.getName());
		System.out.println(foundBar.getAddress().getCity());

		Address a = managedBarHibernateDAO.findAddress(foundBar.getAddress().getId());
		System.out.println(a.getBar().getName());
		System.out.println(a.getCity());

		System.out.println("doManagedPersistAndReadOneToOne=================<<");
	}

	public void doManagedPersistAndReadOneToMany() {
		System.out.println("doManagedPersistAndReadOneToMany=================>");
		
		Bar bar = new Bar();
		bar.setName("BarMe2");
		bar.setValue("This is the first Bar");
		Address address = new Address();
		address.setCity("istanbul");
		address.setStreet("murat sok.");
		address.setNumber("45/6");
		bar.setAdress(address);
		Set<Item> items = new HashSet<Item>();
		Item item1 = new Item();
		item1.setName("item1");
		Item item2 = new Item();
		item2.setName("item2");
		items.add(item1);
		items.add(item2);
		bar.setItems(items);
		String id = managedBarHibernateDAO.save(bar);

		Set<Item> items2 = managedBarHibernateDAO.findItems(id);
		System.out.println("Item2 size" + items2.size());

		Item item3 = new Item();
		item3.setName("item3");
		item3.setBar(bar);
		managedBarHibernateDAO.saveItem(item3);

		Set<Item> items3 = managedBarHibernateDAO.findItems(id);
		System.out.println("Item3 size" + items3.size());

		Item item11 = managedBarHibernateDAO.findItemByName("item1");
		System.out.println("Item1 name:" + item11.getName());

		System.out.println("doManagedPersistAndReadOneToMany=================<<");
	}

}
