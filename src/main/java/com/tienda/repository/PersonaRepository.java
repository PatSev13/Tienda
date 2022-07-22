package com.tienda.repository;

import com.tienda.entity.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends CrudRepository<Persona, Long>{
    Persona findByNombre (String nombre); //Método para encontrar algo en la base de datos por x columna
    
    
}
