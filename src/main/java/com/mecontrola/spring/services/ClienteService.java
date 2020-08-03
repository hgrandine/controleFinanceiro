package com.mecontrola.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mecontrola.spring.domain.Cliente;
import com.mecontrola.spring.repositories.ClienteRepository;
import com.mecontrola.spring.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	 public Cliente find(Integer id) {
		 return repo.findById(id).orElseThrow(() -> new
				 ObjectNotFoundException( "Objeto nao encontrado! id: " + id + ", Tipo: " + Cliente.class.getName())); }
	 
}
