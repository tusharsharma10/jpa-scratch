
package com.main.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.main.jpa.service.JpaPersistenceService;

public class JpaClient {
	public static void main(String[] args) {

		// configuration uses persistence.xml file in META-INF folder
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");

		JpaPersistenceService service = new JpaPersistenceService(emf);

		service.secondLevelCaching();
	}
}
