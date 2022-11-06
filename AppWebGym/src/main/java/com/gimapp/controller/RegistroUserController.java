package com.gimapp.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gimapp.model.FichaUser;
import com.gimapp.repository.IUserRepository;

@Controller
@RequestMapping("/gimnasio/registro")
public class RegistroUserController {

	//una forma de ir mostrando lo que hace el logg, en vez de usar un System.out.println()
	private final org.slf4j.Logger logg = LoggerFactory.getLogger(FichaUser.class);
	
	@Autowired
	private IUserRepository usuarioRepositoy;
	
	private FichaUser usuario;
	
	@ModelAttribute("usuario")
	public FichaUser newFichaUser() {
		return new FichaUser();
	}
	
	@GetMapping("")
	public String mostrarPaginaInicial() {
		return "homeIniciadaSesionUser";
	}
	
	@GetMapping("/register")
	public String mostrarFormularioRegistro() {
		return "registro";
	}
	
	
	@PostMapping("")
	public String registro(@ModelAttribute("usuario") FichaUser user, RedirectAttributes redirectAttrs) {
		List<FichaUser> usuariosBbdd = usuarioRepositoy.findAll();
		boolean coincide = false;
		
		for(FichaUser u: usuariosBbdd) {
			if(u.getEmail().toString().equalsIgnoreCase(user.getEmail().toString())) {
				coincide = true;
			}
		}
		
		if(coincide == false) {
			usuarioRepositoy.save(user);
			logg.info("user {}", user.getNombre());
			usuario = user;
			logg.info("user {}", usuario.getNombre());
			return "home";
		}else {		 
			return "registro";
		}	
	}
}
