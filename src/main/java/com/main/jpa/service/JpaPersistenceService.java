package com.main.jpa.service;

import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import com.main.jpa.entity.Guide;
import com.main.jpa.entity.Message;
import com.main.jpa.entity.Student;

public class JpaPersistenceService {

	/**
	 * Example method to understand Merge concept
	 * Check how the row in DB changes
	 * 
	 */
	
	public void mergeMethodExample(EntityTransaction txn,EntityManager em,EntityManagerFactory emf){
		
		txn.begin();
		Message msg = new Message("Tauqeer Al hasan");
		em.persist(msg);
		txn.commit();
		em.close();
		
		/**
		 * since em.close is called persistence context is closed and now no dirty check happens
		 */
		msg.setText("El mohamaddy");
		
		/**
		 * So need to open another entityManager and on call of merge method the update query will be issued.
		 */
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();;
		
		em2.merge(msg);
		em2.getTransaction().commit();
		em2.close();
		
	
	}
	
	/**
	 * First Level caching scope Entity Manager
	 * If we find a record from Db using em.find()
	 * and again try to execute em.find()
	 * The query will not be ran again by hibernate 
	 * and we'll obtain the record by it's cached copy
	 */
	
	
	/**
	 * Lazy fetching example
	 * For the side that contains set of records here the Guide entity which is the One side in
	 * the relationship will by default lazily fetch its corresponding student objects.
	 * 
	 */
	
	public void lazyFetch(EntityTransaction txn,EntityManager em,EntityManagerFactory emf){
		
		txn.begin();
		
		/**
		 * Adding values to tables
		 */
		/*Guide g1 = new Guide("Mohit");
		Guide g2 = new Guide("Sanjay Daud");
		em.persist(g1);
		em.persist(g2);
		
		Student s1 = new Student("Tarun Prabhat", g1);
		Student s2 = new Student("Tinku Gupta", g2);
		Student s3 = new Student("Shib Soni", g1);
		Student s5 = new Student("Shashi bhai", g2);
		
		em.persist(s1);
		em.persist(s2);
		em.persist(s3);
		em.persist(s5);*/
	
		/**
		 * Lazy Fetching values from table
		 */
		Guide g1 = em.find(Guide.class, 10);
		Set<Student>studSet = g1.getStudentSet();
		
		System.out.println(studSet.size());
		txn.commit();
		
		em.close();
		
		/**
		 * Commented code for getting Lazy Initialization exception
		 * after closing the Entity Manager
		 */
		//System.out.println(studSet.size());
	}
	
	
	
	
	
	
	/**
	 * Sample code 
	 * @param txn
	 * @param em
	 * @param emf
	 */
	
	public void sampleCode(EntityTransaction txn,EntityManager em,EntityManagerFactory emf){
		
		txn.begin();
		
		
		txn.commit();
		
		em.close();
		
	}
	
	
}
