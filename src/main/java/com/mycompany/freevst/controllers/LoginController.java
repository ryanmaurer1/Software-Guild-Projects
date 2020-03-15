/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.freevst.controllers;

import com.mycompany.freevst.entities.User;
import com.mycompany.freevst.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author ryanm
 */
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {

        User u = new User();
        model.addAttribute("user", u);

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result) {
        
        

        if (userService.findByUsername(user.getUsername()) != null) {

            return "redirect:/register?register_error=1";
        } else if (!result.hasErrors()) {

            user.setRole("ROLE_USER");
            user.setPassword(encoder.encode(user.getPassword()));
            userService.addNewUser(user);

            return "redirect:/login";

        }
        return "/register";

    }

}
