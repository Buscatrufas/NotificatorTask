/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.entities;

import es.ujaen.dae.socialnetworkmaven.dto.CommentDTO;
import es.ujaen.dae.socialnetworkmaven.dto.UsersDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author miguel
 */
@Entity
public class Comment implements Serializable {

    @Id
    @org.hibernate.annotations.GenericGenerator(name="comment-id", strategy = "hilo")
    @GeneratedValue(generator = "comment-id")
    private int mid;
    private String owner;
    private String message;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationMsg;
    @OneToMany
    private Collection<Comment> replies;
    @ManyToMany
    private Map<String, Users> likes;

    public Comment() {}

    public Comment(String _owner, String _message) {
       this.message = _message;
       this.owner = _owner;
       creationMsg = Calendar.getInstance().getTime();
       replies = new ArrayList<>();
       likes = new TreeMap<>();
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationMsg() {
        return creationMsg;
    }

    public void setCreationMsg(Date creationMsg) {
        this.creationMsg = creationMsg;
    }

    public Collection<Comment> getReplies() {
        return replies;
    }

    public void addReply(Comment reply) {
        replies.add(reply);
    }

    public Map<String, Users> getLikes() {
        return likes;
    }

    public void setLikes(Map<String, Users> likes) {
        this.likes = likes;
    }
    
    public CommentDTO getCommentDTO() {
        if(replies == null || likes == null)
            return new CommentDTO(mid, message, owner, (Date)creationMsg.clone());
        else{
            Collection<CommentDTO> copyReplies = new ArrayList();
            for(Comment c : replies)
                copyReplies.add(new CommentDTO(c.mid, c.message, c.owner, (Date) c.creationMsg.clone()));
            
            Map<String, UsersDTO> copyLikes = new TreeMap();
            for(Users u : likes.values())
                copyLikes.put(u.getName(), u.getUserDTO());
            
            return new CommentDTO(mid, message, owner, (Date) creationMsg.clone(), copyReplies, copyLikes);
            
        }
    }
    
    public boolean addLike(Users u){
        if(likes.containsKey(u.getName()))
            return false;
        likes.put(u.getName(), u);
        return true;
    } 

}
