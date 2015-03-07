/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.dao;

import es.ujaen.dae.socialnetworkmaven.entities.RequestFriend;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionDeleteFriendRequest;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertFriendRequest;
import java.util.List;
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
public class RequestFriendDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    RequestFriendDAO(){}
    
    @Cacheable("FriendRequest")
    public RequestFriend buscar(int id){
       return em.find(RequestFriend.class, id);
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertFriendRequest.class)
    public void insertFriendRequest(RequestFriend rf){
        try{
            em.persist(rf);
            em.flush();
        }catch(Exception e){
            throw new ExceptionInsertFriendRequest();
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionDeleteFriendRequest.class)
    public void deleteFriendRequest(String userFrom, String userTo){
        try{
            em.createNativeQuery("DELETE FROM RequestFriend r WHERE r.userFrom = ?1 AND r.userTo = ?2 ", RequestFriend.class)
                    .setParameter(1, userFrom)
                    .setParameter(2, userTo).executeUpdate();
        }catch(Exception e){
            throw new ExceptionDeleteFriendRequest();
        }
    }
    
    public List<RequestFriend> getRequestFriend(String username){
            List<RequestFriend> lista = em.createQuery("select r FROM RequestFriend r WHERE r.userTo = ?1", 
                RequestFriend.class)
                .setParameter(1, username).getResultList();
            
            return lista;
        
    }
}
