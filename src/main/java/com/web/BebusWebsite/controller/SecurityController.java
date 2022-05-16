package com.web.BebusWebsite.controller;



import com.web.BebusWebsite.model.User;
import com.web.BebusWebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SecurityController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String getPage(){
        return "redirect:/cinema";
    }

    @RequestMapping("/registration")
    public String getRegisterUser(){
        return "security/register";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid User user, Model model, HttpServletRequest request){

        if (userService.registerNewAccount(user) != null){
            model.addAttribute("wrongData", userService.registerNewAccount(user));
            return "/security/register";
        }
        return "redirect:/login";
    }
}