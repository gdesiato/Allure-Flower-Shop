package com.giuseppe.allureshop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/logout-confirmation";
    }
    /* after the session is invalidated, the user will be redirected to the logout confirmation page.
    The redirection creates a new request, and with it a new session and security context.
     */

    @GetMapping("/logout-confirmation")
    public String logoutConfirmation() {
        return "logout";
    }

}
