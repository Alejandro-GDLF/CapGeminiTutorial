package com.ccsw.tutorial.client;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.client.model.Client;

public interface ClientRepository extends CrudRepository<Client, Long>, JpaSpecificationExecutor<Client> {
	/**
	 * Método que permite recoger cuántos {@link Client} 
	 * cumplen la condición de la {@link Specification}
	 * 
	 * @param spec {@link Specification}
	 * 
	 * @return Número de clientes {@link long}
	 */
    @Override
    long count(Specification<Client> spec);
}
