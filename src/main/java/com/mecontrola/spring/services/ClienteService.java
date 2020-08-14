package com.mecontrola.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mecontrola.spring.domain.Cidade;
import com.mecontrola.spring.domain.Cliente;
import com.mecontrola.spring.domain.Endereco;
import com.mecontrola.spring.domain.enums.TipoCliente;
import com.mecontrola.spring.dto.ClienteDTO;
import com.mecontrola.spring.dto.ClienteNewDTO;
import com.mecontrola.spring.repositories.ClienteRepository;
import com.mecontrola.spring.repositories.EnderecoRepository;
import com.mecontrola.spring.services.exceptions.DataIntegrityException;
import com.mecontrola.spring.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repoCli;
	
	@Autowired
	private EnderecoRepository repoEnd;
	
	 public Cliente find(Integer id) {
		 return repoCli.findById(id).orElseThrow(() -> new
				 ObjectNotFoundException( "Objeto nao encontrado! id: " + id + ", Tipo: " + Cliente.class.getName())); }
	 
	 public List<Cliente> findAll() {
			return repoCli.findAll();
		}
		 
	 	@Transactional
		public Cliente insert(Cliente obj) {
			obj.setId(null);
			obj = repoCli.save(obj);
			repoEnd.saveAll(obj.getEnderecos());
			return obj;
		}

		public Cliente update(Cliente obj) {
			Cliente newObj = find(obj.getId());
			updateData(newObj, obj);
			return repoCli.save(newObj);
		}

		public void delete(Integer id) {
			try {
				repoCli.deleteById(id);
			}catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possivel excluir porque ha pedidos relacionadas");
			}
		}

		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			return repoCli.findAll(pageRequest);
		}
		
		//instancia uma categoria a partir de um dto
		public Cliente fromDTO(ClienteDTO objDTO) {
			return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
		}
		
		public Cliente fromDTO(ClienteNewDTO objDTO) {
			Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), 
					TipoCliente.toEnum(objDTO.getTipo()));
			
			Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
			
			Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), 
					objDTO.getBairro(), objDTO.getCep(), cli, cid);
			
			cli.getEnderecos().add(end);
			cli.getTelefones().add(objDTO.getTelefone1());
			if(objDTO.getTelefone2() != null) cli.getTelefones().add(objDTO.getTelefone2());
			if(objDTO.getTelefone3() != null) cli.getTelefones().add(objDTO.getTelefone3());
			
			return cli;
		}
		 
		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}
}
