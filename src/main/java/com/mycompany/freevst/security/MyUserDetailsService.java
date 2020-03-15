/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.security;

import com.mycompany.freevst.data.UserRepository;
import com.mycompany.freevst.entities.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryanm
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    private User currentUser;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(s);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + s));

        updateCurrentUser(user);

        User u = user.get();

        MyUserDetails userDetails = new MyUserDetails(u);

        return userDetails;

//        return user.map(MyUserDetails::new).get();
    }

    public void updateCurrentUser(Optional<User> user) {
        currentUser = user.orElse(null);
    }

    public User getCurrentUser() {
        return currentUser;
    }

}
