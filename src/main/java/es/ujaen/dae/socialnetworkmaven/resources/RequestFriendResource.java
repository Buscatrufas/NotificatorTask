/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.resources;

import es.ujaen.dae.socialnetworkmaven.dto.RequestFriendDTO;
import es.ujaen.dae.socialnetworkmaven.entities.Controlador;
import es.ujaen.dae.socialnetworkmaven.entities.RequestFriend;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/requestfriend")
public class RequestFriendResource {
    
    @Autowired
    private Controlador controller;
    
    @RequestMapping(value="/sendfriendrequest/{userTo}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void sendFriendRequest(@PathVariable(value="userTo") String userTo) {
        String username = UserResource.autentificationUser();
        controller.addRequestFriend(username, userTo);
    }
    
    @RequestMapping(value="/lookup", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<RequestFriendDTO> getRequestFriend(){
        String username = UserResource.autentificationUser();
        List<RequestFriend> requestFriend = controller.getRequestFriend(username);
        
        if(requestFriend != null){
            List<RequestFriendDTO> requestFriendDTO = new ArrayList();
            for(RequestFriend rf : requestFriend)
                requestFriendDTO.add(rf.getRequestFriendDTO());
            return requestFriendDTO;
        }else
            return null;
    }
    
    
    @RequestMapping(value="/reject/{username}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void rejectRequestFriend(@PathVariable(value="username") String username) {
        String me = UserResource.autentificationUser();
        controller.deleteRequestFriend(username, me);
    }
    
    @RequestMapping(value="/accept/{username}", method=RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void acceptRequestFriend(@PathVariable(value="username") String username) {
        String me = UserResource.autentificationUser();
        controller.acceptRequestFriend(username, me);
    }
}
