package com.main.jpa.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.hibernate.Transaction;

import com.main.jpa.entity.Guide;
import com.main.jpa.entity.Message;
import com.main.jpa.entity.Student;

public class JpaPersistenceService {

	private EntityManagerFactory emf;

	public JpaPersistenceService(EntityManagerFactory emf) {

		this.emf = emf;
	}

	/**
	 * Example method to understand Merge concept Check how the row in DB
	 * changes
	 * 
	 */

	public void mergeMethodExample() {

		EntityManager em = emf.createEntityManager();

		EntityTransaction txn = em.getTransaction();

		txn.begin();
		Message msg = new Message("Tauqeer Al hasan");
		em.persist(msg);
		txn.commit();
		em.close();

		/*
		 * since em.close is called persistence context is closed and now no
		 * dirty check happens
		 */
		msg.setText("El mohamaddy");

		/*
		 * So need to open another entityManager and on call of merge method the
		 * update query will be issued.
		 */
		EntityManager em2 = emf.createEntityManager();
		em2.getTransaction().begin();
		;

		em2.merge(msg);
		em2.getTransaction().commit();
		em2.close();

	}

	/**
	 * First Level caching scope Entity Manager If we find a record from Db
	 * using em.find() and again try to execute em.find() The query will not be
	 * ran again by hibernate and we'll obtain the record by it's cached copy
	 */

	/**
	 * Lazy fetching example For the side that contains set of records here the
	 * Guide entity which is the One side in the relationship will by default
	 * lazily fetch its corresponding student objects.
	 * 
	 */

	public void lazyFetch() {

		EntityManager em = emf.createEntityManager();

		EntityTransaction txn = em.getTransaction();

		/*
		 * Adding values to tables
		 */

		/*
		 * Guide g1 = new Guide("Mohit"); Guide g2 = new Guide("Sanjay Daud");
		 * em.persist(g1); em.persist(g2);
		 * 
		 * Student s1 = new Student("Tarun Prabhat", g1); Student s2 = new
		 * Student("Tinku Gupta", g2); Student s3 = new Student("Shib Soni",
		 * g1); Student s5 = new Student("Shashi bhai", g2);
		 * 
		 * em.persist(s1); em.persist(s2); em.persist(s3); em.persist(s5);
		 */

		/*
		 * Lazy Fetching values from table
		 */
		Guide g1 = em.find(Guide.class, 10);
		Set<Student> studSet = g1.getStudentSet();

		System.out.println(studSet.size());
		txn.commit();

		em.close();

		/**
		 * Commented code for getting Lazy Initialization exception after
		 * closing the Entity Manager
		 */
		// System.out.println(studSet.size());
	}

	/**
	 * N+1 selects problem Demonstrating N+1 selects problem Multiple sql
	 * queries are issued for guide since they are eagerly fetched
	 */

	public void nPlusOneSelect() {

		EntityManager em = emf.createEntityManager();

		EntityTransaction txn = em.getTransaction();

		txn.begin();

		Query q = em.createQuery("select student from Student student");

		List<Student> students = q.getResultList();

		for (Student s : students) {
			System.out.println(s.getStudentName() + " " + s.getStudentId());

		}

		txn.commit();
		em.close();

	}

	/**
	 * To resolve n+1 selects problem 1. Changing entity FetchType student to
	 * Lazy - fetch = FetchType.LAZY but using 1 if we try to access guide
	 * objects value n+1 select problem will reappear. 2. Rewriting a join based
	 * query
	 */
	public void nPlusOneResolve() {

		EntityManager em = emf.createEntityManager();

		EntityTransaction txn = em.getTransaction();

		txn.begin();

		Query q = em.createQuery("select student from Student student left join fetch student.guide");

		List<Student> students = q.getResultList();

		for (Student s : students) {
			if (Objects.nonNull(s.getGuide()))
				System.out.println(s.getStudentName() + " " + s.getStudentId() + " " + s.getGuide().getGuideName());

		}

		txn.commit();
		em.close();

	}

	/**
	 * For large number of records if present in the table and we have to fetch
	 * them all we should use batch fetching.
	 * 
	 * @BatchSize annotation
	 */

	public void batchFetching() {

	}

	/**
	 * Merging detached object and showing the importance of CascadeMerge when
	 * new entitymanager is created and merge method is called firstly a db
	 * select sql is executed and then dirty checking is done with detached
	 * object.
	 */

	public void mergeDetached() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		txn.begin();

		Guide guide = em.find(Guide.class, 10);

		Set<Student> students = guide.getStudentSet();

		Student stud = null;

		for (Student s : students) {

			if (s.getStudentId() == 12) {
				stud = s;
			}
		}

		txn.commit();

		em.close();

		guide.setGuideName("Mohit Jain");

		// student name doesn't get updated in database since cascading for
		// merging is off
		stud.setStudentName("Tarun Parashar");

		EntityManager em2 = emf.createEntityManager();
		EntityTransaction txn2 = em2.getTransaction();

		txn2.begin();

		em2.merge(guide);

		txn2.commit();

		em2.close();
	}

	/**
	 * equals and hashcode checker
	 */

	public void equalsAndHashcodeChecker() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction txn = em.getTransaction();

		txn.begin();

		txn.commit();

//equals method by default implementation uses == operator which considers address for comparison for object
// for primitive it uses value of primitive
		
		Student s1 = new Student("Ronaldo", null);
		Student s2 = new Student("Ronaldo", null);
		 
		
		
		// returns false since it compares address
		System.out.println(s1 == s2);
		
		
		//returns true since it compares attribute in equals method
		System.out.println(s1.equals(s2));
		
		HashSet<Student> set = new HashSet<Student>();
		
		set.add(s1);
		
		//returns true after overriding of both hashCode method and equals method.
		System.out.println(set.contains(s2));
		
		em.close();

	}
	
	
	/**
	 * Second Level Caching
	 */
	
	public void secondLevelCaching(){
		
		EntityManager em1 = emf.createEntityManager();
		
		EntityTransaction txn1 = em1.getTransaction();
		
		txn1.begin();
		
		
		Guide g = em1.find(Guide.class, 10);
		System.out.println(g.getGuideName());
		
		
		
		txn1.commit();
		
		em1.close();
		
		
		
		EntityManager em2 = emf.createEntityManager();
		
		EntityTransaction txn2 = em2.getTransaction();
		
		txn2.begin();
		
		Guide g2 = em2.find(Guide.class, 10);
		System.out.println(g2.getGuideName());
		
		
		txn2.commit();
		
		em2.close();
		
	}
	
	

	/**
	 * Sample code
	 */

	public void sampleCode() {

	}

}
