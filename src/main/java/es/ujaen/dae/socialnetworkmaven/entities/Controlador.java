/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ujaen.dae.socialnetworkmaven.entities;

import es.ujaen.dae.localidadescercanas.Ciudad;
import es.ujaen.dae.localidadescercanas.CiudadInexistente_Exception;
import es.ujaen.dae.localidadescercanas.LocalidadesCercanas;
import es.ujaen.dae.localidadescercanas.LocalidadesCercanasService;
import es.ujaen.dae.socialnetworkmaven.dao.CommentDAO;
import es.ujaen.dae.socialnetworkmaven.dao.RequestFriendDAO;
import es.ujaen.dae.socialnetworkmaven.dao.TaskDAO;
import es.ujaen.dae.socialnetworkmaven.dao.UsersDAO;
import es.ujaen.dae.socialnetworkmaven.dto.CommentDTO;
import es.ujaen.dae.socialnetworkmaven.dto.TaskDTO;
import es.ujaen.dae.socialnetworkmaven.dto.UsersDTO;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionCommentNotFound;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionTaskNotFound;
import es.ujaen.dae.socialnetworkmaven.exceptions.ExceptionUserNotFound;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author miguel
 */
@Component
public class Controlador {

    @Autowired
    private UsersDAO userDAO;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private RequestFriendDAO requestFriendDAO;
    @Autowired
    private TaskDAO taskDAO;

    public Controlador() {
    }

    public void createUser(String name, String pass, String location) {
        userDAO.createUser(new Users(name, pass, location));
    }

    public Collection<Users> searchUser(String name) {
        return userDAO.searchUsers(name);
    }

    public void addRequestFriend(String userFrom, String userTo) {

        RequestFriend requestFriend = new RequestFriend(userTo, userFrom);
        requestFriendDAO.insertFriendRequest(requestFriend);

    }

    public void deleteRequestFriend(String userFrom, String userTo) {
        requestFriendDAO.deleteFriendRequest(userFrom, userTo);
    }

    public void createComment(String username, String msg) {
        Users u = userDAO.find(username);
        if (u == null) {
            throw new ExceptionUserNotFound();
        }
        Comment c = new Comment(username, msg);
        commentDAO.insertComment(c);
        u.setMsgOnWall(c);
        userDAO.updateUser(u);
    }

    public Users search1Target(String username) {
        return userDAO.find(username);
    }

    public Collection<UsersDTO> searchNearUsers(String username, String location) {

        LocalidadesCercanasService localidadescercanasservice = new LocalidadesCercanasService();
        LocalidadesCercanas proxy = localidadescercanasservice.getLocalidadesCercanasPort();

        List<Users> users = userDAO.searchUsers(username);
        List<UsersDTO> userList = new ArrayList();
        List<String> cities = new ArrayList();

        for (Users u : users) {
            userList.add(u.getUserDTO());
            cities.add(u.getLocation());
        }
        if (location.isEmpty()) {
            return userList;
        }
        List<UsersDTO> sortList = new ArrayList();
        try {
            List<Ciudad> sortCities = proxy.ciudadesPorDistancia(location, cities);
            for (Ciudad c : sortCities) {
                for (Users u : users) {
                    if (u.getLocation().equals(c.getNombre())) {
                        sortList.add(u.getUserDTO());
                    }
                }
            }
        } catch (CiudadInexistente_Exception e) {
            return userList;
        }

        return sortList;
    }

    public void addReply(int mid, String owner, String msg) {
        Users u = userDAO.find(owner);
        if (u != null) {
            Comment c = new Comment(owner, msg);
            Comment originalComment = commentDAO.searchComment(mid);

            if (originalComment != null) {
                commentDAO.insertComment(c);
                originalComment.addReply(c);
                commentDAO.updateComment(originalComment);
            } else {
                throw new ExceptionCommentNotFound();
            }
        } else {
            throw new ExceptionUserNotFound();
        }

    }

    public void addLike(int mid, String username) {

        Users u = userDAO.find(username);
        if (u != null) {
            Comment c = commentDAO.searchComment(mid);
            if (c != null) {
                c.addLike(u);
                commentDAO.updateComment(c);
            } else {
                throw new ExceptionCommentNotFound();
            }
        } else {
            throw new ExceptionUserNotFound();
        }
    }

    public void updateTask(int tid, String titleTask) {
        
        Task t = taskDAO.getTask(tid);
        if(t != null){
            t.setTaskTitle(titleTask);
            taskDAO.updateTask(t);
        }else{
            throw new ExceptionTaskNotFound();
        }
        
    }

    public Collection<CommentDTO> showUserWall(String username) {

        List<CommentDTO> wall = new ArrayList();
        Users user = userDAO.find(username);
        if (user != null) {
            for (Users friend : user.getFriendList()) {
                for (Comment c : friend.getMsgOnWall()) {
                    wall.add(c.getCommentDTO());
                }
            }
        } else {
            throw new ExceptionUserNotFound();
        }

        return wall;
    }

    public List<RequestFriend> getRequestFriend(String username) {
        return requestFriendDAO.getRequestFriend(username);
    }

    public void acceptRequestFriend(String userFrom, String userTo) {
        Users u = userDAO.find(userFrom);
        Users targetFriend = userDAO.find(userTo);

        if (u == null || targetFriend == null) {
            throw new ExceptionUserNotFound();
        }

        u.addFriend(targetFriend);
        targetFriend.addFriend(u);
        userDAO.updateUser(u);
        userDAO.updateUser(targetFriend);
        deleteRequestFriend(userFrom, userTo);

    }

    public void createTask(String taskTitle, String ownerTask) {
        taskDAO.insertTask(new Task(taskTitle, ownerTask));
    }

    public Collection<TaskDTO> getUsersTask(String username) {

        List<TaskDTO> taskList = new ArrayList();
        List<Task> list = taskDAO.getAllTask(username);
        Users user = userDAO.find(username);

        if (user != null) {
            for (Task t : list) {
                taskList.add(t.getTaskDTO());
            }
        } else {
            throw new ExceptionUserNotFound();
        }

        return taskList;
    }

    public TaskDTO getTask(String username) {
        List<Task> list = taskDAO.getAllTask(username);
        int numTask = list.size();

        Random r = new Random();
        int rd = r.nextInt(numTask);

        Task t = list.get(rd);

        return t.getTaskDTO();
    }

    public void deleteTask(int tid){
        taskDAO.DeleteTask(tid);
    }
}
