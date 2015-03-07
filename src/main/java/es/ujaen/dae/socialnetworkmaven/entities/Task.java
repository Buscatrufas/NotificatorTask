/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.entities;

import es.ujaen.dae.socialnetworkmaven.dto.TaskDTO;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author miguel
 */

@Entity
public class Task implements Serializable {
    
    @Id
    @org.hibernate.annotations.GenericGenerator(name="task-id", strategy = "hilo")
    @GeneratedValue(generator = "task-id")
    int tid;
    String taskTitle;
    String ownerTask;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    private String type;
    
    public Task(){}
    
    public Task(String _taskTitle, String _ownerTask){
        taskTitle = _taskTitle;
        ownerTask = _ownerTask;
        creationDate = Calendar.getInstance().getTime();  
    }
    
    public Task(String _taskTitle, String _ownerTask, String _type){
        taskTitle = _taskTitle;
        ownerTask = _ownerTask;
        creationDate = Calendar.getInstance().getTime();
        type = _type;   
    }
    
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getOwnerTask() {
        return ownerTask;
    }

    public void setOwnerTask(String ownerTask) {
        this.ownerTask = ownerTask;
    }
    
    public TaskDTO getTaskDTO(){
        return new TaskDTO(tid, taskTitle, ownerTask, creationDate, type);
    }
    
     public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
