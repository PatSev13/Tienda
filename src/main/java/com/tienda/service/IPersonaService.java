package com.tienda.service;


import com.tienda.entity.Persona;
import java.io.FileNotFoundException;
import java.util.List;
import net.sf.jasperreports.engine.JRException;


public interface IPersonaService {
    public List<Persona> getAllPersona();
    public Persona getPersonaById (long id);
    public void savePersona(Persona persona);
    public void delete(long id);
    public Persona findByNombre (String nombre);
    public String exportReport(String reportFormat) throws FileNotFoundException, JRException;
}
