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

import com.gimapp.model.FichaUser;
import com.gimapp.model.Tarifas;
import com.gimapp.repository.ITarifasRepository;
import com.gimapp.repository.IUserRepository;

//controller de la clase producto

@Controller
@RequestMapping("/gimnasio") //path donde vamos a apuntar después de la url para poder acceder al recurso (http:localhot:8080/gimnasio)
public class UsuarioController {

	//instanciamos la interface, a trabes de este objeto vamos a poder usar todos los metodos de JpaRepository
	
	@Autowired
	private IUserRepository userRepository; //el new lo hace spring framework automaticamente
	
	@Autowired
	private ITarifasRepository tarifaRepository;
	
	//una forma de ir mostrando lo que hace el logg, en vez de usar un System.out.println()
	private final org.slf4j.Logger logg = LoggerFactory.getLogger(FichaUser.class);
	
	@GetMapping("") //cuando yo llame a la raiz me muestra lo de aqui (raiz --> http:localhost:8080/gimnasio)
	public String home() {	
		return "home"; //nombre del archivo html que va a devolver
	} 
	
	@GetMapping("/bbdd")
	public String bbdd(Model model) {	//el objeto model permite llevar info desde el controlador hasta la vista
		model.addAttribute("fichaUser", userRepository.findAll()); //a través del repository traemos todos los users de la bbdd y lo guardamos en la variable fichaUser
		return "bbdd";
	}
	
	@GetMapping("/tarifas") //http:localhot:8080/gimnasio/tarifas
	public String create(Model model) {
		model.addAttribute("tarifas", tarifaRepository.findAll()); //a través del repository traemos todos las tarifas de la bbdd y lo guardamos en la variable tarifas
		return "tarifas";
	}
	
	@PostMapping("/save") //http:localhot:8080/gimnasio/save
	public String save(FichaUser usuario){
		logg.info("Información del usuario, {}", usuario);
		List<FichaUser> users = userRepository.findAll();
		
		boolean correcto = true;
		
		for (FichaUser usr : users) {	
            if(usuario.getEmail() == usr.getEmail()) {
            	correcto = false;
            }
        }
		System.out.println("\n\n" + correcto + "\n\n");
		if(correcto == true) {
			userRepository.save(usuario);
			return "redirect:/gimnasio/bbdd";
		}else {
			return "create";
		}
		
	}
	
	//@PathVariable nos permite definir un atributo que viene en la URL y que lo vamos a recibir
	@GetMapping("/edit/{id}") //http:localhot:8080/productos/edit
	public String edit(@PathVariable Integer id, Model model) {
		FichaUser user = userRepository.getOne(id); //para coger el producto de la bbdd
		logg.info("Usuario recuperado {}", user);
		model.addAttribute("UsuarioEditado", user);
		return "edit";
	}
	
	@GetMapping("/delete/{id}") //http:localhot:8080/gimnasio/edit/{id}
	public String delete(@PathVariable Integer id) {
		FichaUser user = userRepository.getOne(id); //para coger el producto de la bbdd
		logg.info("Usuario eliminado {}", user);
		userRepository.delete(user); 
		return "redirect:/gimnasio/bbdd";
	}
	
	@GetMapping("/alta/{id}") //http:localhot:8080/productos/alta{id}
	public String alta(@PathVariable Integer id, Model model) {
		Tarifas tarif = tarifaRepository.getOne(id); //para coger el producto de la bbdd
		logg.info("Tarifa recuperada {}", tarif);
		model.addAttribute("tarifa", tarif);
		return "create";
	}
	
}
