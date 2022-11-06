package com.gimapp.controller;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gimapp.model.DatosBancarios;
import com.gimapp.model.FichaUser;
import com.gimapp.model.Tarifas;
import com.gimapp.repository.IDatosBancariosRepository;
import com.gimapp.repository.ITarifasRepository;
import com.gimapp.repository.IUserRepository;

//controller de la clase producto

@Controller
@RequestMapping("/gimnasio/admin") //path donde vamos a apuntar después de la url para poder acceder al recurso (http:localhot:8080/gimnasio)
public class AdminController {

	//instanciamos la interface, a trabes de este objeto vamos a poder usar todos los metodos de JpaRepository
	
	@Autowired
	private IUserRepository userRepository; //el new lo hace spring framework automaticamente
	
	@Autowired
	private IDatosBancariosRepository datosBancariosRepository;
	
	//una forma de ir mostrando lo que hace el logg, en vez de usar un System.out.println()
	private final org.slf4j.Logger logg = LoggerFactory.getLogger(FichaUser.class);
	
	@GetMapping("") //cuando yo llame a la raiz me muestra lo de aqui (raiz --> http:localhost:8080/gimnasio)
	public String home() {	
		return "homeAdmin"; //nombre del archivo html que va a devolver
	} 
	
	@GetMapping("/bbdd")
	public String bbdd(Model model) {	//el objeto model permite llevar info desde el controlador hasta la vista
		model.addAttribute("fichaUser", userRepository.findAll()); //a través del repository traemos todos los users de la bbdd y lo guardamos en la variable fichaUser	
		return "bbdd";
	}

	@GetMapping("/datosBancarios/{id}")
	public String mostrarDatosBancarios(@PathVariable Integer id, Model model) {
		FichaUser usuario = userRepository.getOne(id);
		
		if(usuario.getIdDatosBancarios() != null) {
			model.addAttribute("user", usuario);
			model.addAttribute("datosBancarios", datosBancariosRepository.getOne(usuario.getIdDatosBancarios()));
			return "datosBancarios";
		}else {
			return "redirect:/gimnasio/admin/bbdd";
		}
	}
	
	@GetMapping("/delete/{id}") //http:localhot:8080/gimnasio/edit/{id}
	public String delete(@PathVariable Integer id) {
		FichaUser user = userRepository.getOne(id); //para coger el producto de la bbdd
		userRepository.delete(user); 
		List<DatosBancarios> datosBancarios = datosBancariosRepository.findAll();
		
		for(DatosBancarios d: datosBancarios) {
			if(d.getIdUser().equals(user.getId())) {
				datosBancariosRepository.delete(d);
			}
		}
	
		return "redirect:/gimnasio/bbdd";
	}
	
	
}

