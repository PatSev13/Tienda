package com.tienda.service;

import com.tienda.entity.Persona;
import com.tienda.repository.PersonaRepository;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class PersonaService implements IPersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> getAllPersona() {
        return (List<Persona>) personaRepository.findAll();
    }

    @Override
    public Persona getPersonaById(long id) {
        return personaRepository.findById(id).orElse(null);
    }

    @Override
    public void savePersona(Persona persona) {
        personaRepository.save(persona);
    }

    @Override
    public void delete(long id) {
        personaRepository.deleteById(id);
    }

    @Override
    public Persona findByNombre(String nombre) {
        return personaRepository.findByNombre(nombre);
    }

    @Override
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
        String path = "D:\\Java\\Desarrollo de aplicaciones web\\Tienda";
        
        //La lista tiene que ver con el archivo jrxml
        List<Persona> reportes = (List<Persona>) personaRepository.findAll();
        
        //Cargar el archivo y compilar
        File file = ResourceUtils.getFile("classpath:reportes.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportes); //Se le pasa la lista

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Patrick Sevilla");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        
        if (reportFormat.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path +"\\personas.html");
        }
        
        if (reportFormat.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasperPrint, path +"\\personas.pdf");
        }
        
        return "redirect:/persona";
    }

}
