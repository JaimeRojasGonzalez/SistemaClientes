package com.jaimerojas.springboot.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jaimerojas.springboot.app.models.entity.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long> {
	
	@Query("SELECT c From Cliente c WHERE"
			+ " CONCAT(c.nombre, c.apellido, c.email, c.fechaNac, c.telefono)"
			+ " LIKE %?1%")
	public List<Cliente> findAll(String palabraClave);
		
}
