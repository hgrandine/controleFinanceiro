package com.mecontrola.spring.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mecontrola.spring.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		Categoria cat1 = new Categoria(1, "Andaime");
		Categoria cat2 = new Categoria(2, "Rodinha");
		
		List<Categoria> lista = new ArrayList<Categoria>();
		
		lista.add(cat1);
		lista.add(cat2);

		return lista;
	}
}
