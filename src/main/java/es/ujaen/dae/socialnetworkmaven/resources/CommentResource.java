/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.resources;

import es.ujaen.dae.socialnetworkmaven.dto.CommentDTO;
import es.ujaen.dae.socialnetworkmaven.entities.Controlador;
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
@RequestMapping("/Comment")
public class CommentResource {

    @Autowired
    private Controlador controller;

    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void writeMsg(@RequestBody String msg) {
        String username = UserResource.autentificationUser();
        controller.createComment(username, msg);
    }

    @RequestMapping(value = "/addReply/{mid}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void addReply(@PathVariable(value = "mid") int mid, @RequestBody String text) {
        String username = UserResource.autentificationUser();
        controller.addReply(mid, username, text);
    }

    @RequestMapping(value = "/like/{mid}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addLike(@PathVariable(value = "mid") int mid) {
        String username = UserResource.autentificationUser();
        controller.addLike(mid, username);
    }

    @RequestMapping(value="/lookup", method=RequestMethod.GET, produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Collection<CommentDTO> showUserWall() {
        String username = UserResource.autentificationUser();
        return controller.showUserWall(username);
    }
}
