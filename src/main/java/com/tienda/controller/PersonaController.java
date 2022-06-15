package com.tienda.controller;

import com.tienda.entity.Persona;
import com.tienda.service.IPersonaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PersonaController {
    
    @Autowired
    private IPersonaService personaService;
    
    @GetMapping("/persona") //Para que cuando le llegue x html haga x cosas. Modo de conectar con el front-end
    public String index (Model model){ //Para poder enviar información al html
        List<Persona> listaPersona = personaService.getAllPersona();
        model.addAttribute("titulo", "Tabla Personas");
        model.addAttribute("personas", listaPersona);
        
        return "personas";
        
    }
}
