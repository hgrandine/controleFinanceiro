package com.mecontrola.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mecontrola.spring.domain.Cliente;
import com.mecontrola.spring.dto.ClienteDTO;
import com.mecontrola.spring.repositories.ClienteRepository;
import com.mecontrola.spring.services.exceptions.DataIntegrityException;
import com.mecontrola.spring.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	 public Cliente find(Integer id) {
		 return repo.findById(id).orElseThrow(() -> new
				 ObjectNotFoundException( "Objeto nao encontrado! id: " + id + ", Tipo: " + Cliente.class.getName())); }
	 
	 public List<Cliente> findAll() {
			return repo.findAll();
		}
		 
		public Cliente insert(Cliente obj) {
			obj.setId(null);
			return repo.save(obj);
		}

		public Cliente update(Cliente obj) {
			Cliente newObj = find(obj.getId());
			updateData(newObj, obj);
			return repo.save(newObj);
		}

		public void delete(Integer id) {
			try {
				repo.deleteById(id);
			}catch (DataIntegrityViolationException e) {
				throw new DataIntegrityException("Não é possivel excluir porque ha entidades relacionadas");
			}
		}

		public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
			PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
			return repo.findAll(pageRequest);
		}
		
		//instancia uma categoria a partir de um dto
		public Cliente fromDTO(ClienteDTO objDTO) {
			return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
		}
		 
		private void updateData(Cliente newObj, Cliente obj) {
			newObj.setNome(obj.getNome());
			newObj.setEmail(obj.getEmail());
		}
}
