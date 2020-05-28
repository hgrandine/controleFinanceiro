package com.mecontrola.spring;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mecontrola.spring.domain.Categoria;
import com.mecontrola.spring.domain.Cidade;
import com.mecontrola.spring.domain.Cliente;
import com.mecontrola.spring.domain.Endereco;
import com.mecontrola.spring.domain.Estado;
import com.mecontrola.spring.domain.Produto;
import com.mecontrola.spring.domain.enums.TipoCliente;
import com.mecontrola.spring.repositories.CategoriaRepository;
import com.mecontrola.spring.repositories.CidadeRepository;
import com.mecontrola.spring.repositories.ClienteRepository;
import com.mecontrola.spring.repositories.EnderecoRepository;
import com.mecontrola.spring.repositories.EstadoRepository;
import com.mecontrola.spring.repositories.ProdutoRepository;

@SpringBootApplication
public class MeControlaApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRep;
	
	@Autowired
	private ProdutoRepository produtoRep;
	
	@Autowired
	private CidadeRepository cidadeRep;
	
	@Autowired
	private EstadoRepository estadoRep;
	
	@Autowired
	private ClienteRepository clienteRep;
	
	@Autowired
	private EnderecoRepository enderecoRep;
	
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
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRep.saveAll(Arrays.asList(est1, est2));
		cidadeRep.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "11111111111", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("1111111111", "222222222"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "52", "ap 50", "jardim x", "11111111", cli1, c1);
		Endereco e2 = new Endereco(null, "Av dores", "300", "casa", "jardim y", "22222222", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRep.save(cli1);
		enderecoRep.saveAll(Arrays.asList(e1,e2));
		
	}

}
