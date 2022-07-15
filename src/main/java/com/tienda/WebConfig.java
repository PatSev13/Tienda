package com.tienda;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration //Definir que es un archivo de configuración
public class WebConfig implements WebMvcConfigurer{
    
    @Bean //Inyectar dependencias de configuración, diciendo que el método es de configuración
    public SessionLocaleResolver localeResolver(){
        var slr = new SessionLocaleResolver(); //Creando un objeto
        slr.setDefaultLocale(new Locale("en")); //Seteando un objeto de tipo Locale, que indica
        //toda la información referente en el que se quiere mostrar la página (ubicación, dialecto, etc)
        return slr;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); //Escucha cuando se le pasa un cambio específicamente en el parámetro "lang"
        return lci;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor()); //InterceptorRegistry es quien revisa el cambio en todo el url
        
    }
    
}
