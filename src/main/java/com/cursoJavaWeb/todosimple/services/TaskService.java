package com.cursoJavaWeb.todosimple.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursoJavaWeb.todosimple.models.Task;
import com.cursoJavaWeb.todosimple.models.User;
import com.cursoJavaWeb.todosimple.models.enums.ProfileEnum;
//import com.cursoJavaWeb.todosimple.models.projection.TaskProjection;
import com.cursoJavaWeb.todosimple.repositories.TaskRepository;
import com.cursoJavaWeb.todosimple.security.UserSpringSecurity;
//import com.cursoJavaWeb.todosimple.services.exceptions.AuthorizationException;
import com.cursoJavaWeb.todosimple.services.exceptions.DataBindingViolationException;
import com.cursoJavaWeb.todosimple.services.exceptions.ObjectNotFoundException;


@Service
public class TaskService {
    
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
            "Anotacao nao encontrado! Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }

    public List<Task> findAllByUserId(Long userId) {
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());    
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;        
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Nao é possivel deletar pois ha entidades relacionadas!");
        }
    }

    private Boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }
}
