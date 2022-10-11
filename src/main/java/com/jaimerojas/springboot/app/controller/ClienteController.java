package com.jaimerojas.springboot.app.controller;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jaimerojas.springboot.app.models.entity.Cliente;
import com.jaimerojas.springboot.app.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	private IClienteService clienteService;

	@RequestMapping(value = {"/listar"}, method = RequestMethod.GET)
	public String listar(@Param("palabraClave") String palabraClave, @RequestParam Map<String, Object> params, Model model) {
		
		List<Cliente> clientes = clienteService.findAll(palabraClave); 
		
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 5);
		
		Page<Cliente> pageCliente = clienteService.getAll(pageRequest);
		
		int totalPage = pageCliente.getTotalPages();
		
		if(totalPage > 0) {
			
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("titulo", "LISTA DE CLIENTES");
		model.addAttribute("clientes", clientes);
		model.addAttribute("palabraClave", palabraClave);
		
		model.addAttribute("List",pageCliente.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		
		
		return "listar";
	}
	
	//paginacion en el controller
/*	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(@RequestParam Map<String, Object> params, Model model) {
		
		int page = params.get("page") != null ? (Integer.valueOf(params.get("page").toString()) - 1) : 0;
		
		PageRequest pageRequest = PageRequest.of(page, 4);
		
		Page<Cliente> pageCliente = clienteService.getAll(pageRequest);
		
		int totalPage = pageCliente.getTotalPages();
		
		if(totalPage > 0) {
			
			List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
			model.addAttribute("pages", pages);
		}
		
		model.addAttribute("List",pageCliente.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
		
		return "listar";
	}*/
	

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {

		Cliente cliente = new Cliente();

		model.put("titulo", "FORMULARIO CLIENTES");
		model.put("cliente", cliente);

		return "form";

	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "el ID del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "el ID del cliente no puede ser cero");
			return "redirect:/listar";
		}

		model.put("titulo", "Editar cliente");
		model.put("cliente", cliente);
	
		return "form";

	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {

			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}

		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con éxito!": "Cliente creado conn éxito!";
		
		clienteService.save(cliente);
		status.setComplete();

		flash.addFlashAttribute("success", mensajeFlash);

		return "redirect:listar";

	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") long id, RedirectAttributes flash) {

		if (id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "cliente eliminado con éxito!");
		}

		return "redirect:/listar";
	}

}
