/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.dao;

import es.ujaen.dae.socialnetworkmaven.entities.Users;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertUser;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionUpdateUser;
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
public class UsersDAO {
    
    @PersistenceContext
    private EntityManager em;
     
     public UsersDAO(){}
     
     @Cacheable("Users")
     public Users find(String name){
         return em.find(Users.class, name);
     }
     
     public List<Users> searchUsers(String query){
         List<Users> listUsers = em.createQuery("SELECT u FROM Users u WHERE u.name LIKE ?1", Users.class)
                 .setParameter(1,"%"+query+"%").getResultList();
         
         return listUsers;
                
     }
     
     
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertUser.class)
     public void createUser(Users user){
         try{
             em.persist(user);
             em.flush();
         }catch(Exception e){
             throw new ExceptionInsertUser();
         }
     }
     
     @CacheEvict(value = "Users", key="#user.getName()")
     @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionUpdateUser.class)
     public void updateUser(Users user){
         try{
             em.merge(user);
         }catch(Exception e){
             throw new ExceptionUpdateUser();
         }
     }
    
     
     
}
