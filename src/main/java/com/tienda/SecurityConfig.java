package com.tienda;

import com.tienda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration //Para que entienda cuál es la configuración que tiene que tener la aplicación
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Es un puente que puede unir dos tipos de "mundos" (objetos) como service y repository
    @Autowired
    private UserService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserService getUserService() {
        return new UserService();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(getUserService());
        return daoAuthenticationProvider;
    }

    //Qué se hace si la autenticación fue éxitosa
    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler();
    }

    //Constructor
    public SecurityConfig(UserService userPrincipalDetailsService) {
        this.userDetailsService = userPrincipalDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    //El siguiente método funciona para hacer la autenticación del usuario
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //Puede ir a...
                .antMatchers("/persona", "/login", "/personaN")
                //Si es...
                .hasRole("ADMIN")
                .antMatchers("/persona", "/", "/login")
                .hasAnyRole("USER", "VENDOR", "ADMIN")
                .anyRequest().authenticated()
                .and()
                //Redirije a un login por defecto
                .formLogin()
                //Que redirija a un "end-point" especificado y que se redirija a algún otro end-point (petición o consulta, request al back-end)
                .loginPage("/login").permitAll().defaultSuccessUrl("/persona", true);
    }
}
