/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.resources;

import es.ujaen.dae.socialnetworkmaven.dto.TaskDTO;
import es.ujaen.dae.socialnetworkmaven.entities.Controlador;
import static java.lang.Math.log;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/Task")
public class TaskResource {
    
    @Autowired
    private Controlador controller;
    
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void newTask(@RequestBody String nameTask) {
        String username = UserResource.autentificationUser();
        controller.createTask(nameTask, username);
    }
    
    @RequestMapping(value="/lookup", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public TaskDTO showTask() {
        String username = UserResource.autentificationUser();
        return controller.getTask(username);
    }
    
    @RequestMapping(value="/lookupALL", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<TaskDTO> showAllTask() {
        String username = UserResource.autentificationUser();
        return controller.getUsersTask(username);
    }
    
    @RequestMapping(value="/{tid}/{name}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void editTaskName(@PathVariable("tid") int tid, @PathVariable("name") String name) {
        String username = UserResource.autentificationUser();
       controller.updateTask(tid, name);
    }
    
    @RequestMapping(value="/{tid}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteTask(@PathVariable("tid") int tid){
        String username = UserResource.autentificationUser();
        controller.deleteTask(tid);
    }
    
    
}
