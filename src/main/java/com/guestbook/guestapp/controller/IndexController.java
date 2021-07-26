package com.guestbook.guestapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {

    private static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @GetMapping("/")
    public String index(Authentication authentication, Model model) {
        LOGGER.info("Entering into index()");
        String username = authentication.getName().toString();
        String role = authentication.getAuthorities().stream().findAny().get().toString();
        LOGGER.info("Role/Authority obtained from authentication for user <--> {} is <--> {}", username, role);
        if ("ROLE_ADMIN".equals(role)) {
            return "redirect:/posts";
        }
        model.addAttribute("name", username);
        LOGGER.info("Leaving index()");
        return "index";
    }
}
