package com.mecontrola.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mecontrola.spring.domain.Pedido;
import com.mecontrola.spring.repositories.PedidoRepository;
import com.mecontrola.spring.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	 public Pedido buscar(Integer id) {
		 return repo.findById(id).orElseThrow(() -> new
				 ObjectNotFoundException( "Objeto nao encontrado! id: " + id + ", Tipo: " + Pedido.class.getName())); }
	 
}
