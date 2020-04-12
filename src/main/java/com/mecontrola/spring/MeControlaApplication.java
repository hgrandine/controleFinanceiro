package com.mecontrola.spring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mecontrola.spring.domain.Categoria;
import com.mecontrola.spring.repositories.CategoriaRepository;

@SpringBootApplication
public class MeControlaApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRep;
	
	public static void main(String[] args) {
		SpringApplication.run(MeControlaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		categoriaRep.saveAll(Arrays.asList(cat1, cat2));
	}

}
