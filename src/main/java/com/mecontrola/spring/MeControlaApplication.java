package com.mecontrola.spring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mecontrola.spring.domain.Categoria;
import com.mecontrola.spring.domain.Produto;
import com.mecontrola.spring.repositories.CategoriaRepository;
import com.mecontrola.spring.repositories.ProdutoRepository;

@SpringBootApplication
public class MeControlaApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRep;
	
	@Autowired
	private ProdutoRepository produtoRep;
	
	public static void main(String[] args) {
		SpringApplication.run(MeControlaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRep.saveAll(Arrays.asList(cat1, cat2));
		produtoRep.saveAll(Arrays.asList(p1,p2,p3));
	}

}
