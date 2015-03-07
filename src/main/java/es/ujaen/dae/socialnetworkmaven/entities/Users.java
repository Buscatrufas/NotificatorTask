/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


 */
package es.ujaen.dae.socialnetworkmaven.entities;

import es.ujaen.dae.socialnetworkmaven.dto.UsersDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author miguel
 */
@Entity
public class Users implements Serializable {

    @Id
    private String name;
    private String password;
    private String location;
    private String userRole;
    @ManyToMany(fetch=FetchType.LAZY)
    private Collection<Users> friends;
    @ManyToMany(fetch=FetchType.LAZY)
    private Collection<Comment> msgOnWall;
    @ManyToMany(fetch=FetchType.LAZY)
    private Collection<Task> tasks;

    public Users() { }
    
    public Users(String _name, String _password, String location) {
        this.friends = new ArrayList();
        this.msgOnWall = new ArrayList();
        this.name = _name;
        this.password = _password;
        this.location = location;
        this.userRole = "ROLE_USER";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }


    public UsersDTO getUserDTO() {
        
        return new UsersDTO(name, "", location);
    }

    public Collection<Users> getFriends() {
        return Collections.unmodifiableCollection(friends);
    }

    public void addFriend(Users f) {
        friends.add(f);
    }

    public Collection<Comment> getMsgOnWall() {
        return Collections.unmodifiableCollection(msgOnWall);
    }

    public void setMsgOnWall(Comment msg) {
        this.msgOnWall.add(msg);
    }
    
    @Override
    public String toString() {
        if (location != null) {
            return this.name + " " + this.location;
        } else {
            return this.name;
        }
    }
    
    public Collection<Users> getFriendList(){
        return friends;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public Collection<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Collection<Task> tasks) {
        this.tasks = tasks;
    }
    
    
}
