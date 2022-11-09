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
import com.gimapp.repository.IClasesGymRepository;
import com.gimapp.repository.IDatosBancariosRepository;
import com.gimapp.repository.ITarifasRepository;
import com.gimapp.repository.IUserRepository;

//controller de la clase producto

@Controller
@RequestMapping("/gimnasio") //path donde vamos a apuntar después de la url para poder acceder al recurso (http:localhot:8080/gimnasio)
public class AppController {

	//instanciamos la interface, a trabes de este objeto vamos a poder usar todos los metodos de JpaRepository
	
	@Autowired
	private IClasesGymRepository clasesGymRepository;
	
	@GetMapping("") //cuando yo llame a la raiz me muestra lo de aqui (raiz --> http:localhost:8080/gimnasio)
	public String home() {	
		return "home"; //nombre del archivo html que va a devolver
	} 
	
	@GetMapping("/clases") //cuando yo llame a la raiz me muestra lo de aqui (raiz --> http:localhost:8080/gimnasio)
	public String verClases(Model model) {	
		model.addAttribute("clasesGym", clasesGymRepository.findAll());
		return "clasesGymBbdd"; //nombre del archivo html que va a devolver
	} 

}
