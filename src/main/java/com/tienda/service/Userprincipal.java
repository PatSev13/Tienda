package com.tienda.service;

import com.tienda.entity.Persona;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*En esta clase se guardan los datos del usuario
User details almacena la información para realizar la autenticación pertinente
*/

public class Userprincipal implements UserDetails{
    private Persona persona;
    
    //Aquí se guarda la información
    public Userprincipal(Persona persona){
        this.persona = persona;
    }
    
    //GrantedAuthority es un objeto que nos indica qué permisos o roles que tienen los usuarios o persona
    //Por cada permiso y role se agregan a la nueva lista
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        
        //Extrae lista de permisos (nombre)
        //p es un elemento y "->" es como un for o una manera de iterar
        this.persona.getPermissionList().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p);
            authorities.add(authority);
        });
        
        //ROLE_ indica que lo que se recibe es un role y no un permiso
        //Extrae la lista de roles (ROLE_name)
        this.persona.getRoleList().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + r);
            authorities.add(authority);
        });
        
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.persona.getPassword();
    }

    @Override
    public String getUsername() {
        return this.persona.getNombre();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.persona.getActive() == 1;
    }
    
    
    
    
    
    
    
}
