package com.mecontrola.spring;

import java.text.SimpleDateFormat;
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
import com.mecontrola.spring.domain.ItemPedido;
import com.mecontrola.spring.domain.Pagamento;
import com.mecontrola.spring.domain.PagamentoComBoleto;
import com.mecontrola.spring.domain.PagamentoComCartao;
import com.mecontrola.spring.domain.Pedido;
import com.mecontrola.spring.domain.Produto;
import com.mecontrola.spring.domain.enums.EstadoPagamento;
import com.mecontrola.spring.domain.enums.TipoCliente;
import com.mecontrola.spring.repositories.CategoriaRepository;
import com.mecontrola.spring.repositories.CidadeRepository;
import com.mecontrola.spring.repositories.ClienteRepository;
import com.mecontrola.spring.repositories.EnderecoRepository;
import com.mecontrola.spring.repositories.EstadoRepository;
import com.mecontrola.spring.repositories.ItemPedidoRepository;
import com.mecontrola.spring.repositories.PagamentoRepository;
import com.mecontrola.spring.repositories.PedidoRepository;
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
	
	@Autowired
	private PedidoRepository pedidoRep;
	
	@Autowired
	private PagamentoRepository pagRep;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRep;
	
	public static void main(String[] args) {
		SpringApplication.run(MeControlaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "cama");
		Categoria cat4 = new Categoria(null, "mesa");
		Categoria cat5 = new Categoria(null, "eletro");
		Categoria cat6 = new Categoria(null, "decoracao");
		Categoria cat7 = new Categoria(null, "perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat1.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRep.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PEDENTE, ped2, sdf.parse("20/10/20117 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRep.saveAll(Arrays.asList(ped1,ped2));
		pagRep.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRep.saveAll(Arrays.asList(ip1,ip2,ip3));
	}

}
