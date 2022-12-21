package com.gimapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

@Controller
@RequestMapping("/gimnasio/inicioSesion")
public class InicioSesionUserController {
	
	@Autowired
	private IUserRepository usuarioRepository;
	
	@Autowired
	private ITarifasRepository tarifaRepository;
	
	@Autowired
	private IDatosBancariosRepository datosBancariosRepository;
	
	@Autowired
	private IClasesGymRepository clasesGymRepository;
	
	@Autowired
	private IBbddUsersClases bbddUserClasesRepository;
	
	@Autowired
	private IBbddUsersTarifas bbddUserTarifasRepository;
	
	private FichaUser usuario;
	private DatosBancarios datosBancarios;
	private Tarifas tarifaUser;
	private BbddUsersClases userClases = new BbddUsersClases();
	private BbddUsersTarifas userTarifas = new BbddUsersTarifas();
	
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
				System.out.println("datos Bancarios" + datosBancarios + "\n\n");
				
				List<BbddUsersTarifas> bbddUserTarifas = bbddUserTarifasRepository.findAll();		
				for(BbddUsersTarifas t: bbddUserTarifas) {
					if(t.getIdUser().equals(usuario.getId())) {
						tarifaUser = tarifaRepository.getOne(t.getIdTarifa());
						System.out.println("datos Bancarios" + tarifaUser + "\n\n");
					}
				}
			}
			return "redirect:/gimnasio/inicioSesion";
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
		
		if(usuario.getIdDatosBancarios() == null) {
			return "perfil";
		}else {
			model.addAttribute("datosBancarios", datosBancarios);
			model.addAttribute("tarifa", tarifaUser);
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
		return "redirect:/gimnasio/inicioSesion";
	}
	
	@GetMapping("/tarifas") //http:localhot:8080/gimnasio/tarifas
	public String tarifas(Model model) {
		model.addAttribute("tarifas", tarifaRepository.findAll()); //a través del repository traemos todos las tarifas de la bbdd y lo guardamos en la variable tarifas
		return "tarifas";
	}
	
	@GetMapping("/contratarTarifa/{id}") //http:localhot:8080/gimnasio/tarifas
	public String tarifaContratada(@PathVariable Integer id, Model model, RedirectAttributes redirectAttrs) {
		List<BbddUsersTarifas> bbddUserTarifas = bbddUserTarifasRepository.findAll();
		tarifaUser = tarifaRepository.getOne(id);
		System.out.println("tarifa" + tarifaUser + "\n\n");

		for(BbddUsersTarifas t: bbddUserTarifas) {
			if(t.getIdTarifa().equals(id) && t.getIdUser().equals(usuario.getId())) {
				redirectAttrs
	            .addFlashAttribute("mensaje", "Ya tienes esa tarifa contratada")
	            .addFlashAttribute("clase", "success");
				return "redirect:/gimnasio/inicioSesion/tarifas";
			}else if(!(t.getIdTarifa().equals(id)) && t.getIdUser().equals(usuario.getId()) && datosBancarios != null){
				t.setIdTarifa(id);
				bbddUserTarifasRepository.save(t);
				model.addAttribute("user", usuario);

				return "perfilParaGuardar";
			}
		}
		
		userTarifas.setIdTarifa(id);
		userTarifas.setIdUser(usuario.getId());
		
		model.addAttribute("usuario", usuario);
		return "pago";
	}
	
	@PostMapping("/savePago")
	public String guardarMetodoPago(DatosBancarios datos, Model model) {
		model.addAttribute("user", usuario);
		model.addAttribute("tarifa", tarifaUser);
		
		datosBancariosRepository.save(datos);
		
		bbddUserTarifasRepository.save(userTarifas);
		
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
		
		//borrar sus clases de bbdd --> clases_gym_usuarios
		List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
	
		for(BbddUsersClases c: bbddUserClases) {
			if(c.getIdUser().equals(usuario.getId())) {
				bbddUserClasesRepository.deleteById(c.getId());
			}
		}
		userClases = new BbddUsersClases();
		
		//borrar sus tarifas de bbdd --> tarifas_gym_usuarios
		List<BbddUsersTarifas> bbddUserTarifas = bbddUserTarifasRepository.findAll();
		
		for(BbddUsersTarifas t: bbddUserTarifas) {
			if(t.getIdUser().equals(usuario.getId())) {
				bbddUserTarifasRepository.deleteById(t.getId());
			}
		}
		userTarifas = new BbddUsersTarifas();
		
		
		return "perfil";
	}
	
	@GetMapping("/clasesUser")
	public String verClasesUser(Model model) {
		model.addAttribute("clasesGym", clasesGymRepository.findAll());
		model.addAttribute("user", usuario);
		
		return "clasesGymUsuario";
	}
	
	@GetMapping("/apuntarseClase/{id}")
	public String apuntarseClase(@PathVariable Integer id, RedirectAttributes redirectAttrs) {		
		//comprobamos si el usuario tiene una tarifa contratada
		boolean tieneTarifa = false;
		List<BbddUsersTarifas> tarifasUsers = bbddUserTarifasRepository.findAll();
		
		for(BbddUsersTarifas t: tarifasUsers) {
			if(t.getIdUser().equals(usuario.getId())) {
				tieneTarifa = true;
			}
		}
		
		//si no tiene tarifa asignada o metodo de pago
		if(tieneTarifa == true || usuario.getIdDatosBancarios() != null) {
			userClases.setIdClase(id);
			userClases.setIdUser(usuario.getId());
			
			List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
			
			for(BbddUsersClases c: bbddUserClases) {
				//si ya esta apuntado a esa clase
				if(c.getIdClase().equals(id) && c.getIdUser().equals(usuario.getId())) {
					redirectAttrs
		            .addFlashAttribute("mensaje", "Ya tienes esa clase contratada")
		            .addFlashAttribute("clase", "success");
					return "redirect:/gimnasio/inicioSesion/clasesUser";
				}
			}
			
			bbddUserClasesRepository.save(userClases);
			userClases = new BbddUsersClases();
			return "redirect:/gimnasio/inicioSesion/misClases";
		}else {
			redirectAttrs
            .addFlashAttribute("mensaje", "Primero tienes que escoger una tarifa e introducir un método de pago")
            .addFlashAttribute("clase", "success");
			return "redirect:/gimnasio/inicioSesion/clasesUser";
		}	
	}
	
	@GetMapping("/misClases")
	public String verMisClases(Model model) {
		List<ClasesGym> clases = clasesGymRepository.findAll();
		List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
		
		ArrayList<ClasesGym> misClases = new ArrayList<ClasesGym>();
	
		for(BbddUsersClases c: bbddUserClases) {
			if(c.getIdUser().equals(usuario.getId())) {
				for(ClasesGym cg: clases) {
					if(cg.getId().equals(c.getIdClase())) {
						misClases.add(cg);
					}
				}
			}
		}

		model.addAttribute("misClasesGym", misClases);
		return "misClasesUser";
	}
	
	@GetMapping("/darseDeBajaClase/{id}")
	public String borrarseDeClase(@PathVariable Integer id) {
		List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
		
		for(BbddUsersClases c: bbddUserClases) {
			if(c.getIdClase().equals(id))
				bbddUserClasesRepository.deleteById(c.getId());
		}
		
		return "redirect:/gimnasio/inicioSesion/misClases";
	} 
	
	@GetMapping("/cerrarSesion")
	public String cerrarSesion() {
		usuario = null;
		datosBancarios = null;
		tarifaUser = null;
		userClases = new BbddUsersClases();
		userTarifas = new BbddUsersTarifas();
		
		return "redirect:/gimnasio";
	}
	
	@PostMapping("/darseDeBaja")
	public String darseDeBaja() {
		usuarioRepository.deleteById(usuario.getId());
		
		//borrar el metodo de pago si lo tiene
		if(usuario.getIdDatosBancarios() != null) {
			datosBancariosRepository.deleteById(usuario.getIdDatosBancarios());;
		
			//borrar sus clases de bbdd --> clases_gym_usuarios
			List<BbddUsersClases> bbddUserClases = bbddUserClasesRepository.findAll();
		
			for(BbddUsersClases c: bbddUserClases) {
				if(c.getIdUser().equals(usuario.getId())) {
					bbddUserClasesRepository.deleteById(c.getId());
				}
			}
			
			//borrar sus tarifas de bbdd --> tarifas_gym_usuarios
			List<BbddUsersTarifas> bbddUserTarifas = bbddUserTarifasRepository.findAll();
			
			for(BbddUsersTarifas t: bbddUserTarifas) {
				if(t.getIdUser().equals(usuario.getId())) {
					bbddUserTarifasRepository.deleteById(t.getId());
				}
			}
			
		}
		
		usuario = null;
		datosBancarios = null;
		tarifaUser = null;
		userClases = new BbddUsersClases();
		userTarifas = new BbddUsersTarifas();
		
		return "redirect:/gimnasio";
	}
	
}
