/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.resources;

import es.ujaen.dae.socialnetworkmaven.dto.UsersDTO;
import es.ujaen.dae.socialnetworkmaven.entities.Controlador;
import es.ujaen.dae.socialnetworkmaven.entities.Users;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertUser;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 *
 * @author miguel
 */
@Controller
@Component
@RequestMapping("/users")
public class UserResource {
    
    @Autowired
    private Controlador controller;
    
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({ExceptionInsertUser.class})
    public void handlerParametroIncorrecto(){}
    
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ExceptionInsertUser.class})
    public void handlerRecursoExistente(){}
    
    
    @RequestMapping(value="/new", method=RequestMethod.PUT, consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody UsersDTO user){
        if(user == null)
            throw new ExceptionInsertUser();
         controller.createUser(user.getName(), user.getPassword(), user.getLocation());
    }
    
    //Para autentificación por parte del usuario se ha hecho copy paste de la diapositiva 25 Lección 19
    
    public static String autentificationUser(){
        
        Object main = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (main instanceof UserDetails) {
            username = ((UserDetails)main).getUsername();
        }else{
            username = main.toString();
        }
        
        return username;
    }
    
    @RequestMapping(value="/search/{name}", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Collection<UsersDTO> searchUser(@PathVariable(value="name") String name){
        
        Users u = controller.search1Target(name);
        if(u != null)
            return controller.searchNearUsers(name, u.getLocation());
        else
            throw new ExceptionInsertUser();
    }
    
    @RequestMapping(value="/loginTest", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody String loginTest(){
        return "correcto";
    }
    
    
}
