package com.gimapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

import com.gimapp.model.BbddUsersClases;
import com.gimapp.model.BbddUsersTarifas;
import com.gimapp.model.ClasesGym;
import com.gimapp.model.DatosBancarios;
import com.gimapp.model.FichaUser;
import com.gimapp.model.Tarifas;
import com.gimapp.repository.IBbddUsersClases;
import com.gimapp.repository.IBbddUsersTarifas;
import com.gimapp.repository.IClasesGymRepository;
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
	
	@Autowired
	private ITarifasRepository tarifaRepository;
	
	@Autowired
	private IClasesGymRepository clasesGymRepository;
	
	@Autowired
	private IBbddUsersClases bbddUserClasesRepository;
	
	@Autowired
	private IBbddUsersTarifas bbddUserTarifasRepository;
	
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
	
		return "redirect:/gimnasio/admin/bbdd";
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
	
	@GetMapping("/tarifasAdmin")
	public String mostrarTarifasAdmin(Model model) {	//el objeto model permite llevar info desde el controlador hasta la vista
		model.addAttribute("tarifas", tarifaRepository.findAll());
		return "tarifasAdmin";
	}
	
	@GetMapping("/verTarifasUser/{id}")
	public String verTarifas(@PathVariable Integer id, Model model) {
		List<BbddUsersTarifas> bbddTarifasUser = bbddUserTarifasRepository.findAll();
		
		for(BbddUsersTarifas t: bbddTarifasUser) {
			if(t.getIdUser().equals(id)) {
				System.out.println("tarifa " + tarifaRepository.getOne(t.getIdTarifa()) + "\n\n" );
				model.addAttribute("tarifa", tarifaRepository.getOne(t.getIdTarifa()));
				return "verTarifas";
			}
		}
		return "redirect:/gimnasio/admin/bbdd";
		
	}
	
	@GetMapping("/añadirTarifa")
	public String añadirTarifa() {		
		return "añadirTarifa";
	}
	
	@PostMapping("/saveNewTarifa")
	public String saveNewTarifa(Tarifas newTarifa) {
		tarifaRepository.save(newTarifa);
		return "redirect:/gimnasio/admin/tarifasAdmin";
	}
	
	@GetMapping("/modificarTarifa/{id}")
	public String modificarTarifa(@PathVariable Integer id, Model model) {		
		model.addAttribute("tarifa", tarifaRepository.getOne(id));
		return "editTarifa";
	}
	
	@PostMapping("/saveModifiTarifa")
	public String saveModifiTarifa(Tarifas modifiTarifa) {
		tarifaRepository.save(modifiTarifa);
		return "redirect:/gimnasio/admin/tarifasAdmin";
	}
	
	@GetMapping("/eliminarTarifa/{id}")
	public String eliminarTarifa(@PathVariable Integer id) {		
		List<BbddUsersTarifas> bbddUserTarifas = bbddUserTarifasRepository.findAll();
		List<DatosBancarios> datosBancarios = datosBancariosRepository.findAll();
		List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
		
		for(BbddUsersTarifas t: bbddUserTarifas) {
			FichaUser usuario = userRepository.getOne(t.getIdUser());
			if(t.getIdTarifa().equals(id)) {
				//borramos la tarifa
				bbddUserTarifasRepository.deleteById(t.getId());
				
				//borramos los datos bancarios del usuario que tenia esa tarifa
				for(DatosBancarios d: datosBancarios) {
					if(d.getIdUser().equals(t.getIdUser())) {
						datosBancariosRepository.delete(d);
					}
				}
				usuario.setIdDatosBancarios(null);
				userRepository.save(usuario);
				
				//borramos las clases del usuario que tenia esa tarifa
				for(BbddUsersClases c: bbddUserClases) {
					if(c.getIdUser().equals(t.getIdUser()))
						bbddUserClasesRepository.deleteById(c.getId());
				}
			}
				
				
		}
		
		tarifaRepository.deleteById(id);
		
		return "redirect:/gimnasio/admin/tarifasAdmin";
	}
	
	@GetMapping("/clasesAdmin")
	public String mostrarClasesAdmin(Model model) {	//el objeto model permite llevar info desde el controlador hasta la vista
		model.addAttribute("clasesGym", clasesGymRepository.findAll());
		return "clasesGymAdmin";
	}
	
	@GetMapping("/verClases/{id}")
	public String verClases(@PathVariable Integer id, Model model) {
		List<ClasesGym> clases = clasesGymRepository.findAll();
		List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
		
		ArrayList<ClasesGym> misClases = new ArrayList<ClasesGym>();
	
		for(BbddUsersClases c: bbddUserClases) {
			if(c.getIdClase().equals(id)) {
				for(ClasesGym cg: clases) {
					if(cg.getId().equals(c.getIdClase())) {
						misClases.add(cg);
					}
				}
			}
		}

		model.addAttribute("misClasesGym", misClases);
		return "misClasesAdmin";
		
	}
	
	@GetMapping("/añadirClase")
	public String añadirClase() {		
		return "añadirClase";
	}
	
	@PostMapping("/saveNewClase")
	public String saveNewClase(ClasesGym newClase) {
		clasesGymRepository.save(newClase);
		return "redirect:/gimnasio/admin/clasesAdmin";
	}
	
	@GetMapping("/modificarClase/{id}")
	public String modificarClase(@PathVariable Integer id, Model model) {		
		model.addAttribute("clase", clasesGymRepository.getOne(id));
		return "editClase";
	}
	
	@PostMapping("/saveModifiClase")
	public String saveModifiClase(ClasesGym modifiClase) {
		clasesGymRepository.save(modifiClase);
		return "redirect:/gimnasio/admin/clasesAdmin";
	}
	
	@GetMapping("/eliminarClase/{id}")
	public String eliminarClase(@PathVariable Integer id) {		
		
		List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
		
		for(BbddUsersClases c: bbddUserClases) {
			if(c.getIdClase().equals(id))
				bbddUserClasesRepository.deleteById(c.getId());
		}
		
		clasesGymRepository.deleteById(id);
		
		return "redirect:/gimnasio/admin/clasesAdmin";
	}
	
}

