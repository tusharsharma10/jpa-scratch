
package com.main.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.main.jpa.entity.Message;
import com.main.jpa.service.JpaPersistenceService;

public class JpaClient {
	public static void main(String[] args) {
		
		//configuration uses persistence.xml file in META-INF folder
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
		
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction txn = em.getTransaction();
       
		JpaPersistenceService service = new JpaPersistenceService();		
		
/*		try {
       			txn.begin();

        			Message msg= new Message("Tushar Sharma");
        			em.persist(msg);
  
	        		txn.commit();
       		}	catch(Exception e) {
	        			if(txn != null) { txn.rollback(); }
	        			e.printStackTrace();
        		}	finally {
        				if(em != null) { em.close(); }
        		}
*/        		
       
        
        //service.mergeMethodExample(txn,em,emf);		
        service.lazyFetch(txn, em, emf);
	}
}














