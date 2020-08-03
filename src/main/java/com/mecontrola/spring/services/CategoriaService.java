package com.mecontrola.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mecontrola.spring.domain.Categoria;
import com.mecontrola.spring.repositories.CategoriaRepository;
import com.mecontrola.spring.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	 public Categoria find(Integer id) {
		 return repo.findById(id).orElseThrow(() -> new
				 ObjectNotFoundException( "Objeto nao encontrado! id: " + id + ", Tipo: " + Categoria.class.getName())); }

	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}
	 
}
