package com.mecontrola.spring.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.mecontrola.spring.domain.Cliente;
import com.mecontrola.spring.domain.enums.TipoCliente;
import com.mecontrola.spring.dto.ClienteNewDTO;
import com.mecontrola.spring.repositories.ClienteRepository;
import com.mecontrola.spring.resources.exceptions.FieldMessage;
import com.mecontrola.spring.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
				&& !BR.isValidCPF(objDto.getCpfOuCnpj())) list.add(new FieldMessage("cpfOuCnpj", "CPF invalido"));
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
				&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) list.add(new FieldMessage("cpfOuCnpj", "CNPJ invalido"));
		
		Cliente aux = clienteRepo.findByEmail(objDto.getEmail());
		
		if(aux != null) list.add(new FieldMessage("email", "Email ja existente")); 
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
