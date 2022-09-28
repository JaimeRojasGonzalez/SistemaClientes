package com.jaimerojas.springboot.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.jaimerojas.springboot.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long> {

	
	
	
}
