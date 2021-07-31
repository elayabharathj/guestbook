package com.guestbook.guestapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        model.addAttribute("name", "Welcome " + username +"!!!");
        if ("ROLE_ADMIN".equals(role)) {
            return "redirect:/posts";
        }
        LOGGER.info("Leaving index()");
        return "index";
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("Entering into logoutPage()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        LOGGER.info("Leaving logoutPage()");
        return "redirect:/login?logout";
    }
}
