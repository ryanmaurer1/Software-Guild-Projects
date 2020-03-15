/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.service;

import com.mycompany.freevst.data.UserRepository;
import com.mycompany.freevst.entities.User;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
    
    public User findByUsername(String username){
        
        return userRepo.findByUsername(username).orElse(null);
        
    }

    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    public User findById(int id) {
        return userRepo.findById(id).orElse(null);

    }
    
    public void addNewUser(User u) {
        userRepo.save(u);
    }
}
