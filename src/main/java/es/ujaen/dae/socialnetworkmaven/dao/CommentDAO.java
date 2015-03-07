/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.dao;

import es.ujaen.dae.socialnetworkmaven.entities.Comment;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertComment;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionUpdateComment;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author miguel
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS, readOnly = true)
@Component
public class CommentDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public CommentDAO(){}
    
    
    @Cacheable("Comments")
    public Comment searchComment(int mid){
        return em.find(Comment.class, mid);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertComment.class)
    public void insertComment(Comment c){
        try{
            em.persist(c);
            em.flush();
        }catch(Exception e){
            throw new ExceptionInsertComment();
        }
    }
    
    @CacheEvict(value = "Comments", key="#c.getMid()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionUpdateComment.class)
    public void updateComment(Comment c){
        try{
            em.merge(c);
        }catch(Exception e){
            throw new ExceptionUpdateComment();
        }
    }
    
    
}
