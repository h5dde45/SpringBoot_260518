package com.p1.controllers;

import com.p1.domain.Role;
import com.p1.domain.User;
import com.p1.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute User user, Map<String, Object> model) {
        User byUsername = userRepo.findByUsername(user.getUsername());
        if (byUsername != null) {
            model.put("message", "user exist...");
            return "registration";
        }
        user.setActive(true);
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        userRepo.save(user);

        return "redirect:/login";
    }

}
