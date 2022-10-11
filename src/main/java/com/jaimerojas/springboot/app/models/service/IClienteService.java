package com.jaimerojas.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jaimerojas.springboot.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll(String palabraClave);

	public Page<Cliente> getAll(Pageable pageable);
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
	
}
