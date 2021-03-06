package br.edu.infnet.appatpb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import br.edu.infnet.appatpb.model.negocio.Cliente;
import br.edu.infnet.appatpb.model.negocio.Usuario;
import br.edu.infnet.appatpb.model.service.ClienteService;
import br.edu.infnet.appatpb.model.service.UsuarioService;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteService clienteService;
	
	
	@GetMapping(value = "/cliente")
	public String telaCadastro() {
		return "redirect:/cliente/lista";
	}

	//@RequestMapping(value = "/usuario/incluir", method = RequestMethod.POST)
	@PostMapping(value = "/cliente/incluir")
	public String incluir(Cliente cliente, @SessionAttribute("user") Usuario usuario) {
		cliente.setUsuario(usuario);
		clienteService.incluir(cliente);
	

		return "redirect:/cliente/lista";
	}
	
	@GetMapping(value = "/cliente/{id}/excluir")
	public String excluir(Model model, @PathVariable Integer id, @SessionAttribute("user") Usuario usuario) {
		
		try {
			clienteService.excluir(id);
		} catch (Exception e) {
			model.addAttribute("mensagem", "Não é possível excluir este cliente");
			
			
			return obterLista(model, usuario);
		}
		
		return "redirect:/cliente/lista";
	}
	
	@GetMapping(value = "/cliente/lista")
	public String obterLista(Model model, @SessionAttribute("user") Usuario usuario) {
		List<Cliente> lista = clienteService.obterListaUser(usuario);
		
		model.addAttribute("clientes", lista);
		
		return "cliente/cadastro";
	}
	
}
