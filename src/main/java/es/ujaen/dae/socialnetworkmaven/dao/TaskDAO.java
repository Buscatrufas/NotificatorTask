/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.dao;

import es.ujaen.dae.socialnetworkmaven.entities.Task;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionDeleteTask;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertTask;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionUpdateTask;
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
public class TaskDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public TaskDAO(){}
    
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionInsertTask.class)
    public void insertTask(Task t){
        try{
            em.persist(t);
            em.flush();
        }catch(Exception e){
            throw new ExceptionInsertTask();
        }
    }
    
    @CacheEvict(value = "Tasks", key="#t.getTid()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionUpdateTask.class)
    public void updateTask(Task t){
        try{
            em.merge(t);
        }catch(Exception e){
            throw new ExceptionUpdateTask();
        }
    }
    
    @CacheEvict(value = "Tasks", key="#t.getTid()")
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionDeleteTask.class)
    public void DeleteTask(int tid){
        try{
            em.createNativeQuery("DELETE FROM Task t WHERE t.tid = ?1", Task.class)
                    .setParameter(1, tid).executeUpdate();
        }catch(Exception e){
            throw new ExceptionDeleteTask();
        }
    }
   
    
    public List<Task> getAllTask(String username){
        List<Task> list = em.createQuery("select t from Task t WHERE t.ownerTask = ?1", Task.class)
                .setParameter(1, username).getResultList();
        return list;
    }
    
    
    public List<Task> getAnyTask(){
        return em.createNativeQuery("select t from Task t order by RAND() limit 1", Task.class).
                getResultList();
    }
    
    @Cacheable("Tasks")
    public Task getTask(int tid){
        return em.find(Task.class, tid);
    }
          
}
