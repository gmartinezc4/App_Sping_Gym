package com.gimapp.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gimapp.model.DatosBancarios;
import com.gimapp.model.FichaUser;
import com.gimapp.repository.IDatosBancariosRepository;
import com.gimapp.repository.ITarifasRepository;
import com.gimapp.repository.IUserRepository;

@Controller
@RequestMapping("/gimnasio/inicioSesion")
public class InicioSesionUserController {
	
	@Autowired
	private IUserRepository usuarioRepository;
	
	@Autowired
	private ITarifasRepository tarifaRepository;
	
	@Autowired
	private IDatosBancariosRepository datosBancariosRepository;
	
	private FichaUser usuario;
	private DatosBancarios datosBancarios;
	
	@ModelAttribute("usuario")
	public FichaUser newFichaUser() {
		return new FichaUser();
	}
	
	@GetMapping("")
	public String mostrarPaginaInicial() {
		return "homeIniciadaSesionUser";
	}
	
	//comprueba que haya un usuario con ese email y pass o que sea un admin
	@PostMapping("")
	public String registro(@ModelAttribute("usuario") FichaUser user) {
		List<FichaUser> usuariosBbdd = usuarioRepository.findAll();
		boolean coincide = false;
		
		for(FichaUser u: usuariosBbdd) {
			if(u.getEmail().toString().equalsIgnoreCase(user.getEmail().toString()) && 
					u.getPassword().toString().equalsIgnoreCase(user.getPassword().toString())) {
				coincide = true;
				usuario = u;
			}
		}
		
		if(user.getEmail().toString().equalsIgnoreCase("admin@admin.com") && 
				user.getPassword().toString().equalsIgnoreCase("Adminadmin123")) {
			return "homeAdmin";
		}
		
		if(coincide == true) {
			if(usuario.getIdDatosBancarios() != null) {
				datosBancarios = datosBancariosRepository.getOne(usuario.getIdDatosBancarios());
			}
			System.out.println("datos bancarios" + datosBancarios + "\n\n");
			return "homeIniciadaSesionUser";
		}else {		 
			return "redirect:/gimnasio/inicioSesion/login";
		}
	}
	
	@GetMapping("/login")
	public String mostrarFormularioRegistro() {
		return "login";
	}
	
	@GetMapping("/perfil")
	public String mostrarPerfil(Model model) {
		model.addAttribute("user", usuario);
		model.addAttribute("tarifas", tarifaRepository.findAll());
		
		if(usuario.getIdDatosBancarios() == null) {
			return "perfil";
		}else {
			model.addAttribute("datosBancarios", datosBancarios);
			return "perfilConBanco";
		}
	}
	
	@GetMapping("/datosBancarios")
	public String mostrarDatosBancarios(Model model) {
		System.out.println("tarjeta" + datosBancariosRepository.findById(usuario.getIdDatosBancarios()) + "\n\n");
		model.addAttribute("datosBancarios", datosBancariosRepository.findById(usuario.getIdDatosBancarios()));
		return "datosBancarios";
	}
	
	@PostMapping("/savePerfil")
	public String guardarUsuarioModificado(FichaUser user) {
		user.setIdDatosBancarios(usuario.getIdDatosBancarios());
		usuarioRepository.save(user);
	
		usuario = user;
		return "homeIniciadaSesionUser";
	}
	
	@GetMapping("/tarifas") //http:localhot:8080/gimnasio/tarifas
	public String tarifas(Model model) {
		model.addAttribute("tarifas", tarifaRepository.findAll()); //a través del repository traemos todos las tarifas de la bbdd y lo guardamos en la variable tarifas
		return "tarifas";
	}
	
	@GetMapping("/metodoPago")
	public String añadirMetodoPago(Model model) {
		model.addAttribute("usuario", usuario);
		return "pago";
	}
	
	@PostMapping("/savePago")
	public String guardarMetodoPago(DatosBancarios datos, Model model) {
		model.addAttribute("user", usuario);		
		datosBancariosRepository.save(datos);	
		
		usuario.setIdDatosBancarios(datos.getId());
		usuarioRepository.save(usuario);
		
		datosBancarios = datos;

		return "perfilParaGuardar";
	}
	
	@PostMapping("/borrarMetodoPago")
	public String editarMetodoPago(DatosBancarios datos, Model model) {
		model.addAttribute("user", usuario);
		datosBancariosRepository.deleteById(datosBancarios.getId());
		datosBancarios = null;
		
		usuario.setIdDatosBancarios(null);
		usuarioRepository.save(usuario);
		
		return "perfil";
	}
	
	@PostMapping("/darseDeBaja")
	public String darseDeBaja() {
		usuarioRepository.deleteById(usuario.getId());
		
		if(usuario.getIdDatosBancarios() != null) {
			datosBancariosRepository.deleteById(usuario.getIdDatosBancarios());
		}
		
		return "redirect:/gimnasio";
	}

}
