package com.jaimerojas.springboot.app.models.service;

import java.util.List;

import com.jaimerojas.springboot.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();

	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);
}
