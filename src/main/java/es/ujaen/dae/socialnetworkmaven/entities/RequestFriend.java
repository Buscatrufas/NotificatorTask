/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.entities;

import es.ujaen.dae.socialnetworkmaven.dto.RequestFriendDTO;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author miguel
 */
@Entity
public class RequestFriend implements Serializable{
    
    @Id
    @org.hibernate.annotations.GenericGenerator(name="request-id", strategy = "hilo")
    @GeneratedValue(generator = "request-id")
    private int idRequest;
    private String userTo;
    private String userFrom;

    public RequestFriend(){}
    
    public RequestFriend(String userTo, String userFrom){
        
        this.userTo = userTo;
        this.userFrom = userFrom;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }
    
    public RequestFriendDTO getRequestFriendDTO(){
        
        return new RequestFriendDTO(userTo, userFrom);
    }
}
