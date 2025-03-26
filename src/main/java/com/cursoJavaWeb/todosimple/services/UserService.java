package com.cursoJavaWeb.todosimple.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cursoJavaWeb.todosimple.models.User;
import com.cursoJavaWeb.todosimple.repositories.UserRepository;
import com.cursoJavaWeb.todosimple.services.exceptions.DataBindingViolatioExceptions;
import com.cursoJavaWeb.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class UserService {
    
    @Autowired // construtor do service
    private UserRepository userRepository;


    public User findById(Long id) {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException(
            "Usuario nao encontrado! Id: " + id + ", Tipo: " + User.class.getName()
        ));
    }

    @Transactional
    public User create(User obj) {
        obj.setId(null);
        obj = this.userRepository.save(obj);
        return obj;
    }

    @Transactional
    public User update(User obj) {
        User newObj = findById(obj.getId()); 
        newObj.setPassword(obj.getPassword());
        return this.userRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolatioExceptions("Nao é possivel excluir pos ha entidades realcionadas!");
        }
    }

}
