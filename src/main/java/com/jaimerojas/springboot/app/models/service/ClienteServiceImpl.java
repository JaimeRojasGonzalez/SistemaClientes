package com.jaimerojas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jaimerojas.springboot.app.dao.IClienteDao;
import com.jaimerojas.springboot.app.models.entity.Cliente;

@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao; 
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll(String palabraClave) {
		if(palabraClave != null) {
			return clienteDao.findAll(palabraClave);
		}
		return (List<Cliente>) clienteDao.findAll();
	}
	

	@Override
	@Transactional
	public void save(Cliente cliente) {
		
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		clienteDao.deleteById(id);
	}


	@Override
	public Page<Cliente> getAll(Pageable pageable) {
		
		return clienteDao.findAll(pageable);
	}

	

	
}
