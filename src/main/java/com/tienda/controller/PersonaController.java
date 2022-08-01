package com.tienda.controller;

import com.tienda.entity.Pais;
import com.tienda.entity.Persona;
import com.tienda.service.IPaisService;
import com.tienda.service.IPersonaService;
import java.io.FileNotFoundException;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PersonaController {
    
    @Autowired
    private IPersonaService personaService;
    
    @Autowired
    private IPaisService paisService;
    
    @GetMapping("/persona") //Para que cuando le llegue x html haga x cosas. Modo de conectar con el front-end. Esto es un end-point
    public String index (Model model){ //Para poder enviar información al html
        List<Persona> listaPersona = personaService.getAllPersona();
        model.addAttribute("titulo", "Tabla Personas");
        model.addAttribute("personas", listaPersona);
        
        return "personas";
        
    }
    
    @GetMapping("/personaN") //Para que cuando le llegue x html haga x cosas. Modo de conectar con el front-end
    public String crearPersona (Model model){ //Para poder enviar información al html
        List<Pais> listaPaises = paisService.listCountry();
        model.addAttribute("persona", new Persona());
        model.addAttribute("paises", listaPaises);
        
        return "crear"; //Es el html
        
    }
    
    @PostMapping("/save") //Para enviar o guardar información
    public String guardarPersona (@ModelAttribute Persona persona){ //ModelAttribute es para enviar información del html a otro método
        personaService.savePersona(persona);
        
        return "redirect:/persona"; //Redirecciona a la página con la tabla
        
    }
    
    @GetMapping("/editPersona/{id}") //Se le pasa el id de la persona que se quiere editar
    public String editarPersona (@PathVariable("id") Long idPersona, Model model){ //La variable a buscar, long o el tipo que se defina y el nombre de la variable
        Persona persona = personaService.getPersonaById(idPersona); //Para devolver la persona que tiene el id que estamos pasando
        List<Pais> listaPaises = paisService.listCountry();
        model.addAttribute("persona", persona);
        model.addAttribute("paises", listaPaises);
        return "crear";
    }
    
    @GetMapping("/delete/{id}") //Se le pasa el id de la persona que se quiere eliminar
    public String elimnarPersona (@PathVariable("id") Long idPersona){ //La variable a buscar, long o el tipo que se defina y el nombre de la variable 
        //Se quita el Model model porque no necesito pasarle nada
        personaService.delete(idPersona); //Para devolver la persona que tiene el id que estamos pasando
        return "redirect:/persona";
    }     
    
    
    @GetMapping("/reporte/{format}")
    public String generarReporte(@PathVariable String format) throws FileNotFoundException, JRException{
        return personaService.exportReport(format);
    }
}
